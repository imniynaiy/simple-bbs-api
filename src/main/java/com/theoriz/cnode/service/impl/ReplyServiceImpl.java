package com.theoriz.cnode.service.impl;

import com.theoriz.cnode.domain.Result;
import com.theoriz.cnode.domain.entity.Reply;
import com.theoriz.cnode.domain.entity.Topic;
import com.theoriz.cnode.mapper.MessageMapper;
import com.theoriz.cnode.mapper.ReplyMapper;
import com.theoriz.cnode.mapper.TopicMapper;
import com.theoriz.cnode.service.IMessageService;
import com.theoriz.cnode.service.IReplyService;
import com.theoriz.cnode.service.ITopicService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.StaticApplicationContext;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.NumberUtils;
import sun.security.krb5.KrbTgsReq;

import java.util.List;

@Service
public class ReplyServiceImpl implements IReplyService {
    @Autowired
    private ReplyMapper replyMapper;
    @Autowired
    private ITopicService topicService;
    @Autowired
    private IMessageService messageService;

    @Override
    public List<Reply> getRecentRepliesByUserId(Long userId, int limit) {
        return replyMapper.getRecentRepliesByUserId(userId, limit);
    }

    @Override
    @Transactional
    public Long save(Reply reply) {
        Topic topic = topicService.getTopic(reply.getTopicId());
        if (topic == null) {
            return null;
        }
        if (reply.getReplyId() != null) {
            Reply quotedReply = getReply(reply.getReplyId());
            if (reply == null) {
                return null;
            }
            reply.setQuotedReply(quotedReply);
        }
        if (replyMapper.save(reply) > 0) {
            topicService.updateLastReplyTimeAndReplyCount(reply.getTopicId(), 1);

            if (reply.getReplyId() == null) {
                String content = "用户" + reply.getAuthor().getLoginname() + "回复了您的主题《" + topic.getTitle() + "》";
                messageService.addTopicRepliedMessage(topic.getAuthorId(), topic.getId(), reply.getId(), content);
                return reply.getId();
            }

            String content = "用户" + reply.getAuthor().getLoginname() + "回复了您在主题《" + topic.getTitle() + "》下的回复";
            messageService.addReplyQuotedMessage(reply.getQuotedReply().getAuthorId(), topic.getId(), reply.getId(), content);
            return reply.getId();
        }
        return null;
    }

    @Override
    public boolean replyExists(Long replyId, Long topicId) {
        return replyMapper.replyExists(replyId, topicId);
    }

    @Override
    public Reply getReply(Long id) {
        return replyMapper.getReply(id);
    }
}
