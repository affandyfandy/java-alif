package MidtermExam.Group2.mapper;

import MidtermExam.Group2.dto.CustomerDTO;
import MidtermExam.Group2.entity.Customer;
import MidtermExam.Group2.entity.Status;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = CustomerMapper.class)
class CustomerMapperTest {
    @MockBean
    private CustomerMapper customerMapper;

    @Test
    void testToDTO() {
        // Given
        Customer customer = new Customer();
        customer.setId(UUID.randomUUID());
        customer.setName("John Doe");
        customer.setPhoneNumber("1234567890");
        customer.setStatus(Status.ACTIVE);

        CustomerDTO customerDTO = new CustomerDTO();
        customerDTO.setId(customer.getId());
        customerDTO.setName(customer.getName());
        customerDTO.setPhoneNumber(customer.getPhoneNumber());
        customerDTO.setStatus(customer.getStatus().toString());

        // When
        when(customerMapper.toDTO(customer)).thenReturn(customerDTO);
        CustomerDTO result = customerMapper.toDTO(customer);

        // Then
        assertEquals(customerDTO, result);
    }

    @Test
    void testToEntity() {
        // Given
        CustomerDTO customerDTO = new CustomerDTO();
        customerDTO.setId(UUID.randomUUID());
        customerDTO.setName("John Doe");
        customerDTO.setPhoneNumber("1234567890");
        customerDTO.setStatus(Status.ACTIVE.name());

        Customer customer = new Customer();
        customer.setId(customerDTO.getId());
        customer.setName(customerDTO.getName());
        customer.setPhoneNumber(customerDTO.getPhoneNumber());
        customer.setStatus(Status.valueOf(customerDTO.getStatus()));

        // When
        when(customerMapper.toEntity(customerDTO)).thenReturn(customer);
        Customer result = customerMapper.toEntity(customerDTO);

        // Then
        assertEquals(customer, result);
    }
}
