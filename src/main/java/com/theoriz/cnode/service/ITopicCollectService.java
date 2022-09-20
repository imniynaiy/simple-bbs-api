package com.theoriz.cnode.service;

import com.theoriz.cnode.domain.model.TopicVo;

import java.util.List;

public interface ITopicCollectService {
    boolean add(Long topicId, Long userId);

    boolean remove(Long topicId, Long userId);

    List<TopicVo> findByUserId(Long userId);
}
