package com.fsoft.invoice_application.service;

import com.fsoft.invoice_application.dto.ProductDto;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class ProductRestTemplateService {
    private final RestTemplate restTemplate;
    private final String baseUrl;

    public ProductRestTemplateService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
        this.baseUrl = "http://localhost:8081";
    }

    public ProductDto getProductById(Long id) {
        return this.restTemplate.getForObject(baseUrl + "/api/v1/products/{id}", ProductDto.class, id);
    }
}
