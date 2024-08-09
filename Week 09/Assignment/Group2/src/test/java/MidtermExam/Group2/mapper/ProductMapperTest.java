package MidtermExam.Group2.mapper;

import MidtermExam.Group2.dto.ProductDTO;
import MidtermExam.Group2.entity.Product;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.math.BigDecimal;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = ProductMapper.class)
public class ProductMapperTest {
    @MockBean
    private ProductMapper productMapper;

    @Test
    void toDTO() {
        // Given
        Product product = new Product();
        product.setId(UUID.randomUUID());
        product.setName("Product Name");
        product.setPrice(new BigDecimal("10.00"));

        ProductDTO productDTO = new ProductDTO();
        productDTO.setId(product.getId());
        productDTO.setName(product.getName());
        productDTO.setPrice(product.getPrice());

        // When
        when(productMapper.toDTO(product)).thenReturn(productDTO);
        ProductDTO result = productMapper.toDTO(product);

        // Then
        assertEquals(product.getId(), result.getId());
        assertEquals(product.getName(), result.getName());
        assertEquals(product.getPrice(), result.getPrice());
    }

    @Test
    void toEntity() {
        // Given
        ProductDTO productDTO = new ProductDTO();
        productDTO.setId(UUID.randomUUID());
        productDTO.setName("Product Name");
        productDTO.setPrice(new BigDecimal("10.00"));

        Product product = new Product();
        product.setId(productDTO.getId());
        product.setName(productDTO.getName());
        product.setPrice(productDTO.getPrice());

        // When
        when(productMapper.toEntity(productDTO)).thenReturn(product);
        Product result = productMapper.toEntity(productDTO);

        // Then
        assertEquals(productDTO.getId(), result.getId());
        assertEquals(productDTO.getName(), result.getName());
        assertEquals(productDTO.getPrice(), result.getPrice());
    }
}
