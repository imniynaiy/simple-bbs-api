package com.theoriz.cnode.domain.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class UserLoginVo {
    private Long id;
    private String loginname;
    @JsonProperty("avatar_url")
    private String avatarUrl;
    private String token;
    @JsonProperty("token_head")
    private String tokenHead;
}
