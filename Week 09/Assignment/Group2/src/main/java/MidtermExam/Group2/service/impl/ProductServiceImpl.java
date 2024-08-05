package MidtermExam.Group2.service.impl;

import MidtermExam.Group2.dto.ProductDTO;
import MidtermExam.Group2.entity.Product;
import MidtermExam.Group2.entity.Status;
import MidtermExam.Group2.mapper.ProductMapper;
import MidtermExam.Group2.repository.ProductRepository;
import MidtermExam.Group2.service.ProductService;
import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@Transactional
public class ProductServiceImpl implements ProductService {
    private final ProductRepository productRepository;
    private final ProductMapper productMapper;

    @Autowired
    public ProductServiceImpl(ProductRepository productRepository, ProductMapper productMapper) {
        this.productRepository = productRepository;
        this.productMapper = productMapper;
    }

    @Override
    public Page<ProductDTO> getAllProducts(Pageable pageable) {
        return productRepository.findAll(pageable).map(productMapper::toDTO);
    }

    @Override
    public Optional<ProductDTO> getProductById(UUID id) {
        return productRepository.findById(id)
                .map(productMapper::toDTO);
    }

    @Override
    public ProductDTO createProduct(ProductDTO productDTO) {
        Product product = productMapper.toEntity(productDTO);
        product = productRepository.save(product);
        return productMapper.toDTO(product);
    }

    @Override
    public Optional<ProductDTO> updateProduct(UUID id, ProductDTO productDTO) {
        Optional<Product> existingProduct = productRepository.findById(id);
        if (existingProduct.isPresent()) {
            Product product = productMapper.toEntity(productDTO);
            product.setId(id);
            product = productRepository.save(product);
            return Optional.of(productMapper.toDTO(product));
        } else {
            return Optional.empty();
        }
    }

    @Override
    public Optional<ProductDTO> toggleProductStatus(UUID id) {
        Optional<Product> existingProduct = productRepository.findById(id);
        if (existingProduct.isPresent()) {
            Product product = existingProduct.get();
            product.setStatus(product.getStatus() == Status.ACTIVE ? Status.INACTIVE : Status.ACTIVE);
            product = productRepository.save(product);
            return Optional.of(productMapper.toDTO(product));
        } else {
            return Optional.empty();
        }
    }

    @Override
    public void deleteProduct(UUID id) {
        productRepository.deleteById(id);
    }

    @Override
    public void importProductsFromCsv(MultipartFile file) throws IOException {
        try (CSVReader csvReader = new CSVReader(new InputStreamReader(file.getInputStream()))) {
            List<String[]> rows = csvReader.readAll();
            for (String[] row : rows) {
                if (row.length >= 3) {
                    ProductDTO productDTO = new ProductDTO();
                    productDTO.setId(UUID.randomUUID());
                    productDTO.setName(row[0]);
                    productDTO.setPrice(new BigDecimal(row[1]));
                    productDTO.setStatus(String.valueOf(Status.valueOf(row[2].toUpperCase())));
                    Product product = productMapper.toEntity(productDTO);
                    productRepository.save(product);
                }
            }
        } catch (CsvException e) {
            throw new IOException("Failed to read CSV file", e);
        }
    }

    @Override
    public Page<ProductDTO> searchProducts(String name, Status status, Pageable pageable) {
        Page<Product> products;
        if (name != null && status != null) {
            products = productRepository.findByNameContainingIgnoreCaseAndStatus(name, status, pageable);
        } else if (name != null) {
            products = productRepository.findByNameContainingIgnoreCase(name, pageable);
        } else if (status != null) {
            products = productRepository.findByStatus(status, pageable);
        } else {
            products = productRepository.findAll(pageable);
        }
        return products.map(productMapper::toDTO);
    }

}
