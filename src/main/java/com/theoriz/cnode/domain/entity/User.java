package com.theoriz.cnode.domain.entity;

import lombok.Data;

import java.util.Date;

@Data
public class User {
    private Long id;
    private String loginname;
    private String password;
    private String avatarUrl;
    private Integer score;
    private Date createdAt;
    private Date updatedAt;
}
