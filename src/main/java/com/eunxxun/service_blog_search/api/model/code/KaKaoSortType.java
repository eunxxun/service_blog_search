package com.eunxxun.service_blog_search.api.model.code;

public enum KaKaoSortType {
    ACC("accuracy", "정확도순"),
    REC("recency", "최신순");

    KaKaoSortType(String value, String desc) {
        this.value = value;
        this.desc = desc;
    }

    private final String value;
    private final String desc;
    public String value() {
        return value;
    }
}
