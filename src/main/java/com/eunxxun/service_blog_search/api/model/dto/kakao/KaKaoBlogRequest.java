package com.eunxxun.service_blog_search.api.model.dto.kakao;

import lombok.*;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class KaKaoBlogRequest {
    private String query;
    private String sort;
    private Integer page;
    private Integer size;
}
