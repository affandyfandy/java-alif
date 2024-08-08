package MidtermExam.Group2.repository;

import MidtermExam.Group2.entity.Product;
import MidtermExam.Group2.entity.Status;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(SpringExtension.class)
@DataJpaTest
public class ProductRepositoryTest {
    @Autowired
    private ProductRepository productRepository;

    private Product product;

    @BeforeEach
    void setUp() {
        product = new Product();
        product.setName("Product A");
        product.setPrice(new BigDecimal(1000));
        product.setStatus(Status.ACTIVE);
    }

    // JUnit Test for save product operation
    @Test
    void givenProduct_whenSaveProduct_thenReturnSavedProduct() {
        // When: saving the product
        Product savedProduct = productRepository.save(product);

        // Then: the saved product should be the same as the product
        assertThat(savedProduct).isEqualTo(product);
    }

    // JUnit Test for find all products operation
    @Test
    void givenProducts_whenFindAllProducts_thenReturnProductList() {
        // Given: saving the product
        Product savedProduct = productRepository.save(product);

        // When: retrieving all products
        List<Product> products = productRepository.findAll();

        // Then: the product list should not be empty
        assertThat(products).isNotEmpty();
        assertThat(products).contains(savedProduct);
        assertThat(products).hasSize(1);
    }

    // JUnit Test for find product by id operation
    @Test
    void givenProductId_whenFindProductById_thenReturnProduct() {
        // Given: saving the product
        Product savedProduct = productRepository.save(product);

        // When: retrieving the product by id
        Optional<Product> foundProduct = productRepository.findById(savedProduct.getId());

        // Then: the found product should be the same as the saved product
        assertThat(foundProduct).isPresent();
        assertThat(foundProduct.get()).isEqualTo(savedProduct);
    }

    // JUnit Test for update product operation
    @Test
    void givenProduct_whenUpdateProduct_thenReturnUpdatedProduct() {
        // Given: saving the product
        Product savedProduct = productRepository.save(product);

        // When: updating the product
        savedProduct.setName("Product Update");
        Product updatedProduct = productRepository.save(savedProduct);

        // Then: the updated product should be the same as the saved product
        assertThat(updatedProduct).isNotNull();
        assertThat(updatedProduct.getName()).isEqualTo("Product Update");
    }

    // JUnit Test for delete product operation
    @Test
    void givenProduct_whenDeleteProduct_thenProductShouldNotExist() {
        // Given: saving the product
        Product savedProduct = productRepository.save(product);

        // When: deleting the saved product
        productRepository.delete(savedProduct);

        // Then: the product should not exist
        assertThat(productRepository.findById(savedProduct.getId())).isEmpty();
    }

    // JUnit Test for find all products with pagination operation
    @Test
    void givenProducts_whenFindAllProductsByPage_thenReturnProductPage() {
        // Given: saving the product
        Product savedProduct = productRepository.save(product);

        // When: retrieving all products with pagination
        Pageable pageable = PageRequest.of(0, 10);
        Page<Product> products = productRepository.findAll(pageable);

        // Then: the product list should not be empty and should contain the saved product
        assertThat(products).isNotEmpty();
        assertThat(products).contains(savedProduct);
        assertThat(products).hasSize(1);
    }

    // JUnit Test for find by name containing ignore case with pagination operation
    @Test
    void givenProducts_whenFindByNameContainingIgnoreCase_thenReturnProductPage() {
        // Given: saving the product
        Product savedProduct = productRepository.save(product);

        // When: retrieving all products with pagination
        Pageable pageable = PageRequest.of(0, 10);
        Page<Product> products = productRepository.findByNameContainingIgnoreCase("Product", pageable);

        // Then: the product list should not be empty and should contain the saved product
        assertThat(products).isNotEmpty();
        assertThat(products).contains(savedProduct);
        assertThat(products).hasSize(1);
    }

    // JUnit Test for find by status with pagination operation
    @Test
    void givenProducts_whenFindByStatus_thenReturnProductPage() {
        // Given: saving the product
        Product savedProduct = productRepository.save(product);

        // When: retrieving all products with pagination
        Pageable pageable = PageRequest.of(0, 10);
        Page<Product> products = productRepository.findByStatus(Status.ACTIVE, pageable);

        // Then: the product list should not be empty and should contain the saved product
        assertThat(products).isNotEmpty();
        assertThat(products).contains(savedProduct);
        assertThat(products).hasSize(1);
    }

    // JUnit Test for find by name containing ignore case and status with pagination operation
    @Test
    void givenProducts_whenFindByNameContainingIgnoreCaseAndStatus_thenReturnProductPage() {
        // Given: saving the product
        Product savedProduct = productRepository.save(product);

        // When: retrieving all products with pagination
        Pageable pageable = PageRequest.of(0, 10);
        Page<Product> products = productRepository.findByNameContainingIgnoreCaseAndStatus("Product", Status.ACTIVE, pageable);

        // Then: the product list should not be empty and should contain the saved product
        assertThat(products).isNotEmpty();
        assertThat(products).contains(savedProduct);
        assertThat(products).hasSize(1);
    }
}
