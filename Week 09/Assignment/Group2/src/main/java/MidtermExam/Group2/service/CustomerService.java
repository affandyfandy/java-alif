package MidtermExam.Group2.service;

import MidtermExam.Group2.dto.CustomerDTO;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public interface CustomerService {
    Page<CustomerDTO> getAllCustomers(Pageable pageable);
    Optional<CustomerDTO> getCustomerById(UUID id);
    CustomerDTO createCustomer(CustomerDTO customerDTO);
    Optional<CustomerDTO> editCustomer(UUID id, CustomerDTO customerDTO);
    Optional<CustomerDTO> changeCustomerStatus(UUID id);
    void deleteCustomer(UUID id);
}
