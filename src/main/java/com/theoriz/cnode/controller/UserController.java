package com.theoriz.cnode.controller;

import com.theoriz.cnode.domain.Result;
import com.theoriz.cnode.domain.entity.User;
import com.theoriz.cnode.domain.model.UserDetailVo;
import com.theoriz.cnode.domain.model.UserDto;
import com.theoriz.cnode.domain.model.UserLoginVo;
import com.theoriz.cnode.exception.UserAlreadyExistsException;
import com.theoriz.cnode.service.IReplyService;
import com.theoriz.cnode.service.ITopicService;
import com.theoriz.cnode.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@CrossOrigin("http://localhost:8020")
public class UserController {
    @Autowired
    IUserService userService;

    @Value("${jwt.tokenHead}")
    String tokenHead;

    @PostMapping("/register")
    public Result register(@RequestBody UserDto UserDto) {
        try {
            userService.registerNewUserAccount(UserDto);
        } catch (UserAlreadyExistsException userAlreadyExistsException) {
            return new Result(false);
        }
        return new Result(true);
    }

    @PostMapping("/login")
    public Result login(UserDto userDto) {
        UserLoginVo userLoginVo = userService.login(userDto);
        if (userLoginVo == null) {
            return new Result(false);
        }
        return new Result(true, userLoginVo);
    }

    @GetMapping("/user/{loginName}")
    public Result<UserDetailVo> getUserInfo(@PathVariable String loginName){
        UserDetailVo userDetail = userService.getUserInfo(loginName);
        if (userDetail != null) {
            return new Result<>(true, userDetail);
        }
        return new Result(false);

    }
}
