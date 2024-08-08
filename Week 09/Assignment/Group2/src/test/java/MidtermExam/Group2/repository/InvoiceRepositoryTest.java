package MidtermExam.Group2.repository;

import MidtermExam.Group2.entity.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(SpringExtension.class)
@DataJpaTest
public class InvoiceRepositoryTest {
    @Autowired
    private InvoiceRepository invoiceRepository;

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private InvoiceProductRepository invoiceProductRepository;

    @Autowired
    private ProductRepository productRepository;

    private Invoice invoice;
    private Customer customer;

    @BeforeEach
    void setUp() {
        customer = new Customer();
        customer.setName("Alif T");
        customer.setPhoneNumber("+6281234567890");
        customer.setStatus(Status.ACTIVE);
        customerRepository.save(customer);

        invoice = new Invoice();
        invoice.setCustomer(customer);
        invoice.setInvoiceAmount(new BigDecimal(0));
        invoice.setInvoiceDate(LocalDateTime.now());
        invoice.setInvoiceProducts(Collections.emptyList());
    }

    // JUnit Test for save invoice operation
    @Test
    void givenInvoice_whenSaveInvoice_thenReturnSavedInvoice() {
        // When: saving the invoice
        Invoice savedInvoice = invoiceRepository.save(invoice);

        // Then: the saved invoice should be the same as the invoice
        Optional<Invoice> foundInvoice = invoiceRepository.findById(savedInvoice.getId());
        assertThat(foundInvoice).isPresent();
        assertThat(foundInvoice.get()).isEqualTo(savedInvoice);
    }

    // JUnit Test for find all invoices operation
    @Test
    void givenInvoices_whenFindAllInvoices_thenReturnInvoiceList() {
        // Given: saving the invoice
        Invoice savedInvoice = invoiceRepository.save(invoice);

        // When: retrieving all invoices
        List<Invoice> invoices = invoiceRepository.findAll();

        // Then: the list of invoices should contain the saved invoice, not be empty, and have a size of 1
        assertThat(invoices).isNotEmpty();
        assertThat(invoices).contains(savedInvoice);
        assertThat(invoices).hasSize(1);
    }

    // JUnit Test for find invoice by id operation
    @Test
    void givenInvoiceId_whenFindInvoiceById_thenReturnInvoice() {
        // Given: saving the invoice and retrieving the ID
        Invoice savedInvoice = invoiceRepository.save(invoice);
        UUID invoiceId = savedInvoice.getId();

        // When: retrieving the invoice by id
        Optional<Invoice> foundInvoice = invoiceRepository.findById(invoiceId);

        // Then: the found invoice should be the same as the saved invoice
        assertThat(foundInvoice).isPresent();
        assertThat(foundInvoice.get()).isEqualTo(savedInvoice);
    }

    // JUnit Test for update invoice operation
    @Test
    void givenInvoice_whenUpdateInvoice_thenReturnUpdatedInvoice() {
        // Given: saving the invoice
        Invoice savedInvoice = invoiceRepository.save(invoice);

        // When: updating the invoice
        savedInvoice.setInvoiceAmount(new BigDecimal(1000));
        Invoice updatedInvoice = invoiceRepository.save(savedInvoice);

        // Then: the updated invoice should not be null and equal to the saved invoice
        assertThat(updatedInvoice).isNotNull();
        assertThat(updatedInvoice).isEqualTo(savedInvoice);
    }

    // JUnit Test for delete invoice operation
    @Test
    void givenInvoice_whenDeleteInvoice_thenInvoiceShouldNotExist() {
        // Given: saving the invoice
        Invoice savedInvoice = invoiceRepository.save(invoice);

        // When: deleting the saved invoice
        invoiceRepository.delete(savedInvoice);

        // Then: the invoice should not exist
        Optional<Invoice> foundInvoice = invoiceRepository.findById(savedInvoice.getId());
        assertThat(foundInvoice).isNotPresent();
    }

    // JUnit Test for find all invoices with pagination operation
    @Test
    void givenMultipleEntity_whenFindAllInvoicesWithPagination_thenReturnInvoicePage() {
        // Given: saving the invoice, customer, product, and invoice product
        invoiceRepository.save(this.invoice);

        Customer customer = new Customer();
        customer.setName("Alif T");
        customer.setPhoneNumber("+6281234567890");
        customer.setStatus(Status.ACTIVE);
        customerRepository.save(customer);

        Product product = new Product();
        product.setName("Product A");
        product.setPrice(new BigDecimal(1000));
        product.setStatus(Status.ACTIVE);
        productRepository.save(product);

        InvoiceProduct invoiceProduct = new InvoiceProduct();
        invoiceProduct.setProduct(product);
        invoiceProduct.setInvoice(this.invoice);
        invoiceProduct.setProductName(product.getName());
        invoiceProduct.setQuantity(1);
        invoiceProduct.setAmount(product.getPrice().multiply(BigDecimal.valueOf(invoiceProduct.getQuantity())));
        invoiceProductRepository.save(invoiceProduct);

        // When: retrieving all invoices with pagination
        Pageable pageable = PageRequest.of(0, 10);
        Page<Invoice> invoices = invoiceRepository.findAll(pageable);

        // Then
        assertThat(invoices).isNotEmpty();
        assertThat(invoices).hasSize(1);
        assertThat(invoices).contains(this.invoice);
    }

    // method: Invoice findInvoiceById(UUID id);
    // JUnit Test for find invoice by id operation
    @Test
    void givenInvoiceId_whenFindInvoiceByIdQuery_thenReturnInvoice() {
        // Given: saving the invoice and retrieving the ID
        Invoice savedInvoice = invoiceRepository.save(this.invoice);

        Customer customer = new Customer();
        customer.setName("Alif T");
        customer.setPhoneNumber("+6281234567890");
        customer.setStatus(Status.ACTIVE);
        customerRepository.save(customer);

        Product product = new Product();
        product.setName("Product A");
        product.setPrice(new BigDecimal(1000));
        product.setStatus(Status.ACTIVE);
        productRepository.save(product);

        InvoiceProduct invoiceProduct = new InvoiceProduct();
        invoiceProduct.setProduct(product);
        invoiceProduct.setInvoice(this.invoice);
        invoiceProduct.setProductName(product.getName());
        invoiceProduct.setQuantity(1);
        invoiceProduct.setAmount(product.getPrice().multiply(BigDecimal.valueOf(invoiceProduct.getQuantity())));
        invoiceProductRepository.save(invoiceProduct);

        UUID invoiceId = savedInvoice.getId();

        // When: retrieving the invoice by id
        Invoice foundInvoice = invoiceRepository.findInvoiceById(invoiceId);
        Optional<Invoice> foundInvoiceOptional = invoiceRepository.findById(invoiceId);

        // Then: the found invoice should be the same as the saved invoice
        System.out.println("savedInvoice: " + savedInvoice);
        System.out.println("foundInvoice: " + foundInvoice);
        System.out.println("foundInvoiceOptional: " + foundInvoiceOptional);
        assertThat(foundInvoice).isEqualTo(savedInvoice);
    }

    // method: List<Invoice> findByCustomerAndDate(@Param("customerId") UUID customerId, @Param("month") Integer month, @Param("year") Integer year)
    // JUnit Test for find invoices by customer id and date operation
    @Test
    void givenCustomerIdMonthYear_whenFindByCustomerAndDate_thenReturnInvoiceList() {
        // Given: saving the invoice and retrieving the ID
        Invoice savedInvoice = invoiceRepository.save(invoice);
        UUID customerId = savedInvoice.getCustomer().getId();
        Integer month = savedInvoice.getInvoiceDate().getMonthValue();
        Integer year = savedInvoice.getInvoiceDate().getYear();

        // When: retrieving the invoice by customer ID, month, and year
        List<Invoice> foundInvoices = invoiceRepository.findByCustomerAndDate(customerId, month, year);

        // Then: the found invoices should not be empty, contain the saved invoice, and have a size of 1
        assertThat(foundInvoices).isNotEmpty();
        assertThat(foundInvoices).contains(savedInvoice);
        assertThat(foundInvoices).hasSize(1);
    }

    // method: List<Invoice> findByCustomerId(UUID customerId);
    // JUnit Test for find invoices by customer id operation
    @Test
    void givenCustomerId_whenFindByCustomerId_thenReturnInvoiceList() {
        // Given: saving the invoice and retrieving the ID
        Invoice savedInvoice = invoiceRepository.save(invoice);
        UUID customerId = savedInvoice.getCustomer().getId();

        // When: retrieving the invoice by customer ID
        List<Invoice> foundInvoices = invoiceRepository.findByCustomerId(customerId);

        // Then: the found invoices should not be empty, contain the saved invoice, and have a size of 1
        assertThat(foundInvoices).isNotEmpty();
        assertThat(foundInvoices).contains(savedInvoice);
        assertThat(foundInvoices).hasSize(1);
    }

    // method: BigDecimal calculateTotalRevenueByDateTime(LocalDateTime startDateTime, LocalDateTime endDateTime);
    // JUnit Test for calculate total revenue by date time operation
    @Test
    void givenStartDateTimeEndDateTime_whenCalculateTotalRevenueByDateTime_thenReturnTotalRevenue() {
        // Given: saving the invoice and retrieving the ID
        Invoice savedInvoice = invoiceRepository.save(invoice);
        LocalDateTime startDateTime = savedInvoice.getInvoiceDate().minusDays(1);
        LocalDateTime endDateTime = savedInvoice.getInvoiceDate().plusDays(1);

        // When: calculating the total revenue by date time
        BigDecimal totalRevenue = invoiceRepository.calculateTotalRevenueByDateTime(startDateTime, endDateTime);

        // Then: the total revenue should not be null and equal to the invoice amount
        assertThat(totalRevenue).isNotNull();
        assertThat(totalRevenue).isEqualTo(savedInvoice.getInvoiceAmount().setScale(2));
    }
}
