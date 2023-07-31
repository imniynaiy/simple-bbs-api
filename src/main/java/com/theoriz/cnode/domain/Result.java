package com.theoriz.cnode.domain;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class Result<T> {
    private boolean success;
    private T data;

    public Result(boolean success) {
        this.success = success;
    }
}