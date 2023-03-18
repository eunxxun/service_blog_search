package com.eunxxun.service_blog_search.api.model.dto.kakao;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

@Data
public class KaKaoBlogResponse {
    private List<BlogResult> blogResultList;
    private Meta meta;

    @JsonCreator
    public KaKaoBlogResponse(@JsonProperty("documents") List<BlogResult> blogResultList, @JsonProperty("meta") Meta meta) {
        this.blogResultList = blogResultList;
        this.meta = meta;
    }
}
