package com.theoriz.cnode.service.impl;

import com.theoriz.cnode.domain.entity.User;
import com.theoriz.cnode.domain.model.LoginUserDto;
import com.theoriz.cnode.exception.UserAlreadyExistsException;
import com.theoriz.cnode.mapper.UserMapper;
import com.theoriz.cnode.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class UserServiceImpl implements IUserService {
    @Autowired
    private UserMapper userMapper;

    @Override
    public void registerNewUserAccount(LoginUserDto loginUserDto) throws UserAlreadyExistsException {
        if (loginnameExists(loginUserDto.getLoginname())) {
            throw new UserAlreadyExistsException("用户名已被注册");
        }
        User user = new User();
        user.setLoginname(loginUserDto.getLoginname());
        String salt = BCrypt.gensalt();
        String hashpw = BCrypt.hashpw(loginUserDto.getPassword(), salt);
        user.setPassword(hashpw);
        userMapper.save(user);
    }

    private boolean loginnameExists(String loginname) {
        return userMapper.findByLoginname(loginname) != null;
    }
}
