package com.theoriz.cnode.service;

import com.theoriz.cnode.domain.model.MessageVo;

import java.util.List;
import java.util.Map;

public interface IMessageService {
    Integer getCountByUserId(Long userId);

    Map<String, List<MessageVo>> getMessagesByUserId(Long userId);

    List<Long> markAllMessageRead(Long userId);

    void addUppedReplyMessage(Long userId, Long topicId, Long replyId, String content);

    void addReplyQuotedMessage(Long userId, Long topicId, Long replyId, String content);

    void addTopicRepliedMessage(Long userId, Long topicId, Long replyId, String content);

    boolean markOneMessageRead(Long userId, Long messageId);
}
