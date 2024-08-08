package MidtermExam.Group2.controller;

import MidtermExam.Group2.dto.InvoiceProductDTO;
import MidtermExam.Group2.service.InvoiceProductService;
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
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@WebMvcTest(controllers = InvoiceProductController.class)
public class InvoiceProductControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private InvoiceProductService invoiceProductService;

    private InvoiceProductDTO invoiceProductDTO;
    private Pageable pageable;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        invoiceProductDTO = new InvoiceProductDTO(UUID.randomUUID(), UUID.randomUUID(), "Product A", 10, new BigDecimal(1000));
        pageable = PageRequest.of(0, 10);
    }

    @Test
    void testGetAllInvoiceProducts() throws Exception {
        Page<InvoiceProductDTO> page = new PageImpl<>(Collections.singletonList(invoiceProductDTO), pageable, 1);

        when(invoiceProductService.getAllInvoiceProducts(any())).thenReturn(page);

        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/invoice-products")
                .contentType("application/json"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.content[0].invoiceId").value(invoiceProductDTO.getInvoiceId().toString()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.content[0].productId").value(invoiceProductDTO.getProductId().toString()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.content[0].productName").value(invoiceProductDTO.getProductName()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.content[0].quantity").value(invoiceProductDTO.getQuantity()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.content[0].amount").value(invoiceProductDTO.getAmount()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.pageable.pageSize").value(pageable.getPageSize()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.pageable.pageNumber").value(pageable.getPageNumber()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.totalElements").value(page.getTotalElements()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.totalPages").value(page.getTotalPages()));
    }

    @Test
    void testAddInvoiceProduct() throws Exception {
        when(invoiceProductService.addInvoiceProduct(invoiceProductDTO)).thenReturn(invoiceProductDTO);

        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/invoice-products")
                .contentType("application/json")
                        .content("{\"invoiceId\":\"" + invoiceProductDTO.getInvoiceId() + "\"," +
                                "\"productId\":\"" + invoiceProductDTO.getProductId() + "\"," +
                                "\"productName\":\"" + invoiceProductDTO.getProductName() + "\"," +
                                "\"quantity\":\"" + invoiceProductDTO.getQuantity() + "\"," +
                                "\"amount\":\"" + invoiceProductDTO.getAmount() + "\"}"))
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.invoiceId").value(invoiceProductDTO.getInvoiceId().toString()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.productId").value(invoiceProductDTO.getProductId().toString()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.productName").value(invoiceProductDTO.getProductName()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.quantity").value(invoiceProductDTO.getQuantity()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.amount").value(invoiceProductDTO.getAmount()));
    }

    @Test
    void testEditInvoiceProduct() throws Exception {
        UUID invoiceId = invoiceProductDTO.getInvoiceId();
        UUID productId = invoiceProductDTO.getProductId();

        InvoiceProductDTO updatedInvoiceProductDto = new InvoiceProductDTO(invoiceId, productId, "Product A", 15, new BigDecimal(1500));

        when(invoiceProductService.editInvoiceProduct(invoiceProductDTO, invoiceId, productId)).thenReturn(invoiceProductDTO);

        mockMvc.perform(MockMvcRequestBuilders.put("/api/v1/invoice-products/" + invoiceId + "/" + productId)
                .contentType("application/json")
                .content("{\"invoiceId\":\"" + invoiceProductDTO.getInvoiceId() + "\"," +
                        "\"productId\":\"" + invoiceProductDTO.getProductId() + "\"," +
                        "\"productName\":\"" + invoiceProductDTO.getProductName() + "\"," +
                        "\"quantity\":\"" + invoiceProductDTO.getQuantity() + "\"," +
                        "\"amount\":\"" + invoiceProductDTO.getAmount() + "\"}"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.invoiceId").value(invoiceProductDTO.getInvoiceId().toString()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.productId").value(invoiceProductDTO.getProductId().toString()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.productName").value(invoiceProductDTO.getProductName()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.quantity").value(invoiceProductDTO.getQuantity()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.amount").value(invoiceProductDTO.getAmount()));
    }

    @Test
    void testDeleteInvoiceProduct() throws Exception {
        UUID invoiceId = invoiceProductDTO.getInvoiceId();
        UUID productId = invoiceProductDTO.getProductId();

        mockMvc.perform(MockMvcRequestBuilders.delete("/api/v1/invoice-products/" + invoiceId + "/" + productId)
                .contentType("application/json"))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }
}
