package com.eunxxun.service_blog_search.api.service;

import com.eunxxun.service_blog_search.api.model.dto.kakao.KaKaoBlogRequest;
import com.eunxxun.service_blog_search.api.model.dto.kakao.KaKaoBlogResponse;
import com.eunxxun.service_blog_search.api.model.dto.kakao.PopularKeyword;

import java.util.List;

public interface BlogSearchService {
    KaKaoBlogResponse searchAndSave(String ip, KaKaoBlogRequest kaKaoBlogRequest);
    KaKaoBlogResponse search(KaKaoBlogRequest kaKaoBlogRequest);
    void saveSearchLog(String ip, String query);
    void increaseSearchCount(String keyword);
    List<PopularKeyword> getTop10Keywords();
}
