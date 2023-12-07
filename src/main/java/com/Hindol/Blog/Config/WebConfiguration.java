package com.Hindol.Blog.Config;

import com.Hindol.Blog.Middleware.AuthenticationInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfiguration implements WebMvcConfigurer {
    @Bean
    public AuthenticationInterceptor authenticationInterceptor() {
        return new AuthenticationInterceptor();
    }
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(authenticationInterceptor()).addPathPatterns("/api/v1/post/**");
        registry.addInterceptor(authenticationInterceptor()).addPathPatterns("/api/v1/comment/**");
        registry.addInterceptor(authenticationInterceptor()).addPathPatterns("/api/v1/category/**");
    }
}
