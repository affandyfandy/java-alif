package com.fsoft.lecture15.assignment3.config;

import com.fsoft.lecture15.assignment3.filter.RequestResponseApiKeyFilter;
import com.fsoft.lecture15.assignment3.repository.ApiKeyRepository;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FilterConfig {
    @Bean
    public RequestResponseApiKeyFilter apiKeyFilter(ApiKeyRepository apiKeyRepository) {
        return new RequestResponseApiKeyFilter(apiKeyRepository);
    }

    @Bean
    public FilterRegistrationBean<RequestResponseApiKeyFilter> apiKeyFilterRegistration(RequestResponseApiKeyFilter apiKeyFilter) {
        FilterRegistrationBean<RequestResponseApiKeyFilter> registrationBean = new FilterRegistrationBean<>();
        registrationBean.setFilter(apiKeyFilter);
        registrationBean.addUrlPatterns("/api/v1/employees/*");
        return registrationBean;
    }
}
