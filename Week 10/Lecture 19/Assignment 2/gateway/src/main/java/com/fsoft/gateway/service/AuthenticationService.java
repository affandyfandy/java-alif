package com.fsoft.gateway.service;

import com.fsoft.gateway.repository.ApiKeyRepository;
import org.springframework.stereotype.Service;

@Service
public class AuthenticationService {

    private final ApiKeyRepository apiKeyRepository;

    public AuthenticationService(ApiKeyRepository apiKeyRepository) {
        this.apiKeyRepository = apiKeyRepository;
    }

    public boolean isValidApiKey(String apiKey) {
        return apiKeyRepository.existsByApiKey(apiKey) && !apiKey.isEmpty();
    }
}
