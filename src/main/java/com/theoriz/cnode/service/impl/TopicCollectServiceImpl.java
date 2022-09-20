package com.theoriz.cnode.service.impl;

import com.theoriz.cnode.domain.model.TopicVo;
import com.theoriz.cnode.domain.model.UserSimpleVo;
import com.theoriz.cnode.mapper.TopicCollectMapper;
import com.theoriz.cnode.service.ITopicCollectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class TopicCollectServiceImpl implements ITopicCollectService {
    @Autowired
    private TopicCollectMapper topicCollectMapper;

    @Override
    public boolean add(Long topicId, Long userId) {
        if (topicCollectMapper.exist(topicId, userId)) {
            return false;
        }
        return topicCollectMapper.add(topicId, userId) > 0;
    }

    @Override
    public boolean remove(Long topicId, Long userId) {
        return topicCollectMapper.remove(topicId, userId) > 0;
    }

    @Override
    public List<TopicVo> findByUserId(Long userId) {
        return topicCollectMapper.findByUserId(userId).stream().map(topic -> new TopicVo(topic.getId(), topic.getAuthorId(),
                    topic.getTab(), topic.getContent(), topic.getTitle(), topic.getLastReplyAt(), topic.isGood(), topic.isTop(),
                    topic.getReplyCount(), topic.getVisitCount(), topic.getCreatedAt(),
                    new UserSimpleVo(topic.getAuthor().getLoginname(), topic.getAuthor().getAvatarUrl())))
                .collect(Collectors.toList());
    }
}
