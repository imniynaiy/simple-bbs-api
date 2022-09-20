package com.theoriz.cnode.domain.entity;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonProperty;
import jdk.nashorn.internal.objects.annotations.Property;
import lombok.Data;

import java.util.Date;

@Data
public class Message {
    private Long id;
    private String type;
    private Long userId;
    private boolean hasRead;
    private Long topicId;
    private Long replyId;
    private String content;
    private Date createdAt;
    private Date updatedAt;
}
