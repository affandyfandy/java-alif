package com.fsoft.lecture15.assignment3.interceptor;

import com.fsoft.lecture15.assignment3.entity.ApiKey;
import com.fsoft.lecture15.assignment3.repository.ApiKeyRepository;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import java.util.Optional;

@Component
public class RequestResponseApiKeyInterceptor implements HandlerInterceptor {
    private final ApiKeyRepository apiKeyRepository;

    private static final Logger logger = LoggerFactory.getLogger(RequestResponseApiKeyInterceptor.class);

    @Autowired
    public RequestResponseApiKeyInterceptor(ApiKeyRepository apiKeyRepository) {
        this.apiKeyRepository = apiKeyRepository;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String apiKey = request.getHeader("API-KEY");

        Optional<ApiKey> optionalApiKey = apiKeyRepository.findByKeyValue(apiKey);

        if (apiKey == null || !optionalApiKey.isPresent()) {
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "API key is missing or invalid");
            return false;
        }

        ApiKey key = optionalApiKey.get();
        String username = key.getUsername();

        if (username == null) {
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Username is missing");
            return false;
        }

        request.setAttribute("username", username);
        response.setHeader("Username", username);

        String currentLocalDateTime = java.time.LocalDateTime.now().toString();
        response.setHeader("Timestamp", currentLocalDateTime);

        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        logger.info("Request {} completed with status {}", request.getRequestURI(), response.getStatus());
        logger.debug("Request details: {}", request);
        logger.debug("Response details: {}", response);
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        ApiKey key = apiKeyRepository.findByKeyValue(request.getHeader("API-KEY")).get();
        key.setLastUsed(java.time.LocalDateTime.now());
        apiKeyRepository.save(key);
    }
}
