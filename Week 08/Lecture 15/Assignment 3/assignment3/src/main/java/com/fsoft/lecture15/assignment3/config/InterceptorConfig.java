package com.fsoft.lecture15.assignment3.config;

import com.fsoft.lecture15.assignment3.interceptor.RequestResponseApiKeyInterceptor;
import com.fsoft.lecture15.assignment3.repository.ApiKeyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class InterceptorConfig implements WebMvcConfigurer {
    private ApiKeyRepository apiKeyRepository;

    @Autowired
    public InterceptorConfig(ApiKeyRepository apiKeyRepository) {
        this.apiKeyRepository = apiKeyRepository;
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new RequestResponseApiKeyInterceptor(apiKeyRepository))
                .addPathPatterns("/api/v1/*");
    }
}
