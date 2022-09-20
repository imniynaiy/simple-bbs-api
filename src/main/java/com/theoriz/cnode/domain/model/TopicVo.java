package com.theoriz.cnode.domain.model;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Date;

@Data
@AllArgsConstructor
public class TopicVo {
    private Long id;
    @JsonProperty("author_id")
    private Long authorId;
    private String tab;
    private String content;
    private String title;
    @JsonProperty("last_reply_at")
    private Date lastReplyAt;
    private boolean good;
    private boolean top;
    @JsonProperty("reply_count")
    private int replyCount;
    @JsonProperty("visit_count")
    private int visitCount;
    @JsonProperty("create_at")
    private Date createAt;
    private UserSimpleVo author;
}
