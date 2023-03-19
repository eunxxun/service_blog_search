package com.eunxxun.service_blog_search.api.model.code;

public enum SortType {
    ACC("accuracy"),
    REC("recency");

    SortType(String value) {
        this.value = value;
    }

    private final String value;
    public String value() {
        return value;
    }
}
