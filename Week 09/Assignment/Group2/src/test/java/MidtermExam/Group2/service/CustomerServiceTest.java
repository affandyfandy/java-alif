package MidtermExam.Group2.service;

import MidtermExam.Group2.dto.CustomerDTO;
import MidtermExam.Group2.entity.Customer;
import MidtermExam.Group2.entity.Status;
import MidtermExam.Group2.mapper.CustomerMapper;
import MidtermExam.Group2.repository.CustomerRepository;
import MidtermExam.Group2.service.impl.CustomerServiceImpl;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;
import java.util.Optional;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {CustomerServiceImpl.class})
public class CustomerServiceTest {

    @Autowired
    private CustomerServiceImpl customerService;

    @MockBean
    private CustomerRepository customerRepository;

    @MockBean
    private CustomerMapper customerMapper;

    private Customer customer;
    private CustomerDTO customerDTO;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        customer = new Customer();
        customer.setName("Alif T");
        customer.setPhoneNumber("+628123456789");
        customer.setStatus(Status.ACTIVE);

        customerDTO = new CustomerDTO();
        customerDTO.setName(customer.getName());
        customerDTO.setPhoneNumber(customer.getPhoneNumber());
        customerDTO.setStatus(customer.getStatus().toString());
    }

    @Test
    void testGetAllCustomers_ReturnPageOfOneCustomer() {
        Pageable pageable = PageRequest.of(0, 1);
        Page<Customer> customerPage = new PageImpl<>(List.of(customer));
        Page<CustomerDTO> customerDTOPage = new PageImpl<>(List.of(customerDTO));

        Mockito.when(customerRepository.findAll(pageable)).thenReturn(customerPage);
        Mockito.when(customerMapper.toDTO(customer)).thenReturn(customerDTO);

        Page<CustomerDTO> result = customerService.getAllCustomers(pageable);

        Assertions.assertThat(result.getContent()).hasSize(1);
        Assertions.assertThat(result.getContent().get(0).getId()).isEqualTo(customer.getId());
    }

    @Test
    void testGetCustomerById_ID1_ReturnCustomer() {
        Mockito.when(customerRepository.findById(customer.getId())).thenReturn(Optional.of(customer));
        Mockito.when(customerMapper.toDTO(customer)).thenReturn(customerDTO);

        Optional<CustomerDTO> result = customerService.getCustomerById(customer.getId());

        Assertions.assertThat(result).isPresent();
        Assertions.assertThat(result.get().getId()).isEqualTo(customer.getId());
        Mockito.verify(customerRepository).findById(customer.getId());
        Mockito.verify(customerMapper).toDTO(customer);
    }

    @Test
    void testGetCustomerById_ID2_ReturnNull() {
        Mockito.when(customerRepository.findById(customer.getId())).thenReturn(Optional.empty());

        Optional<CustomerDTO> result = customerService.getCustomerById(customer.getId());

        Assertions.assertThat(result).isEmpty();
        Mockito.verify(customerRepository).findById(customer.getId());
    }

    @Test
    void testCreateCustomer_ReturnCustomerDTO() {
        Mockito.when(customerMapper.toEntity(customerDTO)).thenReturn(customer);
        Mockito.when(customerRepository.save(customer)).thenReturn(customer);
        Mockito.when(customerMapper.toDTO(customer)).thenReturn(customerDTO);

        CustomerDTO result = customerService.createCustomer(customerDTO);

        Assertions.assertThat(result).isNotNull();
        Assertions.assertThat(result.getId()).isEqualTo(customer.getId());
        Mockito.verify(customerMapper).toEntity(customerDTO);
        Mockito.verify(customerRepository).save(customer);
        Mockito.verify(customerMapper).toDTO(customer);
    }

    @Test
    void testEditCustomer_ID1_ReturnCustomerDTO() {
        CustomerDTO updatedCustomerDTO = new CustomerDTO();
        updatedCustomerDTO.setName("Alif T");
        updatedCustomerDTO.setStatus(Status.ACTIVE.toString());

        Mockito.when(customerRepository.findById(customer.getId())).thenReturn(Optional.of(customer));
        Mockito.when(customerRepository.save(customer)).thenReturn(customer);
        Mockito.when(customerMapper.toDTO(customer)).thenReturn(updatedCustomerDTO);

        Optional<CustomerDTO> result = customerService.editCustomer(customer.getId(), updatedCustomerDTO);

        Assertions.assertThat(result).isPresent();
        Assertions.assertThat(result.get().getId()).isEqualTo(customer.getId());
        Mockito.verify(customerRepository).findById(customer.getId());
        Mockito.verify(customerRepository).save(customer);
        Mockito.verify(customerMapper).toDTO(customer);
    }

    @Test
    void testChangeCustomerStatus_ID1_ReturnCustomerDTO() {
        Mockito.when(customerRepository.findById(customer.getId())).thenReturn(Optional.of(customer));
        Mockito.when(customerRepository.save(customer)).thenReturn(customer);
        Mockito.when(customerMapper.toDTO(customer)).thenReturn(customerDTO);

        Optional<CustomerDTO> result = customerService.changeCustomerStatus(customer.getId());

        Assertions.assertThat(result).isPresent();
        Assertions.assertThat(result.get().getId()).isEqualTo(customer.getId());
        Mockito.verify(customerRepository).findById(customer.getId());
        Mockito.verify(customerRepository).save(customer);
        Mockito.verify(customerMapper).toDTO(customer);
    }

    @Test
    void testChangeCustomerStatus_ID2_ReturnNull() {
        Mockito.when(customerRepository.findById(customer.getId())).thenReturn(Optional.empty());

        Optional<CustomerDTO> result = customerService.changeCustomerStatus(customer.getId());

        Assertions.assertThat(result).isEmpty();
        Mockito.verify(customerRepository).findById(customer.getId());
    }

    @Test
    void testDeleteCustomer_ID1() {
        customerService.deleteCustomer(customer.getId());
        Mockito.verify(customerRepository).deleteById(customer.getId());
    }
}
