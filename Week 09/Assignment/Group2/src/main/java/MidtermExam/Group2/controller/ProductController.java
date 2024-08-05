package MidtermExam.Group2.controller;

import MidtermExam.Group2.dto.ProductDTO;
import MidtermExam.Group2.entity.Status;
import MidtermExam.Group2.service.impl.ProductServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import jakarta.validation.Valid;
import java.io.IOException;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/products")
public class ProductController {

    private final ProductServiceImpl productService;

    @Autowired
    public ProductController(ProductServiceImpl productService) {
        this.productService = productService;
    }

    @GetMapping
    public ResponseEntity<Page<ProductDTO>> getAllProducts(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<ProductDTO> products = productService.getAllProducts(pageable);
        return ResponseEntity.ok(products);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductDTO> getProductById(@PathVariable UUID id) {
        Optional<ProductDTO> product = productService.getProductById(id);
        return product.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<?> createProduct(@Valid @RequestBody ProductDTO productDTO, BindingResult result) {
        if (result.hasErrors()) {
            return ResponseEntity.badRequest().body("Validation error: " + result.getAllErrors());
        }
        ProductDTO createdProduct = productService.createProduct(productDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdProduct);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProductDTO> updateProduct(@PathVariable UUID id, @Valid @RequestBody ProductDTO productDTO) {
        Optional<ProductDTO> updatedProduct = productService.updateProduct(id, productDTO);
        return updatedProduct.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PatchMapping("/{id}")
    public ResponseEntity<ProductDTO> toggleProductStatus(@PathVariable UUID id) {
        Optional<ProductDTO> updatedProduct = productService.toggleProductStatus(id);
        return updatedProduct.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteProduct(@PathVariable UUID id) {
        productService.deleteProduct(id);
        return ResponseEntity.ok("Product successfully deleted");
    }

    @PostMapping("/import")
    public ResponseEntity<String> importProductsFromCsv(@RequestParam("file") MultipartFile file) {
        try {
            productService.importProductsFromCsv(file);
            return ResponseEntity.ok("Products imported successfully");
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to import products");
        }
    }

    @GetMapping("/search")
    public ResponseEntity<Page<ProductDTO>> searchProducts(
            @RequestParam(required = false) String name,
            @RequestParam(required = false) Status status,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "name,asc") String[] sort) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Order.by(sort[0]).with(Sort.Direction.fromString(sort[1]))));
        Page<ProductDTO> products = productService.searchProducts(name, status, pageable);
        return ResponseEntity.ok(products);
    }
}
