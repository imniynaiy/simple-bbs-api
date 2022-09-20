package com.theoriz.cnode.domain.entity;

import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class Reply {
    private Long id;
    private Long topicId;
    private Long authorId;
    private Long replyId;
    private String content;
    private Date createdAt;
    private Date updatedAt;
    private User author;
    private List<Long> upUserIdList;
    private Reply quotedReply;
}
