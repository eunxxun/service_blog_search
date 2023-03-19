package com.eunxxun.service_blog_search.api.model.dto.kakao;

import lombok.Data;

@Data
public class BlogResult {
    private String title;
    private String contents;
    private String url;
    private String blogname;
    private String thumbnail;
    private String datetime;
}
