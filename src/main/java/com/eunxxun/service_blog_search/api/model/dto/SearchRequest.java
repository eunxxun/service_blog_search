package com.eunxxun.service_blog_search.api.model.dto;

import lombok.*;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SearchRequest {
    @NonNull
    private String query;
    private String sort;
    private Integer page;
    private Integer size;
}
