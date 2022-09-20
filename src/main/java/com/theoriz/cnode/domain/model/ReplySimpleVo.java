package com.theoriz.cnode.domain.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Date;
import java.util.List;

public class ReplySimpleVo {
    private Long id;
    private String content;
    @JsonProperty("ups")
    private List<Long> upUserIdList;
    @JsonProperty("created_at")
    private Date createdAt;
}
