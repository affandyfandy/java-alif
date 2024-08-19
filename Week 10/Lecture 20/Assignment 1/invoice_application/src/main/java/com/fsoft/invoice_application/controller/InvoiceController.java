package com.fsoft.invoice_application.controller;

import com.fsoft.invoice_application.client.ProductFeignClient;
import com.fsoft.invoice_application.dto.InvoiceDto;
import com.fsoft.invoice_application.dto.ProductDto;
import com.fsoft.invoice_application.service.InvoiceService;
import com.fsoft.invoice_application.service.ProductRestTemplateService;
import com.fsoft.invoice_application.service.ProductWebClientService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/invoices")
public class InvoiceController {
    private final InvoiceService invoiceService;
    private final ProductFeignClient productFeignClient;
    private final ProductWebClientService productWebClientService;
    private final ProductRestTemplateService productRestTemplateService;

    public InvoiceController(InvoiceService invoiceService, ProductFeignClient productFeignClient, ProductWebClientService productWebClientService, ProductRestTemplateService productRestTemplateService) {
        this.invoiceService = invoiceService;
        this.productFeignClient = productFeignClient;
        this.productWebClientService = productWebClientService;
        this.productRestTemplateService = productRestTemplateService;
    }

    @GetMapping
    public ResponseEntity<List<InvoiceDto>> getAllInvoices() {
        return ResponseEntity.ok(invoiceService.getAllInvoices());
    }

    @GetMapping("/{id}")
    public ResponseEntity<InvoiceDto> getInvoiceById(@PathVariable("id") Long id) {
        return invoiceService.getInvoiceById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<InvoiceDto> createInvoice(@RequestBody InvoiceDto invoiceDto) {
        return ResponseEntity.ok(invoiceService.createInvoice(invoiceDto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<InvoiceDto> updateInvoice(@PathVariable("id") Long id, @RequestBody InvoiceDto invoiceDto) {
        return ResponseEntity.ok(invoiceService.updateInvoice(id, invoiceDto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteInvoice(@PathVariable("id") Long id) {
        invoiceService.deleteInvoice(id);
        return ResponseEntity.ok("Invoice deleted successfully");
    }

    @GetMapping("/feign/products/{id}")
    public ResponseEntity<ProductDto> getProductByIdUsingFeign(@PathVariable("id") Long id) {
        return ResponseEntity.ok(productFeignClient.getProductById(id));
    }

    @GetMapping("/webclient/products/{id}")
    public ResponseEntity<ProductDto> getProductByIdUsingWebClient(@PathVariable("id") Long id) {
        return ResponseEntity.ok(productWebClientService.getProductById(id).block());
    }

    @GetMapping("/resttemplate/products/{id}")
    public ResponseEntity<ProductDto> getProductByIdUsingRestTemplate(@PathVariable("id") Long id) {
        return ResponseEntity.ok(productRestTemplateService.getProductById(id));
    }
}
