package com.fsoft.lecture15.assignment3.repository;

import com.fsoft.lecture15.assignment3.entity.ApiKey;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ApiKeyRepository extends JpaRepository<ApiKey, Long> {
    Optional<ApiKey> findByKeyValue(String apiKey);
}
