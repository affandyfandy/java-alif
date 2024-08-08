package MidtermExam.Group2.repository;

import MidtermExam.Group2.entity.*;
import org.assertj.core.api.AssertionsForClassTypes;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
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
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class InvoiceProductRepositoryTest {
    @Autowired
    private InvoiceProductRepository invoiceProductRepository;

    @Autowired
    private InvoiceRepository invoiceRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private CustomerRepository customerRepository;

    private InvoiceProduct invoiceProduct;
    private Customer customer;
    private Invoice invoice;
    private Product product;

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
        invoiceRepository.save(invoice);

        product = new Product();
        product.setName("Product A");
        product.setPrice(new BigDecimal(1000));
        product.setStatus(Status.ACTIVE);
        productRepository.save(product);

        invoiceProduct = new InvoiceProduct();
        invoiceProduct.setInvoice(invoice);
        invoiceProduct.setProduct(product);
        invoiceProduct.setProductName(product.getName());
        invoiceProduct.setQuantity(1);
        invoiceProduct.setAmount(product.getPrice().multiply(BigDecimal.valueOf(invoiceProduct.getQuantity())));
    }

    // JUnit Test for save invoice product operation
    @Test
    @Order(1)
    void givenInvoiceProduct_whenSaveInvoiceProduct_thenReturnSavedInvoiceProduct() {
        // When: saving the invoice product
        InvoiceProduct savedInvoiceProduct = invoiceProductRepository.save(invoiceProduct);

        // Then: the retrieved invoice product should be present and equal to the saved invoice product
        Optional<InvoiceProduct> foundInvoiceProduct = invoiceProductRepository.findById(new InvoiceProductId(invoiceProduct.getInvoice().getId(), invoiceProduct.getProduct().getId()));
        assertThat(foundInvoiceProduct).isPresent();
        assertThat(foundInvoiceProduct.get()).isEqualTo(savedInvoiceProduct);
    }

    // JUnit Test for find all invoice products operation
    @Test
    void givenInvoiceProductList_whenFindAll_thenReturnInvoiceProductList() {
        // Given: multiple invoice products to be saved
        Product productTwo = new Product();
        productTwo.setName("Product B");
        productTwo.setPrice(new BigDecimal(2000));
        productTwo.setStatus(Status.ACTIVE);

        InvoiceProduct invoiceProductTwo = new InvoiceProduct(
                this.invoice,
                productTwo,
                productTwo.getName(),
                1,
                new BigDecimal(2000),
                LocalDateTime.now(),
                LocalDateTime.now()
        );

        productRepository.save(productTwo);
        invoiceProductRepository.save(this.invoiceProduct);
        invoiceProductRepository.save(invoiceProductTwo);

        invoice.setInvoiceAmount(invoiceProductRepository.calculateTotalAmountByInvoiceId(invoice.getId()));
        invoiceRepository.save(invoice);

        // When: retrieving all invoice products
        List<InvoiceProduct> invoiceProducts = invoiceProductRepository.findAll();

        // Then: the invoice product list should not be empty and should contain 2 invoice products
        assertThat(invoiceProducts).isNotEmpty();
        assertThat(invoiceProducts.size()).isEqualTo(2);
    }

    // JUnit Test for get invoice product by id operation
    @Test
    void givenInvoiceProductId_whenFindById_thenReturnInvoiceProduct() {
        // Given: an invoice product is saved
        InvoiceProduct savedInvoiceProduct = invoiceProductRepository.save(invoiceProduct);
        InvoiceProductId invoiceProductId = new InvoiceProductId(invoiceProduct.getInvoice().getId(), invoiceProduct.getProduct().getId());

        // When: retrieving the invoice product by ID
        Optional<InvoiceProduct> foundInvoiceProduct = invoiceProductRepository.findById(invoiceProductId);

        // Then: the retrieved invoice product should be present and equal to the saved invoice product
        assertThat(foundInvoiceProduct).isPresent();
        assertThat(foundInvoiceProduct.get()).isEqualTo(savedInvoiceProduct);
    }

    // JUnit Test for delete invoice product operation
    @Test
    void givenInvoiceProduct_whenDeleteInvoiceProduct_thenInvoiceProductNotFound() {
        // Given: an invoice product is saved
        InvoiceProduct savedInvoiceProduct = invoiceProductRepository.save(invoiceProduct);

        // When: deleting the saved invoice product
        invoiceProductRepository.delete(savedInvoiceProduct);

        // Then: the retrieved invoice product should not be present
        Optional<InvoiceProduct> foundInvoiceProduct = invoiceProductRepository.findById(new InvoiceProductId(invoiceProduct.getInvoice().getId(), invoiceProduct.getProduct().getId()));
        assertThat(foundInvoiceProduct).isNotPresent();
    }

    // JUnit Test for calculate total amount by invoice id operation
    @Test
    void givenInvoiceId_whenCalculateTotalAmountByInvoiceId_thenReturnTotalAmount() {
        // Given: an invoice product is saved
        invoiceProductRepository.save(invoiceProduct);

        // When: calculating the total amount for the invoice
        BigDecimal totalAmount = invoiceProductRepository.calculateTotalAmountByInvoiceId(invoiceProduct.getInvoice().getId());

        // Then: the total amount should be equal to the product price
        assertThat(totalAmount).isEqualTo(new BigDecimal("1000.00"));
    }
}
