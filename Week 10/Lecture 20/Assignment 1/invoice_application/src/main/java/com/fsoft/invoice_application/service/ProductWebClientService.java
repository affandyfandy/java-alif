package com.fsoft.invoice_application.service;

import com.fsoft.invoice_application.dto.ProductDto;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Service
public class ProductWebClientService {
    private final WebClient webClient;

    public ProductWebClientService(WebClient webClient) {
        this.webClient = webClient;
    }

    public Mono<ProductDto> getProductById(Long id) {
        return webClient.get()
                .uri("/api/v1/products/{id}", id)
                .retrieve()
                .bodyToMono(ProductDto.class);
    }
}
