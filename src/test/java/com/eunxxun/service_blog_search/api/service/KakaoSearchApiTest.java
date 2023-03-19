package com.eunxxun.service_blog_search.api.service;

import com.eunxxun.service_blog_search.api.model.code.SortType;
import com.eunxxun.service_blog_search.api.model.dto.SearchRequest;
import com.eunxxun.service_blog_search.api.model.dto.kakao.KaKaoBlogResponse;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
class KakaoSearchApiTest {

    @Autowired
    private KakaoSearchApi kakaoSearchApi;

    @Test
    void kakaoOpenApiConnection() {
        SearchRequest request = SearchRequest.builder()
                .query("마우스")
                .page(1)
                .size(10)
                .sort(SortType.ACC.value())
                .build();

        KaKaoBlogResponse response = kakaoSearchApi.search(request);
        assertNotNull(response);
    }
}