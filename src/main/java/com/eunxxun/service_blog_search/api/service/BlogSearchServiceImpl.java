package com.eunxxun.service_blog_search.api.service;

import com.eunxxun.service_blog_search.api.model.dto.SearchRequest;
import com.eunxxun.service_blog_search.api.model.dto.kakao.KaKaoBlogResponse;
import com.eunxxun.service_blog_search.api.model.dto.kakao.PopularKeyword;
import com.eunxxun.service_blog_search.api.model.entity.SearchResult;
import com.eunxxun.service_blog_search.api.repository.BlogSearchRepository;
import lombok.AllArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ZSetOperations;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Service
@AllArgsConstructor
public class BlogSearchServiceImpl implements BlogSearchService {

    private final BlogSearchRepository blogSearchRepository;
    private final KakaoSearchApi kakaoSearchApi;
    private final RedisTemplate<String, String> redisTemplate;
    private static final String REDIS_KEY = "search_count";

    @Async
    @Override
    public KaKaoBlogResponse searchAndSave(String ip, SearchRequest searchRequest) {
        KaKaoBlogResponse result = kakaoSearchApi.search(searchRequest);

        blogSearchRepository.save(SearchResult.builder()
                .userIp(ip)
                .keyword(searchRequest.getQuery())
                .searchDt(LocalDateTime.now())
                .build());

        redisTemplate.opsForZSet().incrementScore(REDIS_KEY, searchRequest.getQuery(), 1);

        return result;
    }

    @Override
    public List<PopularKeyword> getTop10Keywords() {
        List<PopularKeyword> result = new ArrayList<>();
        Set<ZSetOperations.TypedTuple<String>> keywords = redisTemplate.opsForZSet().reverseRangeWithScores(REDIS_KEY, 0, 9);
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
