package com.eunxxun.service_blog_search.api.model.dto.naver;

import com.eunxxun.service_blog_search.api.model.dto.kakao.BlogResult;
import com.eunxxun.service_blog_search.api.model.dto.kakao.Meta;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
public class NaverBlogResponse {
    private String lastBuildDate;
    private Long total;
    private Integer start;
    private Integer display;
    private List<Item> items;
}
