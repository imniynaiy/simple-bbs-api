package com.theoriz.cnode.domain.entity;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
public class User {
    private Long id;
    private String loginname;
    private String password;
    private String avatarUrl;
    private int score;
    private Date createdAt;
    private Date updatedAt;

    public User(Long id, String loginname) {
        this.id = id;
        this.loginname = loginname;
    }
}
