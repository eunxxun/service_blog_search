package com.eunxxun.service_blog_search.api.external;

import com.eunxxun.service_blog_search.api.model.dto.kakao.KaKaoBlogRequest;
import com.eunxxun.service_blog_search.api.model.dto.kakao.KaKaoBlogResponse;
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
public class KakaoSearchApi implements SearchApi<KaKaoBlogRequest, KaKaoBlogResponse>{
    private final RestTemplate restTemplate = new RestTemplate();

    @Value("${openapi.kakao.apikey}")
    private String apiKey;

    @Value("${openapi.kakao.url}")
    private String url;

    @Override
    public KaKaoBlogResponse search(KaKaoBlogRequest request) {
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "KakaoAK " + apiKey);

        UriComponents uriBuilder = UriComponentsBuilder.fromHttpUrl(url)
                .queryParam("sort", request.getSort())
                .queryParam("query", request.getQuery())
                .queryParam("page", request.getPage())
                .queryParam("size", request.getSize())
                .build(false);

        HttpEntity<String> entity = new HttpEntity<>(headers);
        ResponseEntity<KaKaoBlogResponse> response = restTemplate.exchange(uriBuilder.toUriString(), HttpMethod.GET, entity, KaKaoBlogResponse.class);

        return response.getBody();
    }
}
