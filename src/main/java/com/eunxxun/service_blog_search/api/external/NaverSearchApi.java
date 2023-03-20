package com.eunxxun.service_blog_search.api.external;

import com.eunxxun.service_blog_search.api.model.dto.naver.NaverBlogRequest;
import com.eunxxun.service_blog_search.api.model.dto.naver.NaverBlogResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

@Service
public class NaverSearchApi implements SearchApi<NaverBlogRequest, NaverBlogResponse> {
    private final RestTemplate restTemplate = new RestTemplate();

    @Value("${openapi.naver.url}")
    private String url;

    @Value("${openapi.naver.clientId}")
    private String clientId;

    @Value("${openapi.naver.clientSecret}")
    private String clientSecret;

    @Override
    public NaverBlogResponse search(NaverBlogRequest request) {
        HttpHeaders headers = new HttpHeaders();
        headers.set("X-Naver-Client-Id", clientId);
        headers.set("X-Naver-Client-Secret", clientSecret);

        UriComponents uriBuilder = UriComponentsBuilder.fromHttpUrl(url)
                .queryParam("query", request.getQuery())
                .queryParam("sort", request.getSort())
                .queryParam("start", request.getStart())
                .queryParam("display", request.getDisplay())
                .build(false);

        HttpEntity<String> entity = new HttpEntity<>(headers);
        ResponseEntity<NaverBlogResponse> response = restTemplate.exchange(uriBuilder.toUriString(), HttpMethod.GET, entity, NaverBlogResponse.class);

        return response.getBody();
    }

}
