package com.eunxxun.service_blog_search.api.model.dto.kakao;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class Meta {
    @JsonProperty("is_end")
    private Boolean isEnd;

    @JsonProperty("pageable_count")
    private Integer pageableCount;

    @JsonProperty("total_count")
    private Integer totalCount;
}
