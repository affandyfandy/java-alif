package MidtermExam.Group2.service;

import MidtermExam.Group2.dto.ProductDTO;
import MidtermExam.Group2.entity.Product;
import MidtermExam.Group2.entity.Status;
import MidtermExam.Group2.mapper.ProductMapper;
import MidtermExam.Group2.repository.ProductRepository;
import MidtermExam.Group2.service.impl.ProductServiceImpl;
import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.nio.charset.StandardCharsets;
import java.util.Collections;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {ProductServiceImpl.class})
public class ProductServiceTest {
    @Autowired
    private ProductServiceImpl productService;

    @MockBean
    private ProductRepository productRepository;

    @MockBean
    private ProductMapper productMapper;

    private Product product;
    private ProductDTO productDTO;
    private UUID productId;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        productId = UUID.randomUUID();

        product = new Product();
        product.setId(productId);
        product.setName("Product A");
        product.setPrice(new BigDecimal("10.00"));
        product.setStatus(Status.ACTIVE);

        productDTO = new ProductDTO();
        productDTO.setId(productId);
        productDTO.setName(product.getName());
        productDTO.setPrice(product.getPrice());
        productDTO.setStatus(product.getStatus().toString());
    }

    /**
     * Method under test: {@link ProductServiceImpl#getAllProducts(Pageable)}
     */
    @Test
    void testGetAllProducts_ReturnPageOfProducts() {
        Pageable pageable = PageRequest.of(0,10);

        when(productRepository.findAll(any(Pageable.class)))
                .thenReturn(new PageImpl<>(Collections.singletonList(product)));
        when(productMapper.toDTO(product)).thenReturn(productDTO);

        Page<ProductDTO> result = productService.getAllProducts(pageable);

        assertThat(result).isNotNull();
        assertThat(result).isNotEmpty();
        assertThat(result.getContent()).hasSize(1);
        assertThat(result.getContent().get(0)).isEqualTo(productDTO);

        verify(productRepository).findAll(pageable);
        verify(productMapper).toDTO(product);
    }

    /**
     * Method under test: {@link ProductServiceImpl#getProductById(UUID)}
     */
    @Test
    void testGetProductById_ID1_ReturnProductDTO() {
        when(productRepository.findById(productId)).thenReturn(Optional.of(product));
        when(productMapper.toDTO(product)).thenReturn(productDTO);

        ProductDTO result = productService.getProductById(productId).get();

        assertThat(result).isNotNull();
        assertThat(result).isEqualTo(productDTO);

        verify(productRepository).findById(productId);
        verify(productMapper).toDTO(product);
    }

    /**
     * Method under test: {@link ProductServiceImpl#getProductById(UUID)}
     */
    @Test
    void testGetProductById_ID2_ReturnEmpty() {
        when(productRepository.findById(productId)).thenReturn(Optional.empty());

        assertThat(productService.getProductById(productId)).isEmpty();

        verify(productRepository).findById(productId);
    }

    /**
     * Method under test: {@link ProductServiceImpl#createProduct(ProductDTO)}
     */
    @Test
    void testCreateProduct_ReturnProductDTO() {
        when(productMapper.toEntity(productDTO)).thenReturn(product);
        when(productRepository.save(product)).thenReturn(product);
        when(productMapper.toDTO(product)).thenReturn(productDTO);

        ProductDTO result = productService.createProduct(productDTO);

        assertThat(result).isNotNull();
        assertThat(result).isEqualTo(productDTO);

        verify(productMapper).toEntity(productDTO);
        verify(productRepository).save(product);
        verify(productMapper).toDTO(product);
    }

    /**
     * Method under test: {@link ProductServiceImpl#updateProduct(UUID, ProductDTO)}
     */
    @Test
    void testUpdateProduct_ID1_ReturnProductDTO() {
        ProductDTO updatedProductDTO = new ProductDTO();
        updatedProductDTO.setId(productId);
        updatedProductDTO.setName("Product B");
        updatedProductDTO.setPrice(new BigDecimal("20.00"));
        updatedProductDTO.setStatus(Status.INACTIVE.toString());

        when(productRepository.findById(productId)).thenReturn(Optional.of(product));
        when(productMapper.toEntity(updatedProductDTO)).thenReturn(product);
        when(productRepository.save(product)).thenReturn(product);
        when(productMapper.toDTO(product)).thenReturn(updatedProductDTO);

        Optional<ProductDTO> result = productService.updateProduct(productId, updatedProductDTO);

        assertThat(result).isPresent();
        assertThat(result.get()).isEqualTo(updatedProductDTO);

        verify(productRepository).findById(productId);
        verify(productRepository).save(product);
        verify(productMapper).toDTO(product);
    }

    /**
     * Method under test: {@link ProductServiceImpl#updateProduct(UUID, ProductDTO)}
     */
    @Test
    void testUpdateProduct_ID2_ReturnEmpty() {
        when(productRepository.findById(productId)).thenReturn(Optional.empty());

        assertThat(productService.updateProduct(productId, productDTO)).isEmpty();

        verify(productRepository).findById(productId);
    }

    /**
     * Method under test: {@link ProductServiceImpl#toggleProductStatus(UUID)}
     */
    @Test
    void testToggleProductStatus_ID1_ReturnProductDTO() {
        when(productRepository.findById(productId)).thenReturn(Optional.of(product));
        when(productRepository.save(product)).thenReturn(product);
        when(productMapper.toDTO(product)).thenReturn(productDTO);

        Optional<ProductDTO> result = productService.toggleProductStatus(productId);

        assertThat(result).isPresent();
        assertThat(result.get()).isEqualTo(productDTO);

        verify(productRepository).findById(productId);
        verify(productRepository).save(product);
        verify(productMapper).toDTO(product);
    }

    /**
     * Method under test: {@link ProductServiceImpl#toggleProductStatus(UUID)}
     */
    @Test
    void testToggleProductStatus_ID2_ReturnEmpty() {
        when(productRepository.findById(productId)).thenReturn(Optional.empty());

        assertThat(productService.toggleProductStatus(productId)).isEmpty();

        verify(productRepository).findById(productId);
    }

    /**
     * Method under test: {@link ProductServiceImpl#deleteProduct(UUID)}
     */
    @Test
    void testDeleteProduct_ID1() {
        doNothing().when(productRepository).deleteById(productId);

        productService.deleteProduct(productId);

        verify(productRepository).deleteById(productId);
    }

    /**
     * Method under test: {@link ProductServiceImpl#deleteProduct(UUID)}
     */
    @Test
    void testDeleteProduct_ID2() {
        doThrow(new RuntimeException()).when(productRepository).deleteById(any(UUID.class));

        assertThrows(RuntimeException.class, () -> productService.deleteProduct(productId));

        verify(productRepository).deleteById(productId);
    }

    /**
     * Method under test: {@link ProductServiceImpl#importProductsFromCsv(MultipartFile)}
     */
    @Test
    void testImportProductsFromCsv() throws IOException, CsvException {
        String csvContent = "Product A,10.00,ACTIVE";
        MockMultipartFile multipartFile = new MockMultipartFile("file", "products.csv", "text/csv", new ByteArrayInputStream(csvContent.getBytes(StandardCharsets.UTF_8)));

        Product productA = new Product();

        when(productMapper.toEntity(any(ProductDTO.class))).thenReturn(productA);
        when(productRepository.save(productA)).thenReturn(productA);

        productService.importProductsFromCsv(multipartFile);

        verify(productMapper).toEntity(argThat(dto ->
                dto.getName().equals("Product A") &&
                dto.getPrice().equals(new BigDecimal("10.00")) &&
                dto.getStatus().equals(Status.ACTIVE.toString())
        ));
        verify(productRepository, times(1)).save(productA);
    }

    /**
     * Method under test: {@link ProductServiceImpl#importProductsFromCsv(MultipartFile)}
     */
    @Test
    void testImportProductsFromCsv_InvalidCsv() throws IOException, CsvException {
        MultipartFile multipartFile = mock(MultipartFile.class);

        when(multipartFile.getInputStream()).thenThrow(new IOException());

        Exception exception = assertThrows(IOException.class, () -> productService.importProductsFromCsv(multipartFile));

        assertThat(exception).isInstanceOf(IOException.class);

        verify(multipartFile).getInputStream();
    }

    /**
     * Method under test: {@link ProductServiceImpl#searchProducts(String, Status, Pageable)}
     */
    @Test
    void testSearchProducts_NameAndStatus() {
        Pageable pageable = PageRequest.of(0, 10);

        when(productRepository.findByNameContainingIgnoreCaseAndStatus("Product A", Status.ACTIVE, pageable))
                .thenReturn(new PageImpl<>(Collections.singletonList(product)));
        when(productMapper.toDTO(product)).thenReturn(productDTO);

        Page<ProductDTO> result = productService.searchProducts("Product A", Status.ACTIVE, pageable);

        assertThat(result).isNotNull();
        assertThat(result).isNotEmpty();
        assertThat(result.getContent()).hasSize(1);
        assertThat(result.getContent().get(0)).isEqualTo(productDTO);

        verify(productRepository).findByNameContainingIgnoreCaseAndStatus("Product A", Status.ACTIVE, pageable);
        verify(productMapper).toDTO(product);
        verify(productRepository, never()).findByNameContainingIgnoreCase(anyString(), any(Pageable.class));
        verify(productRepository, never()).findByStatus(any(Status.class), any(Pageable.class));
        verify(productRepository, never()).findAll(any(Pageable.class));
    }

    /**
     * Method under test: {@link ProductServiceImpl#searchProducts(String, Status, Pageable)}
     */
    @Test
    void testSearchProducts_Name() {
        Pageable pageable = PageRequest.of(0, 10);

        when(productRepository.findByNameContainingIgnoreCase("Product A", pageable))
                .thenReturn(new PageImpl<>(Collections.singletonList(product)));
        when(productMapper.toDTO(product)).thenReturn(productDTO);

        Page<ProductDTO> result = productService.searchProducts("Product A", null, pageable);

        assertThat(result).isNotNull();
        assertThat(result).isNotEmpty();
        assertThat(result.getContent()).hasSize(1);
        assertThat(result.getContent().get(0)).isEqualTo(productDTO);

        verify(productRepository).findByNameContainingIgnoreCase("Product A", pageable);
        verify(productMapper).toDTO(product);
        verify(productRepository, never()).findByNameContainingIgnoreCaseAndStatus(anyString(), any(Status.class), any(Pageable.class));
        verify(productRepository, never()).findByStatus(any(Status.class), any(Pageable.class));
        verify(productRepository, never()).findAll(any(Pageable.class));
    }

    /**
     * Method under test: {@link ProductServiceImpl#searchProducts(String, Status, Pageable)}
     */
    @Test
    void testSearchProducts_Status() {
        Pageable pageable = PageRequest.of(0, 10);

        when(productRepository.findByStatus(Status.ACTIVE, pageable))
                .thenReturn(new PageImpl<>(Collections.singletonList(product)));
        when(productMapper.toDTO(product)).thenReturn(productDTO);

        Page<ProductDTO> result = productService.searchProducts(null, Status.ACTIVE, pageable);

        assertThat(result).isNotNull();
        assertThat(result).isNotEmpty();
        assertThat(result.getContent()).hasSize(1);
        assertThat(result.getContent().get(0)).isEqualTo(productDTO);

        verify(productRepository).findByStatus(Status.ACTIVE, pageable);
        verify(productMapper).toDTO(product);
        verify(productRepository, never()).findByNameContainingIgnoreCase(anyString(), any(Pageable.class));
        verify(productRepository, never()).findByNameContainingIgnoreCaseAndStatus(anyString(), any(Status.class), any(Pageable.class));
        verify(productRepository, never()).findAll(any(Pageable.class));
    }

    /**
     * Method under test: {@link ProductServiceImpl#searchProducts(String, Status, Pageable)}
     */
    @Test
    void testSearchProducts_NoCriteria() {
        Pageable pageable = PageRequest.of(0, 10);

        when(productRepository.findAll(pageable))
                .thenReturn(new PageImpl<>(Collections.singletonList(product)));
        when(productMapper.toDTO(product)).thenReturn(productDTO);

        Page<ProductDTO> result = productService.searchProducts(null, null, pageable);

        assertThat(result).isNotNull();
        assertThat(result).isNotEmpty();
        assertThat(result.getContent()).hasSize(1);
        assertThat(result.getContent().get(0)).isEqualTo(productDTO);

        verify(productRepository).findAll(pageable);
        verify(productMapper).toDTO(product);
        verify(productRepository, never()).findByNameContainingIgnoreCase(anyString(), any(Pageable.class));
        verify(productRepository, never()).findByNameContainingIgnoreCaseAndStatus(anyString(), any(Status.class), any(Pageable.class));
        verify(productRepository, never()).findByStatus(any(Status.class), any(Pageable.class));
    }
}
