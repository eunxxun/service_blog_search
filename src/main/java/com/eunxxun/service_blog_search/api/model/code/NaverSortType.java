package com.eunxxun.service_blog_search.api.model.code;

public enum NaverSortType {
    SIM_DESC("sim", "정확도 순으로 내림차순"),
    DATE_DESC("date", "날짜순으로 내림차순");

    NaverSortType(String value, String desc) {
        this.value = value;
        this.desc = desc;
    }

    private final String value;
    private final String desc;
    public String value() {
        return value;
    }
}
