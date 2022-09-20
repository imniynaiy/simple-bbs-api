package com.theoriz.cnode.service;

import com.theoriz.cnode.domain.entity.Reply;

import java.util.List;

public interface IReplyService {
    List<Reply> getRecentRepliesByUserId(Long userId, int limit);

    Long save(Reply reply);

    boolean replyExists(Long replyId, Long topicId);

    Reply getReply(Long id);


}
