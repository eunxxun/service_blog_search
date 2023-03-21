package com.eunxxun.service_blog_search.api.controller;

import com.eunxxun.service_blog_search.api.model.code.KaKaoSortType;
import com.eunxxun.service_blog_search.api.model.dto.kakao.KaKaoBlogRequest;
import com.eunxxun.service_blog_search.api.model.dto.kakao.KaKaoBlogResponse;
import com.eunxxun.service_blog_search.api.model.dto.kakao.PopularKeyword;
import com.eunxxun.service_blog_search.api.service.BlogSearchService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/blog")
@Validated
@RequiredArgsConstructor
public class BlogSearchController {

    private final BlogSearchService blogSearchService;

    @GetMapping("/search")
    public KaKaoBlogResponse searchKeyword(@RequestParam("keyword") @NotEmpty String keyword,
                                           @RequestParam(value = "sort", required = false, defaultValue = "ACC") KaKaoSortType sort,
                                           @RequestParam(value = "page", required = false, defaultValue = "1") @Max(50) @Min(1) Integer page,
                                           @RequestParam(value = "size", required = false, defaultValue = "10") @Max(50) @Min(1) Integer size,
                                           HttpServletRequest request) {
        String ip = request.getHeader("X-Forwarded-For");
        if (StringUtils.isEmpty(ip) || "unknown".equalsIgnoreCase(ip)) ip = request.getRemoteAddr();

        KaKaoBlogRequest kaKaoBlogRequest = KaKaoBlogRequest.builder()
                .query(keyword)
                .page(page)
                .size(size)
                .sort(sort.value())
                .build();

        return blogSearchService.searchAndSave(ip, kaKaoBlogRequest);
    }

    @GetMapping("/popular-keyword")
    public List<PopularKeyword> findTop10Keywords(@RequestParam(value = "size", required = false, defaultValue = "10") @Max(10) @Min(1) Integer size) {
        return blogSearchService.getTop10Keywords(size);
    }
}

