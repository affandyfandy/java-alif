package MidtermExam.Group2.controller;

import MidtermExam.Group2.dto.ProductDTO;
import MidtermExam.Group2.entity.Status;
import MidtermExam.Group2.service.ProductService;
import MidtermExam.Group2.service.impl.ProductServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.Optional;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@WebMvcTest(controllers = ProductController.class)
public class ProductControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ProductServiceImpl productService;

    private ProductDTO productDTO;
    private Pageable pageable;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        productDTO = new ProductDTO(UUID.randomUUID(), "Product", BigDecimal.valueOf(42L), "ACTIVE");
        pageable = PageRequest.of(0, 10);
    }

    @Test
    void testGetAllProducts() throws Exception {
        // Given
        Page<ProductDTO> page = new PageImpl<>(Collections.singletonList(productDTO), pageable, 1);

        // Prepare the mock data
        when(productService.getAllProducts(any())).thenReturn(page);

        // Perform the GET request and verify the response
        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/products")
                .contentType("application/json"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.content[0].id").value(productDTO.getId().toString()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.content[0].name").value(productDTO.getName()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.content[0].price").value(productDTO.getPrice().intValue()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.content[0].status").value(productDTO.getStatus()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.pageable.pageSize").value(pageable.getPageSize()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.pageable.pageNumber").value(pageable.getPageNumber()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.totalElements").value(page.getTotalElements()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.totalPages").value(page.getTotalPages()));
    }

    @Test
    void testGetProductById() throws Exception {
        // Given
        UUID id = productDTO.getId();

        // Prepare the mock data
        when(productService.getProductById(id)).thenReturn(Optional.of(productDTO));

        // Perform the GET request and verify the response
        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/products/{id}", productDTO.getId())
                .contentType("application/json"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(productDTO.getId().toString()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value(productDTO.getName()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.price").value(productDTO.getPrice().intValue()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.status").value(productDTO.getStatus()));
    }

    @Test
    void testCreateProduct() throws Exception {
        // Given
        UUID id = productDTO.getId();
        ProductDTO newProductDTO = new ProductDTO(id, "New Product", BigDecimal.valueOf(42L), "ACTIVE");

        // Prepare the mock data
        when(productService.createProduct(newProductDTO)).thenReturn(newProductDTO);

        // Perform the POST request and verify the response
        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/products")
                .contentType("application/json")
                .content("{\"id\":\"" + id + "\",\"name\":\"New Product\",\"price\":42,\"status\":\"ACTIVE\"}"))
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(newProductDTO.getId().toString()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value(newProductDTO.getName()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.price").value(newProductDTO.getPrice().intValue()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.status").value(newProductDTO.getStatus()));
    }

    @Test
    void testUpdateProduct() throws Exception {
        // Given
        UUID id = productDTO.getId();
        ProductDTO updatedProductDTO = new ProductDTO(id, "Updated Product", BigDecimal.valueOf(42L), "ACTIVE");

        // Prepare the mock data
        when(productService.updateProduct(id, updatedProductDTO)).thenReturn(Optional.of(updatedProductDTO));

        // Perform the PUT request and verify the response
        mockMvc.perform(MockMvcRequestBuilders.put("/api/v1/products/{id}", id)
                .contentType("application/json")
                .content("{\"id\":\"" + id + "\",\"name\":\"Updated Product\",\"price\":42,\"status\":\"ACTIVE\"}"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(updatedProductDTO.getId().toString()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value(updatedProductDTO.getName()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.price").value(updatedProductDTO.getPrice().intValue()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.status").value(updatedProductDTO.getStatus()));
    }

    @Test
    void testToggleProductStatus() throws Exception {
        // Given
        UUID id = productDTO.getId();
        ProductDTO toggledProductDTO = new ProductDTO(id, "Product", BigDecimal.valueOf(42L), "INACTIVE");

        // Prepare the mock data
        when(productService.toggleProductStatus(id)).thenReturn(Optional.of(toggledProductDTO));

        // Perform the PATCH request and verify the response
        mockMvc.perform(MockMvcRequestBuilders.patch("/api/v1/products/{id}", id)
                .contentType("application/json"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(toggledProductDTO.getId().toString()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.status").value(toggledProductDTO.getStatus()));
    }

    @Test
    void testDeleteProduct() throws Exception {
        // Given
        UUID id = productDTO.getId();

        // Perform the DELETE request and verify the response
        mockMvc.perform(MockMvcRequestBuilders.delete("/api/v1/products/{id}", id)
                .contentType("application/json"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string("Product successfully deleted"));
    }
}
