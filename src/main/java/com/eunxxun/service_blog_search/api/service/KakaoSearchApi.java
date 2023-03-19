package com.eunxxun.service_blog_search.api.service;

import com.eunxxun.service_blog_search.api.model.dto.SearchRequest;
import com.eunxxun.service_blog_search.api.model.dto.kakao.BlogResult;
import com.eunxxun.service_blog_search.api.model.dto.kakao.KaKaoBlogResponse;
import com.eunxxun.service_blog_search.api.model.dto.kakao.Meta;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

@Service
public class KakaoSearchApi implements SearchApi<SearchRequest, KaKaoBlogResponse>{
    private final RestTemplate restTemplate = new RestTemplate();

    @Value("${openapi.kakao.apikey}")
    private String apiKey;

    @Value("${openapi.kakao.url}")
    private String url;

    @Override
    public KaKaoBlogResponse search(SearchRequest request) {
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "KakaoAK " + apiKey);

        UriComponents uriBuilder = UriComponentsBuilder.fromHttpUrl(url)
                .queryParam("sort", request.getSort())
                .queryParam("query", request.getQuery())
                .queryParam("page", request.getPage())
                .queryParam("size", request.getSize())
                .encode()
                .build(false);

        HttpEntity<String> entity = new HttpEntity<>(headers);
        ResponseEntity<KaKaoBlogResponse> response = restTemplate.exchange(uriBuilder.toUriString(), HttpMethod.GET, entity, KaKaoBlogResponse.class);

        return response.getBody();
    }
}
