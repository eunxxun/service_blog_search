package com.eunxxun.service_blog_search.api.model.dto.naver;

import lombok.*;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class NaverBlogRequest {
    private String query;
    private String sort;
    private Integer start;
    private Integer display;
}
