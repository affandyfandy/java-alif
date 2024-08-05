package MidtermExam.Group2.controller;

import MidtermExam.Group2.dto.CustomerDTO;
import MidtermExam.Group2.service.CustomerService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/customers")
public class CustomerController {

    private final CustomerService customerService;

    @Autowired
    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    @GetMapping
    public ResponseEntity<Page<CustomerDTO>> getAllCustomers(Pageable pageable) {
        Page<CustomerDTO> customers = customerService.getAllCustomers(pageable);
        return ResponseEntity.ok(customers);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CustomerDTO> getCustomerById(@PathVariable UUID id) {
        Optional<CustomerDTO> customer = customerService.getCustomerById(id);
        return customer.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<CustomerDTO> createCustomer(@Valid @RequestBody CustomerDTO customerDTO) {
        CustomerDTO createdCustomer = customerService.createCustomer(customerDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdCustomer);
    }

    @PutMapping("/{id}")
    public ResponseEntity<CustomerDTO> editCustomer(@PathVariable UUID id, @Valid @RequestBody CustomerDTO customerDTO) {
        Optional<CustomerDTO> updatedCustomer = customerService.editCustomer(id, customerDTO);
        return updatedCustomer.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PatchMapping("/{id}")
    public ResponseEntity<?> changeCustomerStatus(@PathVariable UUID id) {
        Optional<CustomerDTO> updatedCustomer = customerService.changeCustomerStatus(id);
        if (updatedCustomer.isPresent()) {
            return ResponseEntity.ok(updatedCustomer.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteCustomer(@PathVariable UUID id) {
        Optional<CustomerDTO> customer = customerService.getCustomerById(id);

        if (customer.isPresent()) {
            customerService.deleteCustomer(id);
            return ResponseEntity.ok("Customer record deleted successfully.");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Customer not found.");
        }
    }
}
