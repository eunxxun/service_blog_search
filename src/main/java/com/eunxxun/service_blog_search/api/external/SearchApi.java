package com.eunxxun.service_blog_search.api.external;

public interface SearchApi<T, R>  {
    R search(T request);
}
