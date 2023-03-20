package com.eunxxun.service_blog_search.api.external;

import com.eunxxun.service_blog_search.api.model.code.KaKaoSortType;
import com.eunxxun.service_blog_search.api.model.code.NaverSortType;
import com.eunxxun.service_blog_search.api.model.dto.kakao.KaKaoBlogRequest;
import com.eunxxun.service_blog_search.api.model.dto.kakao.KaKaoBlogResponse;
import com.eunxxun.service_blog_search.api.model.dto.naver.NaverBlogRequest;
import com.eunxxun.service_blog_search.api.model.dto.naver.NaverBlogResponse;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
class SearchApiTest {

    @Autowired
    private KakaoSearchApi kakaoSearchApi;

    @Autowired
    private NaverSearchApi naverSearchApi;

    @Test
    @DisplayName("카카오 블로그 검색 api 호출 테스트")
    void kakaoOpenApiConnection() {
        KaKaoBlogRequest request = KaKaoBlogRequest.builder()
                .query("마우스")
                .page(1)
                .size(10)
                .sort(KaKaoSortType.ACC.value())
                .build();

        KaKaoBlogResponse response = kakaoSearchApi.search(request);
        assertNotNull(response);
    }

    @Test
    @DisplayName("네이버 블로그 검색 api 호출 테스트")
    void naverOpenApiConnection() {
        NaverBlogRequest request = NaverBlogRequest.builder()
                .query("마우스")
                .start(1)
                .display(10)
                .sort(NaverSortType.SIM_DESC.value())
                .build();

        NaverBlogResponse response = naverSearchApi.search(request);
        assertNotNull(response);
    }
}