package com.theoriz.cnode.service;

import com.theoriz.cnode.domain.entity.User;
import com.theoriz.cnode.domain.model.UserDetailVo;
import com.theoriz.cnode.domain.model.UserDto;
import com.theoriz.cnode.domain.model.UserLoginVo;

public interface IUserService {

    void registerNewUserAccount(UserDto userDto);

    UserLoginVo login(UserDto userDto);

    User getUserByLoginName(String loginName);

    UserDetailVo getUserInfo(String loginName);

    User getUserByLoginname(String loginname);
}
