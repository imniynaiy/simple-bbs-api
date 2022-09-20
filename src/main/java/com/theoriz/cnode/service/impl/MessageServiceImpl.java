package com.theoriz.cnode.service.impl;

import com.theoriz.cnode.domain.entity.Message;
import com.theoriz.cnode.domain.model.MessageVo;
import com.theoriz.cnode.mapper.MessageMapper;
import com.theoriz.cnode.service.IMessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class MessageServiceImpl implements IMessageService {
    @Autowired
    private MessageMapper messageMapper;

    @Override
    public Integer getCountByUserId(Long userId) {
        try {
            return messageMapper.getCountByUserId(userId);
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public Map<String, List<MessageVo>> getMessagesByUserId(Long userId) {
        List<Message> messages = messageMapper.getMessagesByUserId(userId);
        Map<String, List<MessageVo>> result = new HashMap<>();
        List<MessageVo> hasReadMessages = new ArrayList<>();
        List<MessageVo> hasNotReadMessages = new ArrayList<>();
        for (Message message: messages) {
            if (message.isHasRead()) {
                hasReadMessages.add(new MessageVo(message));
            } else {
                hasNotReadMessages.add(new MessageVo(message));
            }

        }
        result.put("has_read_messages", hasReadMessages);
        result.put("hasnot_read_messages", hasNotReadMessages);
        return result;
    }

    @Override
    public List<Long> markAllMessageRead(Long userId) {
        List<Long> affectedMessageIds = messageMapper.getUnreadMessageIdsByUserId(userId);
        messageMapper.markMessagesRead(affectedMessageIds);
        return affectedMessageIds;
    }

    @Override
    public void addUppedReplyMessage(Long userId, Long topicId, Long replyId, String content) {
        messageMapper.addMessage(userId, "reply_upped",0, topicId, replyId, content);
    }

    @Override
    public void addTopicRepliedMessage(Long userId, Long topicId, Long replyId, String content) {
        messageMapper.addMessage(userId, "topic_replied",0, topicId, replyId, content);
    }

    @Override
    public void addReplyQuotedMessage(Long userId, Long topicId, Long replyId, String content) {
        messageMapper.addMessage(userId, "reply_quoted",0, topicId, replyId, content);
    }

    @Override
    public boolean markOneMessageRead(Long userId, Long messageId) {
        return messageMapper.markOneMessageRead(userId, messageId) > 0;
    }
}
