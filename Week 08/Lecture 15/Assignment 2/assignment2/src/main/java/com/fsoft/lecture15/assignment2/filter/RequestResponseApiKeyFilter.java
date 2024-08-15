package com.fsoft.lecture15.assignment2.filter;

import com.fsoft.lecture15.assignment2.repository.ApiKeyRepository;
import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.io.IOException;


@Component
@Order(1)
public class RequestResponseApiKeyFilter implements Filter {

    private final ApiKeyRepository apiKeyRepository;

    public RequestResponseApiKeyFilter(ApiKeyRepository apiKeyRepository) {
        this.apiKeyRepository = apiKeyRepository;
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest) servletRequest;
        HttpServletResponse httpResponse = (HttpServletResponse) servletResponse;

        Logger logger = org.slf4j.LoggerFactory.getLogger(RequestResponseApiKeyFilter.class);
        logger.info("Logging Request  {} : {}", httpRequest.getMethod(), httpRequest.getRequestURI());

        String apiKey = httpRequest.getHeader("API-KEY");

        if (apiKey == null || !apiKeyRepository.existsByKeyValue(apiKey)) {
            httpResponse.sendError(HttpServletResponse.SC_UNAUTHORIZED, "API key is missing or invalid");
            return;
        }

        httpResponse.setHeader("Source", "FPT Software");

        filterChain.doFilter(servletRequest, servletResponse);

        logger.info("Logging Response :{}", httpResponse.getStatus());

    }
}
