package com.example.zuulgateway;

import com.example.zuulgateway.filter.AccessFilter;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;
import org.springframework.context.annotation.Bean;

@EnableZuulProxy
@SpringBootApplication
public class ZuulGatewayApplication {

    public static void main(String[] args) {
        SpringApplication.run(ZuulGatewayApplication.class, args);
    }

    /**
     * 在实现了自定义过滤器之后, 它并不会直接生效, 我们需要在这里创建具体的Bean才能使自定义过滤器生效
     *
     * @return
     */
    @Bean
    public AccessFilter accessFilter() {
        return new AccessFilter();
    }
}
