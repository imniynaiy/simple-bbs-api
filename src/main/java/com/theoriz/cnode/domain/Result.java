package com.theoriz.cnode.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@AllArgsConstructor
@Data
public class Result<T> {
    @NonNull
    private boolean success;
    private T data;
}
