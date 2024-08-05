package MidtermExam.Group2.service;

import MidtermExam.Group2.dto.ProductDTO;
import MidtermExam.Group2.entity.Status;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Optional;
import java.util.UUID;

public interface ProductService {

    Page<ProductDTO> getAllProducts(Pageable pageable);

    Optional<ProductDTO> getProductById(UUID id);

    ProductDTO createProduct(ProductDTO productDTO);

    Optional<ProductDTO> updateProduct(UUID id, ProductDTO productDTO);

    Optional<ProductDTO> toggleProductStatus(UUID id);

    void deleteProduct(UUID id);

    void importProductsFromCsv(MultipartFile file) throws IOException;

    public Page<ProductDTO> searchProducts(String name, Status status, Pageable pageable);
}