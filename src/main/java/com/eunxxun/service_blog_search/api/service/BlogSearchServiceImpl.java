package com.eunxxun.service_blog_search.api.service;

import com.eunxxun.service_blog_search.api.model.entity.SearchResult;
import com.eunxxun.service_blog_search.api.repository.BlogSearchRepository;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@AllArgsConstructor
public class BlogSearchServiceImpl implements BlogSearchService {

    private final BlogSearchRepository blogSearchRepository;

//    @Override
//    public SearchResult findById(Long id) throws Exception {
//        return blogSearchRepository.findById(id).orElseThrow(() -> new Exception("test"));
//    }

    @Override
    @Transactional
    public void save(String ip, String keyword) {
        blogSearchRepository.save(SearchResult.builder()
                .userIp(ip)
                .keyword(keyword)
                .searchDt(LocalDateTime.now())
                .build());
    }


}
