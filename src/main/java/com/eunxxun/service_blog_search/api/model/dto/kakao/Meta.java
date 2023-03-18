package com.eunxxun.service_blog_search.api.model.dto.kakao;

import lombok.Data;

@Data
public class Meta {
    private Integer totalCount;
    private Integer pageableCount;
    private Boolean isEnd;
}
