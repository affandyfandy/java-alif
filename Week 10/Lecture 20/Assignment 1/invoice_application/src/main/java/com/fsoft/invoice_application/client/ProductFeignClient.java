package com.fsoft.invoice_application.client;

import com.fsoft.invoice_application.dto.ProductDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@FeignClient(name = "product-service", url = "http://localhost:8082")
public interface ProductFeignClient {

    @GetMapping("/api/v1/products/{id}")
    ProductDto getProductById(@PathVariable("id") Long id);

    @GetMapping("/api/v1/products")
    List<ProductDto> getAllProducts();

    @PostMapping("/api/v1/products")
    ProductDto createProduct(ProductDto productDto);

    @PutMapping("/api/v1/products/{id}")
    ProductDto updateProduct(@PathVariable("id") Long id, ProductDto productDto);

    @DeleteMapping("/api/v1/products/{id}")
    void deleteProduct(@PathVariable("id") Long id);
}
