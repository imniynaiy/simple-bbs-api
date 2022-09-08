package com.theoriz.cnode.service;

import com.theoriz.cnode.domain.model.UserDto;

public interface IUserService {

    void registerNewUserAccount(UserDto userDto);

    String login(UserDto userDto);
}
