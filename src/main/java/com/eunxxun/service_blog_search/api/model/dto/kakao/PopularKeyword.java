package com.eunxxun.service_blog_search.api.model.dto.kakao;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class PopularKeyword {
    private String keyword;
    private Long count;
}
