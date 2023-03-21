package com.eunxxun.service_blog_search.api.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class RedisServiceTest {
    private static final String REDIS_KEY = "search_count";

    @Autowired
    private RedisTemplate<String, String> redisTemplate;
    @Autowired
    private BlogSearchServiceImpl blogSearchService;

    @Test
    @DisplayName("redis 검색 횟수 증가")
    void increaseSearchCount() {
        //given
        redisTemplate.delete(REDIS_KEY);
        String[] keywords = {"test","test","test"};

        //when
        for (String keyword : keywords) {
            blogSearchService.increaseSearchCount(keyword);
        }

        //then
        assertThat(redisTemplate.opsForZSet().score(REDIS_KEY, "test")).isEqualTo(3);
    }
}
