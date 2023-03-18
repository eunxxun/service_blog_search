package com.eunxxun.service_blog_search.api.repository;

import com.eunxxun.service_blog_search.api.model.entity.SearchResult;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BlogSearchRepository extends JpaRepository<SearchResult, Long> {
}
