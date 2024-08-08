package MidtermExam.Group2.repository;

import MidtermExam.Group2.entity.Customer;
import MidtermExam.Group2.entity.Status;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(SpringExtension.class)
@DataJpaTest
public class CustomerRepositoryTest {

    @Autowired
    private CustomerRepository customerRepository;

    private Customer customer;

    @BeforeEach
    void setUp() {
        customer = new Customer(
                UUID.randomUUID(),
                "Alif T",
                "+6281234567890",
                Status.ACTIVE,
                LocalDateTime.now(),
                LocalDateTime.now(),
                null
        );
    }

    // JUnit Test for save customer operation
    @Test
    void givenCustomer_whenSaveCustomer_thenReturnSavedCustomer() {
        // When: saving the customer and retrieving it by ID
        Customer savedCustomer = customerRepository.save(customer);
        Optional<Customer> foundCustomer = customerRepository.findById(savedCustomer.getId());

        // Then: the retrieved customer should be present and equal to the saved customer
        assertThat(foundCustomer).isPresent();
        assertThat(foundCustomer.get()).isEqualTo(savedCustomer);
    }

    // JUnit Test for get customer list operation
    @Test
    void givenCustomerList_whenFindAll_thenReturnCustomerList() {
        // Given: multiple customers to be saved
        Customer customerOne = new Customer(
                UUID.randomUUID(),
                "Budi",
                "+6281234567890",
                Status.ACTIVE,
                LocalDateTime.now(),
                LocalDateTime.now(),
                null
        );

        Customer customerTwo = new Customer(
                UUID.randomUUID(),
                "Caca",
                "+6281234567890",
                Status.ACTIVE,
                LocalDateTime.now(),
                LocalDateTime.now(),
                null
        );

        customerRepository.save(customerOne);
        customerRepository.save(customerTwo);

        // When: retrieving all customers
        List<Customer> customerList = customerRepository.findAll();

        // Then: the customer list should not be empty and have a size of 2
        assertThat(customerList).isNotEmpty();
        assertThat(customerList.size()).isEqualTo(2);
    }

    // JUnit Test for find customer by id operation
    @Test
    void givenCustomerId_whenFindById_thenReturnCustomer() {
        // Given: a customer is saved
        Customer savedCustomer = customerRepository.save(customer);

        // When: retrieving the customer by ID
        Optional<Customer> foundCustomer = customerRepository.findById(savedCustomer.getId());

        // Then: the retrieved customer should be present and equal to the saved customer
        assertThat(foundCustomer).isPresent();
        assertThat(foundCustomer.get()).isEqualTo(savedCustomer);
    }

    // JUnit Test for update customer operation
    @Test
    void givenCustomer_whenUpdateCustomer_thenReturnUpdatedCustomer() {
        // Given: a saved customer
        Customer savedCustomer = customerRepository.save(customer);

        // When: updating the customer's details
        savedCustomer.setName("Alif Update");
        savedCustomer.setPhoneNumber("+6281234567891");
        savedCustomer.setStatus(Status.INACTIVE);

        Customer updatedCustomer = customerRepository.save(savedCustomer);

        // Then: the updated customer should not be null and have the updated details
        assertThat(updatedCustomer).isNotNull();
        assertThat(updatedCustomer.getName()).isEqualTo("Alif Update");
        assertThat(updatedCustomer.getPhoneNumber()).isEqualTo("+6281234567891");
        assertThat(updatedCustomer.getStatus()).isEqualTo(Status.INACTIVE);
    }

    // JUnit Test for delete customer operation
    @Test
    void givenCustomerId_whenDeleteCustomer_thenCustomerShouldBeDeleted() {
        // Given: a saved customer
        Customer savedCustomer = customerRepository.save(customer);

        // When: deleting the customer by ID and retrieving it
        customerRepository.deleteById(savedCustomer.getId());
        Optional<Customer> foundCustomer = customerRepository.findById(savedCustomer.getId());

        // Then: the retrieved customer should not be present
        assertThat(foundCustomer).isNotPresent();
    }

    // JUnit Test for find customer by name operation
    @Test
    void givenCustomerName_whenFindByName_thenReturnCustomer() {
        // Given: a customer is saved
        Customer savedCustomer = customerRepository.save(customer);

        // When: retrieving the customer by name
        Optional<Customer> foundCustomer = customerRepository.findByName(savedCustomer.getName());

        // Then: the retrieved customer should be present and equal to the saved customer
        assertThat(foundCustomer).isPresent();
        assertThat(foundCustomer.get()).isEqualTo(savedCustomer);
    }
}
