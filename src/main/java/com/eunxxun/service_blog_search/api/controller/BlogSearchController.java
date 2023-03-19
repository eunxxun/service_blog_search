package com.eunxxun.service_blog_search.api.controller;

import com.eunxxun.service_blog_search.api.model.code.SortType;
import com.eunxxun.service_blog_search.api.model.dto.SearchRequest;
import com.eunxxun.service_blog_search.api.model.dto.kakao.KaKaoBlogResponse;
import com.eunxxun.service_blog_search.api.model.dto.kakao.PopularKeyword;
import com.eunxxun.service_blog_search.api.service.BlogSearchService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/blog")
@RequiredArgsConstructor
public class BlogSearchController {

    private final BlogSearchService blogSearchService;

    @GetMapping("/search")
    public KaKaoBlogResponse searchKeyword(@RequestParam("keyword") String keyword,
                                           @RequestParam(value = "sort", required = false, defaultValue = "ACC") SortType sort,
                                           @RequestParam(value = "page", required = false, defaultValue = "1") Integer page,
                                           @RequestParam(value = "size", required = false, defaultValue = "10") Integer size,
                                           HttpServletRequest request) {

        String ip = request.getHeader("X-Forwarded-For");
        if (StringUtils.isEmpty(ip) || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }

        SearchRequest searchRequest = SearchRequest.builder().query(keyword).page(page).size(size).sort(sort.value()).build();

        return blogSearchService.searchAndSave(ip, searchRequest);
    }

    @GetMapping("/popular-keyword")
    public List<PopularKeyword> findTop10Keywords() {
        return blogSearchService.getTop10Keywords();
    }
}

