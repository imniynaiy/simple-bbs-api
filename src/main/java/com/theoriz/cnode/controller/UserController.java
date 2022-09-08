package com.theoriz.cnode.controller;

import com.theoriz.cnode.domain.Result;
import com.theoriz.cnode.domain.model.UserDto;
import com.theoriz.cnode.exception.UserAlreadyExistsException;
import com.theoriz.cnode.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
public class UserController {
    @Autowired
    IUserService userService;

    @Value("${jwt.tokenHead}")
    String tokenHead;

    @PostMapping("/register")
    public Result register(UserDto UserDto) {
        try {
            userService.registerNewUserAccount(UserDto);
        } catch (UserAlreadyExistsException userAlreadyExistsException) {
            return new Result(false);
        }
        return new Result(true);
    }

    @PostMapping("/login")
    public Result login(UserDto userDto) {
        String token = userService.login(userDto);
        if (token == null) {
            return new Result(false);
        }
        Map<String, String> tokenMap = new HashMap<>();
        tokenMap.put("token", token);
        tokenMap.put("tokenHead", tokenHead);
        return new Result(true, tokenMap);
    }

    @GetMapping("/user/{loginName}")
    public Result<UserVo> getUserInfo(@PathVariable String loginName){

    }

//    @GetMapping("/public/test")
//    public String test() {
//        return "/public/test";
//    }
}
