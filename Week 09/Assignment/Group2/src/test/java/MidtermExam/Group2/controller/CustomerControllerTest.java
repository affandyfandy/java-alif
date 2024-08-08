package MidtermExam.Group2.controller;

import MidtermExam.Group2.dto.CustomerDTO;
import MidtermExam.Group2.mapper.CustomerMapper;
import MidtermExam.Group2.service.CustomerService;
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

import java.util.Collections;
import java.util.Optional;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@WebMvcTest(controllers = CustomerController.class)
public class CustomerControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CustomerService customerService;

    private CustomerDTO customerDTO;
    private Pageable pageable;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        customerDTO = new CustomerDTO(UUID.randomUUID(), "Alif T", "+6281234567890", "ACTIVE");
        pageable = PageRequest.of(0, 10);
    }

    @Test
    void testGetAllCustomers() throws Exception{
        // Given
        Page<CustomerDTO> page = new PageImpl<>(Collections.singletonList(customerDTO), pageable, 1);

        // Prepare the mock data
        when(customerService.getAllCustomers(any())).thenReturn(page);

        // Perform the GET request and verify the response
        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/customers")
                .contentType("application/json"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.content[0].id").value(customerDTO.getId().toString()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.content[0].name").value(customerDTO.getName()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.content[0].phoneNumber").value(customerDTO.getPhoneNumber()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.content[0].status").value(customerDTO.getStatus()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.pageable.pageSize").value(pageable.getPageSize()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.pageable.pageNumber").value(pageable.getPageNumber()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.totalElements").value(page.getTotalElements()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.totalPages").value(page.getTotalPages()));
    }

    @Test
    void testGetCustomerById() throws Exception {
        // Given
        UUID id = customerDTO.getId();

        // Prepare the mock data
        when(customerService.getCustomerById(id)).thenReturn(Optional.of(customerDTO));

        // Perform the GET request and verify the response
        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/customers/{id}", id)
                .contentType("application/json"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(customerDTO.getId().toString()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value(customerDTO.getName()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.phoneNumber").value(customerDTO.getPhoneNumber()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.status").value(customerDTO.getStatus()));
    }

    @Test
    void testCreateCustomer() throws Exception {
        // Prepare the mock data
        when(customerService.createCustomer(customerDTO)).thenReturn(customerDTO);

        // Perform the POST request and verify the response
        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/customers")
                .contentType("application/json")
                .content("{\"id\":\"" + customerDTO.getId() + "\",\"name\":\"" + customerDTO.getName() + "\",\"phoneNumber\":\"" + customerDTO.getPhoneNumber() + "\",\"status\":\"" + customerDTO.getStatus() + "\"}"))
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(customerDTO.getId().toString()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value(customerDTO.getName()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.phoneNumber").value(customerDTO.getPhoneNumber()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.status").value(customerDTO.getStatus()));
    }

    @Test
    void testEditCustomer() throws Exception {
        // Given
        UUID id = customerDTO.getId();
        CustomerDTO updatedCustomerDTO = new CustomerDTO(id, "Alif Updated", "+6281234567890", "ACTIVE");

        // Prepare the mock data
        when(customerService.editCustomer(id, updatedCustomerDTO)).thenReturn(Optional.of(updatedCustomerDTO));

        // Perform the PUT request and verify the response
        mockMvc.perform(MockMvcRequestBuilders.put("/api/v1/customers/{id}", id)
                .contentType("application/json")
                .content("{\"id\":\"" + id + "\",\"name\":\"Alif Updated\",\"phoneNumber\":\"+6281234567890\",\"status\":\"ACTIVE\"}"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(updatedCustomerDTO.getId().toString()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value(updatedCustomerDTO.getName()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.phoneNumber").value(updatedCustomerDTO.getPhoneNumber()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.status").value(updatedCustomerDTO.getStatus()));
    }

    @Test
    void testChangeCustomerStatus() throws Exception {
        // Given
        UUID id = customerDTO.getId();
        CustomerDTO changedCustomerDTO = new CustomerDTO(id, "Alif T", "+6281234567890", "INACTIVE");

        // Prepare the mock data
        when(customerService.changeCustomerStatus(id)).thenReturn(Optional.of(changedCustomerDTO));

        // Perform the PATCH request and verify the response
        mockMvc.perform(MockMvcRequestBuilders.patch("/api/v1/customers/{id}", id)
                .contentType("application/json"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(changedCustomerDTO.getId().toString()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.status").value(changedCustomerDTO.getStatus()));
    }

    @Test
    void testDeleteCustomer() throws Exception {
        // Given
        UUID id = customerDTO.getId();

        // Prepare the mock data
        when(customerService.getCustomerById(id)).thenReturn(Optional.of(customerDTO));

        // Perform the DELETE request and verify the response
        mockMvc.perform(MockMvcRequestBuilders.delete("/api/v1/customers/{id}", id)
                .contentType("application/json"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string("Customer record deleted successfully."));
    }
}
