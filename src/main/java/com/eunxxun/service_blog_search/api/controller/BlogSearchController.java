package com.eunxxun.service_blog_search.api.controller;

import com.eunxxun.service_blog_search.api.model.code.SortType;
import com.eunxxun.service_blog_search.api.model.dto.SearchRequest;
import com.eunxxun.service_blog_search.api.model.dto.kakao.KaKaoBlogResponse;
import com.eunxxun.service_blog_search.api.service.BlogSearchService;
import com.eunxxun.service_blog_search.api.service.KakaoSearchApi;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/blog")
@RequiredArgsConstructor
public class BlogSearchController {

    private final BlogSearchService blogSearchService;
    private final KakaoSearchApi kakaoSearchApi;

    @GetMapping("/search")
    public KaKaoBlogResponse searchKeyword(@RequestParam("keyword") String keyword,
                                           @RequestParam(value = "sort", required = false, defaultValue = "ACC") SortType sort,
                                           @RequestParam(value = "page", required = false, defaultValue = "1") Integer page,
                                           @RequestParam(value = "size", required = false, defaultValue = "10") Integer size,
                                           HttpServletRequest request) {

        String ip = request.getHeader("X-Forwarded-For");
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }

        SearchRequest searchRequest = SearchRequest.builder()
                .query(keyword).page(page).size(size).sort(sort.value()).build();

        blogSearchService.save(ip, keyword);
        return kakaoSearchApi.search(searchRequest);
    }

//    @GetMapping("/popular-keyword")
//    public List<> findTop10Keywords() {
//
//    }
}

