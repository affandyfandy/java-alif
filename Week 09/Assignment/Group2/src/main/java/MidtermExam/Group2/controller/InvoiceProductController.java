package MidtermExam.Group2.controller;

import MidtermExam.Group2.dto.InvoiceProductDTO;
import MidtermExam.Group2.service.InvoiceProductService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
    public ResponseEntity<InvoiceProductDTO> addInvoiceProduct(@Valid @RequestBody InvoiceProductDTO invoiceProductDTO) {
        InvoiceProductDTO addedInvoiceProduct = invoiceProductService.addInvoiceProduct(invoiceProductDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(addedInvoiceProduct);
    }

    @PutMapping("/{invoiceId}/{productId}")
    public ResponseEntity<InvoiceProductDTO> editInvoiceProduct(@PathVariable("invoiceId") UUID invoiceId, @PathVariable("productId") UUID productId, @Valid @RequestBody InvoiceProductDTO invoiceProductDTO) {
        InvoiceProductDTO editedInvoiceProduct = invoiceProductService.editInvoiceProduct(invoiceProductDTO, invoiceId, productId);
        return ResponseEntity.ok(editedInvoiceProduct);
    }

    @DeleteMapping("/{invoiceId}/{productId}")
    public ResponseEntity<String> deleteInvoiceProduct(@PathVariable("invoiceId") UUID invoiceId, @PathVariable("productId") UUID productId) {
        invoiceProductService.deleteInvoiceProduct(invoiceId, productId);
        return ResponseEntity.ok("Invoice product deleted successfully");
    }
}
