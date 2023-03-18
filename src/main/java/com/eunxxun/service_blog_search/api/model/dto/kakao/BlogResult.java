package com.eunxxun.service_blog_search.api.model.dto.kakao;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class BlogResult {
    private String title;
    private String content;
    private String url;
    private String blogName;
    private String thumbnail;
    private LocalDateTime dateTime;
}
