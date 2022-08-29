package com.theoriz.cnode.service;

import com.theoriz.cnode.domain.model.LoginUserDto;

public interface IUserService {

    void registerNewUserAccount(LoginUserDto loginUserDto);
}
