package com.theoriz.cnode.service;

import com.theoriz.cnode.domain.entity.Reply;
import com.theoriz.cnode.domain.entity.Topic;
import com.theoriz.cnode.domain.model.*;

import java.util.Arrays;
import java.util.List;

public interface ITopicService {
    List<Topic> getRecentTopicsByUserId(Long userId, int limit);

    List<Topic> getRecentReplyTopicsByUserId(Long userId, int limit);

    Long save(Topic topic);

    boolean topicExists(Long topicId);

    List<TopicVo> findPage(ListTopicDto listTopicDto);

    TopicDetailVo getTopicDetail(Long id, Long userId);

    TopicDetailVo getTopicDetail(Long id);

    Topic getTopic(Long id);

    boolean update(TopicDto topicDto, Long userId);

    void updateLastReplyTimeAndReplyCount(Long replyId, int i);
}
