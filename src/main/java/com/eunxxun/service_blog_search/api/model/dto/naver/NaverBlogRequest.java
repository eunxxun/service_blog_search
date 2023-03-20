package com.eunxxun.service_blog_search.api.model.dto.naver;

import com.eunxxun.service_blog_search.api.model.code.NaverSortType;
import lombok.*;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class NaverBlogRequest {
    @NonNull
    private String query;
    @Builder.Default
    private String sort = NaverSortType.SIM_DESC.value();
    @Builder.Default
    private Integer start = 1;
    @Builder.Default
    private Integer display = 10;
}
