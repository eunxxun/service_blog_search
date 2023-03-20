package com.eunxxun.service_blog_search.api.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum ErrorCode {
    MISSING_KEYWORD("검색할 키워드가 없습니다.", HttpStatus.BAD_REQUEST, "400"),
    TOO_LARGE_REQ("요청 파라미터가 너무 길어 검색할 수 없습니다.", HttpStatus.URI_TOO_LONG, "414"),
    API_SERVICE_UNAVAILABLE("API 호출 실패", HttpStatus.SERVICE_UNAVAILABLE, "500");

    private final String message;
    private final HttpStatus httpStatus;
    private final String code;

    ErrorCode(String message, HttpStatus httpStatus, String code) {
        this.message = message;
        this.httpStatus = httpStatus;
        this.code = code;
    }

}
