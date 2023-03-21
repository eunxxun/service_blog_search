package com.eunxxun.service_blog_search.api.service;

import com.eunxxun.service_blog_search.api.external.SearchApi;
import com.eunxxun.service_blog_search.api.model.dto.kakao.BlogResult;
import com.eunxxun.service_blog_search.api.model.dto.kakao.KaKaoBlogRequest;
import com.eunxxun.service_blog_search.api.model.dto.kakao.KaKaoBlogResponse;
import com.eunxxun.service_blog_search.api.model.dto.kakao.Meta;
import com.eunxxun.service_blog_search.api.model.entity.SearchResult;
import com.eunxxun.service_blog_search.api.repository.BlogSearchRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class BlogSearchServiceImplTest {

    private static final String IP = "127.0.0.1";
    private static final String KEYWORD = "test";

    @Mock
    private BlogSearchRepository blogSearchRepository;
    @Mock
    private SearchApi<KaKaoBlogRequest, KaKaoBlogResponse> searchApi;
    @Mock
    private RedisTemplate<String, String> redisTemplate;
    @InjectMocks
    private BlogSearchServiceImpl blogSearchService;

    KaKaoBlogResponse kaKaoBlogResponse;

    @Test
    void 검색횟수_증가_동시성_테스트() throws InterruptedException {
//        ExecutorService executorService = Executors.newFixedThreadPool(10);
//        CountDownLatch countDownLatch = new CountDownLatch(10);
//        for (int i = 1; i <= 10; i++) {
//            executorService.execute(() -> {
//                hitsRedisRepository.incrementHits(1L);
//                countDownLatch.countDown();
//            });
//        }
//
//        countDownLatch.await();
//        Integer hits = hitsRedisRepository.getHits(1L);
//        assertThat(hits).isEqualTo(10);
    }

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        kaKaoBlogResponse = new KaKaoBlogResponse();
        List<BlogResult> blogResultList = new ArrayList<>();
        blogResultList.add(BlogResult.builder().blogname("블로그 이름입니다.").contents("블로그 내용 입니다.").thumbnail("test").title("블로그 제목").url("test").build());
        blogResultList.add(BlogResult.builder().blogname("블로그 이름2입니다.").contents("블로그 내용2 입니다.").thumbnail("test2").title("블로그 제목2").url("test2").build());
        kaKaoBlogResponse.setBlogResultList(blogResultList);
        kaKaoBlogResponse.setMeta(Meta.builder().isEnd(true).pageableCount(1).totalCount(2).build());
    }
    @Test
    @DisplayName("블로그 키워드 검색 - 키워드 검색 성공")
    void successSearchKeyword() {
        // given
        KaKaoBlogRequest kaKaoBlogRequest = KaKaoBlogRequest.builder()
                .query(KEYWORD)
                .build();

        KaKaoBlogResponse expectedResult = kaKaoBlogResponse;
        when(searchApi.search(kaKaoBlogRequest)).thenReturn(expectedResult);

        // when
        KaKaoBlogResponse result = (KaKaoBlogResponse) blogSearchService.search(kaKaoBlogRequest);

        // then
        assertEquals(expectedResult, result);
        verify(searchApi).search(kaKaoBlogRequest);
    }

    @Test
    @DisplayName("블로그 키워드 검색 - 검색 로그 저장")
    void saveSearchLog() {
        // given
        SearchResult searchResult = SearchResult.builder()
                .keyword("블로그")
                .userIp(IP)
                .searchDt(LocalDateTime.now())
                .build();

        //when
        blogSearchRepository.save(searchResult);

        //then
        assertNotNull(searchResult.getKeyword());
    }

}