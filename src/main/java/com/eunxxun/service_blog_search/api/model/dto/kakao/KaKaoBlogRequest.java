package com.eunxxun.service_blog_search.api.model.dto.kakao;

import com.eunxxun.service_blog_search.api.model.code.KaKaoSortType;
import jakarta.validation.constraints.NotEmpty;
import lombok.*;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class KaKaoBlogRequest {
    @NotEmpty
    private String query;
    @Builder.Default
    private String sort = KaKaoSortType.ACC.value();
    @Builder.Default
    private Integer page = 1;
    @Builder.Default
    private Integer size = 10;
}
