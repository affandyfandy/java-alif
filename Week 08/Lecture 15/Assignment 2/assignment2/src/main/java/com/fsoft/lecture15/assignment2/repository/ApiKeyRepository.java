package com.fsoft.lecture15.assignment2.repository;

import com.fsoft.lecture15.assignment2.entity.ApiKey;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ApiKeyRepository extends JpaRepository<ApiKey, Long> {
    boolean existsByKeyValue(String keyValue);
}
