package com.theoriz.cnode.domain.model;

import com.fasterxml.jackson.annotation.JsonAlias;
import lombok.Data;

@Data
public class TopicCollectDto {
    @JsonAlias("topic_id")
    private Long topicId;
}
