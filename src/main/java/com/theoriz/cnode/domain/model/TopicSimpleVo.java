package com.theoriz.cnode.domain.model;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Date;

@Data
@AllArgsConstructor
public class TopicSimpleVo {
    private Long id;
    private String title;
    @JsonProperty("last_reply_at")
    private Date lastReplyAt;
    private UserSimpleVo author;
}
