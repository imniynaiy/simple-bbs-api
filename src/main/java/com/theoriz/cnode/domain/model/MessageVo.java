package com.theoriz.cnode.domain.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.theoriz.cnode.domain.entity.Message;
import lombok.Data;

import java.util.Date;

@Data
public class MessageVo {
    private Long id;
    private String type;
    @JsonProperty("has_read")
    private boolean hasRead;
    @JsonProperty("topic_id")
    private Long topicId;
    @JsonProperty("reply_id")
    private Long replyId;
    private String content;
    @JsonProperty("created_at")
    private Date createdAt;

    public MessageVo(Message message) {
        this.id = message.getId();
        this.type = message.getType();
        this.hasRead = message.isHasRead();
        this.topicId = message.getTopicId();
        this.replyId = message.getReplyId();
        this.content = message.getContent();
        this.createdAt = message.getCreatedAt();
    }
}
