package com.eunxxun.service_blog_search.api.exception;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ExceptionResponse<T> {
    private T data;
    private int code;
    private String message;

    public ExceptionResponse(T body) {
        this.data = data;
    }
}
