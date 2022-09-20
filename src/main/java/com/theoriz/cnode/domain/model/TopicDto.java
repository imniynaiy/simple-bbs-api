package com.theoriz.cnode.domain.model;

import com.fasterxml.jackson.annotation.JsonAlias;
import lombok.Data;

@Data
public class TopicDto {
    @JsonAlias("topic_id")
    private Long id;
    private String title;
    private String tab;
    private String content;
}
