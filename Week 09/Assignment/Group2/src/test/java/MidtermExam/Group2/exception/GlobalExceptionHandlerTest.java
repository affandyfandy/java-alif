package MidtermExam.Group2.exception;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class GlobalExceptionHandlerTest {

    @Autowired
    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void whenMethodArgumentNotValid_thenReturnsBadRequest() throws Exception {
        String invalidRequestBody = "{ \"name\": \"\", \"phoneNo\": \"1234567890\", \"status\": \"\" }";

        mockMvc.perform(post("/api/v1/customers")  // Replace with your actual endpoint
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(invalidRequestBody))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.errors").exists())
                .andExpect(jsonPath("$.errors").isArray())
                .andExpect(jsonPath("$.errors.length()").value(3));
    }

    @Test
    void whenIllegalArgumentException_thenReturnsBadRequest() throws Exception {
        mockMvc.perform(post("/api/v1/customers")  // Replace with your actual endpoint
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{ \"triggerException\": true }"))  // Adjust as necessary to trigger IllegalArgumentException
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.errors").exists())
                .andExpect(jsonPath("$.errors").isArray())
                .andExpect(jsonPath("$.errors.length()").value(3));
    }

    @Test
    void whenRuntimeException_thenReturnsInternalServerError() throws Exception {
        // Simulate endpoint that throws RuntimeException
        mockMvc.perform(get("/api/v1/revenue/day?date=2024-07-252") // Adjust path to your actual endpoint
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isInternalServerError())
                .andExpect(jsonPath("$.errors").exists())
                .andExpect(jsonPath("$.errors").isArray())
                .andExpect(jsonPath("$.errors.length()").value(1)); // Replace with your actual message
    }

    @Test
    void whenInvoiceNotFoundException_thenReturnsNotFound() throws Exception {
        // Simulate endpoint that throws InvoiceNotFoundException
        mockMvc.perform(get("/api/v1/invoices/af2bccfe-55e9-44f7-8663-ee5a0573ce98") // Adjust path to your actual endpoint
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.errors").exists())
                .andExpect(jsonPath("$.errors").isArray())
                .andExpect(jsonPath("$.errors.length()").value(1))
                .andExpect(jsonPath("$.errors[0]").value("Invoice not found")); // Replace with your actual message
    }
}
