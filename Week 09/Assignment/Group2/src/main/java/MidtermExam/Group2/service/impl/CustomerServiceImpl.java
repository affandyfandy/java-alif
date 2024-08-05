package MidtermExam.Group2.service.impl;

import MidtermExam.Group2.dto.CustomerDTO;
import MidtermExam.Group2.entity.Customer;
import MidtermExam.Group2.entity.Status;
import MidtermExam.Group2.mapper.CustomerMapper;
import MidtermExam.Group2.repository.CustomerRepository;
import MidtermExam.Group2.service.CustomerService;
import org.springframework.data.domain.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

@Service
@Transactional
public class CustomerServiceImpl implements CustomerService {

    private final CustomerRepository customerRepository;
    private final CustomerMapper customerMapper;

    @Autowired
    public CustomerServiceImpl(CustomerRepository customerRepository, CustomerMapper customerMapper) {
        this.customerRepository = customerRepository;
        this.customerMapper = customerMapper;
    }

    @Override
    public Page<CustomerDTO> getAllCustomers(Pageable pageable) {
        Page<Customer> customers = customerRepository.findAll(pageable);
        return customers.map(customerMapper::toDTO);
    }

    @Override
    public Optional<CustomerDTO> getCustomerById(UUID id) {
        return customerRepository.findById(id).map(customerMapper::toDTO);
    }

    @Override
    public CustomerDTO createCustomer(CustomerDTO customerDTO) {
        Customer customer = customerMapper.toEntity(customerDTO);
        customer.setCreatedTime(LocalDateTime.now());
        customer.setUpdatedTime(LocalDateTime.now());
        customer = customerRepository.save(customer);
        return customerMapper.toDTO(customer);
    }

    @Override
    public Optional<CustomerDTO> editCustomer(UUID id, CustomerDTO customerDTO) {
        return customerRepository.findById(id).map(customer -> {
            customer.setName(customerDTO.getName());
            customer.setPhoneNumber(customerDTO.getPhoneNumber());
            customer.setUpdatedTime(LocalDateTime.now());
            customerRepository.save(customer);
            return customerMapper.toDTO(customer);
        });
    }

    @Override
    public Optional<CustomerDTO> changeCustomerStatus(UUID id) {
        Optional<Customer> existingCustomer = customerRepository.findById(id);
        if (existingCustomer.isPresent()) {
            Customer customer = existingCustomer.get();
            customer.setStatus(customer.getStatus() == Status.ACTIVE ? Status.INACTIVE : Status.ACTIVE);
            customer = customerRepository.save(customer);
            return Optional.of(customerMapper.toDTO(customer));
        } else {
            return Optional.empty();
        }
    }

    @Override
    public void deleteCustomer(UUID id) {
        customerRepository.deleteById(id);
    }


}
