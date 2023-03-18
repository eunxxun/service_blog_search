package com.eunxxun.service_blog_search.api.service;

public interface SearchApi<T, R>  {
    R search(T request);
}
