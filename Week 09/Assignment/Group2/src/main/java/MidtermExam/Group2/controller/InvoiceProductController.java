package MidtermExam.Group2.controller;

import MidtermExam.Group2.dto.InvoiceListDTO;
import MidtermExam.Group2.dto.InvoiceProductDTO;
import MidtermExam.Group2.service.InvoiceProductService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/invoice-products")
public class InvoiceProductController {
    private final InvoiceProductService invoiceProductService;

    @Autowired
    public InvoiceProductController(InvoiceProductService invoiceProductService) {
        this.invoiceProductService = invoiceProductService;
    }

    @GetMapping
    public ResponseEntity<Page<InvoiceProductDTO>> getAllInvoiceProducts(Pageable pageable) {
        return ResponseEntity.ok(invoiceProductService.getAllInvoiceProducts(pageable));
    }

    @PostMapping
    public ResponseEntity<?> addInvoiceProduct(@Valid @RequestBody InvoiceProductDTO invoiceProductDTO) {
        try {
            InvoiceProductDTO addedInvoiceProduct = invoiceProductService.addInvoiceProduct(invoiceProductDTO);
            return ResponseEntity.status(HttpStatus.CREATED).body(addedInvoiceProduct);
        } catch (RuntimeException e) {
            Map<String, String> response = new HashMap<>();
            response.put("errors", e.getMessage());
            return ResponseEntity.badRequest().body(response);
        }
    }

    @PutMapping("/{invoiceId}/{productId}")
    public ResponseEntity<?> editInvoiceProduct(@PathVariable("invoiceId") UUID invoiceId, @PathVariable("productId") UUID productId, @Valid @RequestBody InvoiceProductDTO invoiceProductDTO) {
        try {
            InvoiceProductDTO editedInvoiceProduct = invoiceProductService.editInvoiceProduct(invoiceProductDTO, invoiceId, productId);
            return ResponseEntity.ok(editedInvoiceProduct);
        } catch (RuntimeException e) {
            Map<String, String> response = new HashMap<>();
            response.put("errors", e.getMessage());
            return ResponseEntity.badRequest().body(response);
        }
    }

    @DeleteMapping("/{invoiceId}/{productId}")
    public ResponseEntity<?> deleteInvoiceProduct(@PathVariable("invoiceId") UUID invoiceId, @PathVariable("productId") UUID productId) {
        try {
            invoiceProductService.deleteInvoiceProduct(invoiceId, productId);
            return ResponseEntity.ok().build();
        } catch (RuntimeException e) {
            Map<String, String> response = new HashMap<>();
            response.put("errors", e.getMessage());
            return ResponseEntity.badRequest().body(response);
        }
    }
}
