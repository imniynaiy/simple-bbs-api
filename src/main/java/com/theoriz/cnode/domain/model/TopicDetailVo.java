package com.theoriz.cnode.domain.model;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.theoriz.cnode.domain.entity.Reply;
import com.theoriz.cnode.domain.entity.User;
import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class TopicDetailVo {
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
    @JsonProperty("created_at")
    private Date createdAt;
    private UserSimpleVo author;
    @JsonProperty("replies")
    private List<ReplyVo> replyList;
    @JsonProperty("is_collect")
    private boolean collect;
}
