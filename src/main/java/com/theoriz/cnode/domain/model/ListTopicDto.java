package com.theoriz.cnode.domain.model;

import lombok.Data;

@Data
public class ListTopicDto {
    private Integer page;
    private String tab;
    private Integer limit;
    private boolean good;
}
