package com.eunxxun.service_blog_search.api.service;

import com.eunxxun.service_blog_search.api.external.SearchApi;
import com.eunxxun.service_blog_search.api.model.dto.kakao.KaKaoBlogRequest;
import com.eunxxun.service_blog_search.api.model.dto.kakao.KaKaoBlogResponse;
import com.eunxxun.service_blog_search.api.model.dto.kakao.PopularKeyword;
import com.eunxxun.service_blog_search.api.model.dto.naver.NaverBlogRequest;
import com.eunxxun.service_blog_search.api.model.dto.naver.NaverBlogResponse;
import com.eunxxun.service_blog_search.api.model.entity.SearchResult;
import com.eunxxun.service_blog_search.api.repository.BlogSearchRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ZSetOperations;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Slf4j
@Service
@AllArgsConstructor
public class BlogSearchServiceImpl implements BlogSearchService {

    private final BlogSearchRepository blogSearchRepository;
    private final SearchApi<KaKaoBlogRequest, KaKaoBlogResponse> kakaoSearchApi;
    private final SearchApi<NaverBlogRequest, NaverBlogResponse> naverSearchApi;
    private final RedisTemplate<String, String> redisTemplate;
    private static final String REDIS_KEY = "search_count";

    @Override
    public KaKaoBlogResponse searchAndSave(String ip, KaKaoBlogRequest kaKaoBlogRequest) {
        //카카오 Api 호출
        KaKaoBlogResponse result = search(kaKaoBlogRequest);
        //검색 로그 저장
        saveSearchLog(ip, kaKaoBlogRequest.getQuery());
        //keyword Redis 횟수 증가
        increaseSearchCount(kaKaoBlogRequest.getQuery());

        return result;
    }

    @Override
    public KaKaoBlogResponse search(KaKaoBlogRequest kaKaoBlogRequest) {
        return kakaoSearchApi.search(kaKaoBlogRequest);
    }

    private void saveSearchLog(String ip, String query) {
        blogSearchRepository.save(SearchResult.builder()
                .userIp(ip)
                .keyword(query)
                .searchDt(LocalDateTime.now())
                .build());
    }

    @Override
    public void increaseSearchCount(String keyword) {
        redisTemplate.opsForZSet().incrementScore(REDIS_KEY, keyword, 1);
    }

    @Override
    public List<PopularKeyword> getTop10Keywords(Integer size) {
        List<PopularKeyword> result = new ArrayList<>();
        Set<ZSetOperations.TypedTuple<String>> keywords = redisTemplate.opsForZSet().reverseRangeWithScores(REDIS_KEY, 0, size - 1);
        if(keywords != null){
            for (ZSetOperations.TypedTuple<String> keyword : keywords) {
                result.add(PopularKeyword.builder()
                        .keyword(keyword.getValue())
                        .count(Math.round(keyword.getScore()))
                        .build());
            }
        }
        return result;
    }

}
