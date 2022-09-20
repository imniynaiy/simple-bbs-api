package com.theoriz.cnode.domain.model;

import com.fasterxml.jackson.annotation.JsonAlias;
import lombok.Data;

@Data
public class ReplyDto {
    private String content;
    @JsonAlias("reply_id")
    private Long replyId;
}
