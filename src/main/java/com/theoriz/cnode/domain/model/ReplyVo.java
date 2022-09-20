package com.theoriz.cnode.domain.model;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.theoriz.cnode.domain.entity.User;
import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class ReplyVo {
    private Long id;
    private UserSimpleVo author;
    private String content;
    @JsonProperty("ups")
    private List<Long> upUserIdList;
    @JsonProperty("created_at")
    private Date createdAt;
    @JsonProperty("reply_id")
    private Long replyId;
    @JsonProperty("is_uped")
    private boolean uped;

    public ReplyVo(Long id, User author, String content, List<Long> upUserIdList, Date createdAt, Long replyId) {
        this.id = id;
        this.author = new UserSimpleVo(author.getLoginname(), author.getAvatarUrl());
        this.content = content;
        this.upUserIdList = upUserIdList;
        this.createdAt = createdAt;
        this.replyId = replyId;
    }
}
