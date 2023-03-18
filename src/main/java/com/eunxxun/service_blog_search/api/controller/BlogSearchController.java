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

//    @GetMapping("/test/{id}")
//    public SearchResult findById(@PathVariable Long id) throws Exception {
//        return blogSearchService.findById(id);
//    }

    @GetMapping("/search")
    public KaKaoBlogResponse searchKeyword(@RequestParam("keyword") String keyword,
                                                 HttpServletRequest request) {
        String ip = request.getRemoteAddr();
        SearchRequest searchRequest = SearchRequest.builder().query(keyword).page(1).size(10).sort(SortType.ACCURACY.value()).build();

        blogSearchService.save(ip, keyword);
        return kakaoSearchApi.search(searchRequest);
    }

//    @GetMapping("/popular-keyword")
//    public List<> findTop10Keywords() {
//
//    }
}

