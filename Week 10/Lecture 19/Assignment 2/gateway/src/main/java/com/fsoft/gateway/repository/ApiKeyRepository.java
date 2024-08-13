package com.fsoft.gateway.repository;

import com.fsoft.gateway.model.ApiKey;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ApiKeyRepository extends JpaRepository<ApiKey, Long> {
    boolean existsByApiKey(String apiKey);
}
