package com.fsoft.invoice_application.controller;

import com.fsoft.invoice_application.client.ProductFeignClient;
import com.fsoft.invoice_application.dto.ProductDto;
import com.fsoft.invoice_application.service.ProductRestTemplateService;
import com.fsoft.invoice_application.service.ProductWebClientService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping
public class InvoiceController {
    private final ProductFeignClient productFeignClient;
    private final ProductWebClientService productWebClientService;
    private final ProductRestTemplateService productRestTemplateService;

    public InvoiceController(ProductFeignClient productFeignClient, ProductWebClientService productWebClientService, ProductRestTemplateService productRestTemplateService) {
        this.productFeignClient = productFeignClient;
        this.productWebClientService = productWebClientService;
        this.productRestTemplateService = productRestTemplateService;
    }

    @GetMapping("/feign/products/{id}")
    public ProductDto getProductByIdFeign(@PathVariable("id") Long id) {
        return productFeignClient.getProductById(id);
    }

    @GetMapping("/webclient/products/{id}")
    public ProductDto getProductByIdWebClient(@PathVariable("id") Long id) {
        return productWebClientService.getProductById(id).block();
    }

    @GetMapping("/resttemplate/products/{id}")
    public ProductDto getProductByIdRestTemplate(@PathVariable("id") Long id) {
        return productRestTemplateService.getProductById(id);
    }
}
