package com.theoriz.cnode.service.impl;

import com.theoriz.cnode.domain.entity.User;
import com.theoriz.cnode.domain.model.*;
import com.theoriz.cnode.exception.UserAlreadyExistsException;
import com.theoriz.cnode.mapper.UserMapper;
import com.theoriz.cnode.service.IReplyService;
import com.theoriz.cnode.service.ITopicService;
import com.theoriz.cnode.service.IUserService;
import com.theoriz.cnode.utils.JwtTokenUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class UserServiceImpl implements IUserService {
    @Autowired
    private UserMapper userMapper;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Autowired
    private UserDetailsService userDetailsService;


    @Autowired
    private ITopicService topicService;
    @Value("${jwt.tokenHead}")
    private String tokenHead;

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
    public UserLoginVo login(UserDto userDto) {
        String loginname = userDto.getLoginname();
        String password = userDto.getPassword();
        User user = getUserByLoginname(loginname);
        boolean passwordCorrect = BCrypt.checkpw(password, user.getPassword());
        if (passwordCorrect) {
            UserLoginVo userLoginVo = new UserLoginVo();
            userLoginVo.setId(user.getId());
            userLoginVo.setLoginname(loginname);
            userLoginVo.setTokenHead(tokenHead);
            userLoginVo.setAvatarUrl(user.getAvatarUrl());
            userLoginVo.setToken(jwtTokenUtil.generateToken(user));
            return userLoginVo;
        }
        return null;
    }

    @Override
    public User getUserByLoginName(String loginName) {
        return userMapper.findByLoginname(loginName);
    }

    private boolean loginnameExists(String loginname) {
        return userMapper.findByLoginname(loginname) != null;
    }

    @Override
    public UserDetailVo getUserInfo(String loginName) {
        User user = getUserByLoginName(loginName);
        if (user != null) {
            UserDetailVo userDetail = new UserDetailVo();
            userDetail.setLoginname(user.getLoginname());
            userDetail.setAvatarUrl(user.getAvatarUrl());
            userDetail.setCreatedAt(user.getCreatedAt());
            userDetail.setScore(user.getScore());
            userDetail.setRecentTopics(getRecentTopics(user));
            userDetail.setRecentReplies(getRecentReplyTopics(user));
            return userDetail;
        }
        return null;
    }

    @Override
    public User getUserByLoginname(String loginname) {
        return userMapper.findByLoginname(loginname);
    }

    private List<TopicSimpleVo> getRecentReplyTopics(User user) {
        return topicService.getRecentReplyTopicsByUserId(user.getId(), 5).stream().map(topic ->
                new TopicSimpleVo(topic.getId(), topic.getTitle(), topic.getLastReplyAt(),
                        new UserSimpleVo(user.getLoginname(), user.getAvatarUrl()))).collect(Collectors.toList());
    }

    private List<TopicSimpleVo> getRecentTopics(User user) {
        return topicService.getRecentTopicsByUserId(user.getId(), 15).stream().map(topic ->
                new TopicSimpleVo(topic.getId(), topic.getTitle(), topic.getLastReplyAt(),
                        new UserSimpleVo(user.getLoginname(), user.getAvatarUrl()))).collect(Collectors.toList());
    }
}
