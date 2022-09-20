package com.theoriz.cnode.domain.model;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class UserDetailVo {
    private String loginname;
    @JsonProperty("avatar_url")
    private String avatarUrl;
    private Integer score;
    @JsonProperty("created_at")
    private Date createdAt;
    @JsonProperty("recent_topics")
    private List<TopicSimpleVo> recentTopics;
    @JsonProperty("recent_replies")
    private List<TopicSimpleVo> recentReplies;
}
