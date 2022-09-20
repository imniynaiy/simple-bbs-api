package com.theoriz.cnode.domain.entity;

import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class Topic {
    private Long id;
    private Long authorId;
    private String tab;
    private String content;
    private String title;
    private Date lastReplyAt;
    private boolean good;
    private boolean top;
    private int replyCount;
    private int visitCount;
    private Date createdAt;
    private Date updatedAt;
    private User author;
    private List<Reply> replyList;
}
