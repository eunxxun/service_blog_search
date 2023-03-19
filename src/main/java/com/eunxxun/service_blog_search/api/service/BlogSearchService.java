package com.eunxxun.service_blog_search.api.service;

import com.eunxxun.service_blog_search.api.model.dto.SearchRequest;
import com.eunxxun.service_blog_search.api.model.dto.kakao.KaKaoBlogResponse;
import com.eunxxun.service_blog_search.api.model.dto.kakao.PopularKeyword;

import java.util.List;

public interface BlogSearchService {
    KaKaoBlogResponse searchAndSave(String ip, SearchRequest searchRequest);
    List<PopularKeyword> getTop10Keywords();
}
