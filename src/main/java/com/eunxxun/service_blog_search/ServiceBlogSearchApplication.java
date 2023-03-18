package com.eunxxun.service_blog_search;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaRepositories(basePackages = "com.eunxxun.service_blog_search.api.repository")
public class ServiceBlogSearchApplication {

    public static void main(String[] args) {
        SpringApplication.run(ServiceBlogSearchApplication.class, args);
    }

}
