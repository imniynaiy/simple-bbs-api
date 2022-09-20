package com.theoriz.cnode.service.impl;

import com.github.pagehelper.PageHelper;
import com.theoriz.cnode.domain.entity.Reply;
import com.theoriz.cnode.domain.entity.Topic;
import com.theoriz.cnode.domain.entity.User;
import com.theoriz.cnode.domain.model.*;
import com.theoriz.cnode.mapper.TopicCollectMapper;
import com.theoriz.cnode.mapper.TopicMapper;
import com.theoriz.cnode.mapper.UserReplyLikeMapper;
import com.theoriz.cnode.service.ITopicService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
public class TopicServiceImpl implements ITopicService {
    @Autowired
    private TopicMapper topicMapper;
    @Autowired
    private UserReplyLikeMapper userReplyLikeMapper;
    @Autowired
    private TopicCollectMapper userTopicCollectMapper;

    @Override
    public List<Topic> getRecentTopicsByUserId(Long userId, int limit) {
        return topicMapper.getRecentTopicsByUserId(userId, limit);
    }

    @Override
    public List<Topic> getRecentReplyTopicsByUserId(Long userId, int limit) {
        return topicMapper.getRecentReplyTopicsByUserId(userId, limit);
    }

    @Override
    public Long save(Topic topic) {
        if (topicMapper.save(topic) > 0) {
            return topic.getId();
        }
        return null;
    }

    @Override
    public boolean topicExists(Long topicId) {
        return topicMapper.topicExists(topicId);
    }

    @Override
    public List<TopicVo> findPage(ListTopicDto listTopicDto) {
        PageHelper.startPage(listTopicDto.getPage(), listTopicDto.getLimit());
        List<Topic> topics = topicMapper.list(listTopicDto.getTab(), listTopicDto.isGood());
        List<TopicVo> topicVos = topics.stream().map(topic -> new TopicVo(topic.getId(),topic.getAuthorId(),
                topic.getTab(),topic.getContent(), topic.getTitle(), topic.getLastReplyAt(), topic.isGood(),
                topic.isTop(), topic.getReplyCount(), topic.getVisitCount(), topic.getCreatedAt(),
                new UserSimpleVo(topic.getAuthor().getLoginname(), topic.getAuthor().getAvatarUrl()))
            ).collect(Collectors.toList());
        return topicVos;
    }

    @Override
    public TopicDetailVo getTopicDetail(Long id) {
        return getTopicDetail(id, null);
    }

    @Override
    public Topic getTopic(Long id) {
        return topicMapper.getById(id);
    }

    @Override
    public boolean update(TopicDto topicDto, Long userId) {
        Topic topic = new Topic();
        topic.setId(topicDto.getId());
        topic.setTitle(topicDto.getTitle());
        topic.setContent(topicDto.getContent());
        topic.setAuthorId(userId);
        topic.setTab(topicDto.getTab());
        return topicMapper.update(topic) > 0;
    }

    @Override
    public void updateLastReplyTimeAndReplyCount(Long topicId, int i) {
        topicMapper.updateReplyTime(topicId);
        topicMapper.addReplyCount(1, topicId);
    }

    @Override
    public TopicDetailVo getTopicDetail(Long topicId, Long userId) {
        topicMapper.addVisitCount(1, topicId);
        Topic topic = topicMapper.getByIdWithReplies(topicId);
        TopicDetailVo topicDetailVo = new TopicDetailVo();
        topicDetailVo.setId(topic.getId());
        topicDetailVo.setAuthorId(topic.getAuthorId());
        topicDetailVo.setTab(topic.getTab());
        topicDetailVo.setContent(topic.getContent());
        topicDetailVo.setTitle(topic.getTitle());
        topicDetailVo.setLastReplyAt(topic.getLastReplyAt());
        topicDetailVo.setGood(topic.isGood());
        topicDetailVo.setTop(topic.isTop());
        topicDetailVo.setReplyCount(topic.getReplyCount());
        topicDetailVo.setVisitCount(topic.getVisitCount());
        topicDetailVo.setCreatedAt(topic.getCreatedAt());
        topicDetailVo.setAuthor(new UserSimpleVo(topic.getAuthor().getLoginname(), topic.getAuthor().getAvatarUrl()));
        topicDetailVo.setReplyList(topic.getReplyList().stream().map(convertReplyToReplyVo).collect(Collectors.toList())
        );
        if (userId != null) {
            topicDetailVo.setCollect(userTopicCollectMapper.exist(topicId, userId));
            topicDetailVo.getReplyList().stream().map(addUped(userId));
        }
        return topicDetailVo;
    }

    Function<Reply, ReplyVo> convertReplyToReplyVo = reply -> {
        return new ReplyVo(reply.getId(), reply.getAuthor(), reply.getContent(), reply.getUpUserIdList(),
                reply.getCreatedAt(), reply.getReplyId());
    };

    private Function<ReplyVo, ReplyVo> addUped(Long userId) {
        Function<ReplyVo, ReplyVo> addUpedFunction = replyVo -> {
            replyVo.setUped(userReplyLikeMapper.exists(userId, replyVo.getId()));
            return replyVo;
        };
        return addUpedFunction;
    }
}
