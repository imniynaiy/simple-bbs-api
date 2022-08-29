package com.theoriz.cnode.domain;

import lombok.Data;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Data
public class Result<T> {
    @NonNull
    private boolean success;
    private T data;
}
