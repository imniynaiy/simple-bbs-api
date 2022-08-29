package com.theoriz.cnode.controller;

import com.theoriz.cnode.domain.Result;
import com.theoriz.cnode.domain.model.LoginUserDto;
import com.theoriz.cnode.exception.UserAlreadyExistsException;
import com.theoriz.cnode.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class UserController {
    @Autowired
    IUserService userService;

    @PostMapping("/register")
    public Result register(LoginUserDto loginUserDto) {
        try {
            userService.registerNewUserAccount(loginUserDto);
        } catch (UserAlreadyExistsException userAlreadyExistsException) {
            return new Result(false);
        }
        return new Result(true);
    }

    @GetMapping("/public/test")
    public String test() {
        return "/public/test";
    }
}
