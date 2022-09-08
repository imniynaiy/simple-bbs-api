package com.theoriz.cnode.service.impl;

import com.theoriz.cnode.domain.entity.User;
import com.theoriz.cnode.domain.model.UserDto;
import com.theoriz.cnode.exception.UserAlreadyExistsException;
import com.theoriz.cnode.mapper.UserMapper;
import com.theoriz.cnode.service.IUserService;
import com.theoriz.cnode.utils.JwtTokenUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class UserServiceImpl implements IUserService {
    @Autowired
    private UserMapper userMapper;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Autowired
    private UserDetailsService userDetailsService;

    @Override
    public void registerNewUserAccount(UserDto userDto) throws UserAlreadyExistsException {
        if (loginnameExists(userDto.getLoginname())) {
            throw new UserAlreadyExistsException("用户名已被注册");
        }
        User user = new User();
        user.setLoginname(userDto.getLoginname());
        String salt = BCrypt.gensalt();
        String hashpw = BCrypt.hashpw(userDto.getPassword(), salt);
        user.setPassword(hashpw);
        userMapper.save(user);
    }

    @Override
    public String login(UserDto userDto) {
        UserDetails userDetails = userDetailsService.loadUserByUsername(userDto.getLoginname());
        boolean passwordCorrect = BCrypt.checkpw(userDto.getPassword(), userDetails.getPassword());
        if (passwordCorrect) {
            return jwtTokenUtil.generateToken(userDetails);
        }
        return null;
    }

    private boolean loginnameExists(String loginname) {
        return userMapper.findByLoginname(loginname) != null;
    }
}
