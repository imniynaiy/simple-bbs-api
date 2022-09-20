package com.theoriz.cnode.service.impl;

import com.theoriz.cnode.domain.entity.Reply;
import com.theoriz.cnode.mapper.MessageMapper;
import com.theoriz.cnode.mapper.UserMapper;
import com.theoriz.cnode.mapper.UserReplyLikeMapper;
import com.theoriz.cnode.service.IReplyService;
import com.theoriz.cnode.service.IUserReplyLikeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserReplyLikeServiceImpl implements IUserReplyLikeService {
    @Autowired
    private UserReplyLikeMapper userReplyLikeMapper;

    @Autowired
    private UserMapper userMapper;
    @Autowired
    private MessageMapper messageMapper;
    @Autowired
    private IReplyService replyService;

    @Override
    public String upOrDownReply(Long replyId, Long userId) {
        if (userReplyLikeMapper.exists(userId, replyId)) {
            downReply(replyId, userId);
            return "down";
        } else if (upReply(replyId, userId)) {
            return "up";
        }
        return null;
    }

    @Transactional
    public boolean upReply(Long replyId, Long userId) {
        int infectedRows = userReplyLikeMapper.add(userId, replyId);
        userMapper.addScore(userId,10);
        return infectedRows > 0;
    }

    @Transactional
    public boolean downReply(Long replyId, Long userId) {
        int infectedRows = userReplyLikeMapper.delete(userId, replyId);
        userMapper.addScore(userId,-10);
        return infectedRows > 0;
    }
}
