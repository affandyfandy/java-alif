package MidtermExam.Group2.mapper;

import MidtermExam.Group2.dto.CustomerDTO;
import MidtermExam.Group2.dto.InvoiceDTO;
import MidtermExam.Group2.dto.InvoiceDetailDTO;
import MidtermExam.Group2.dto.InvoiceListDTO;
import MidtermExam.Group2.entity.Customer;
import MidtermExam.Group2.entity.Invoice;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;

class InvoiceMapperTest {

    private InvoiceMapper invoiceMapper;
    private CustomerMapper customerMapper;
    private InvoiceProductMapper invoiceProductMapper;

    @BeforeEach
    void setUp() {

        customerMapper = Mappers.getMapper(CustomerMapper.class);
        invoiceProductMapper = Mappers.getMapper(InvoiceProductMapper.class);
        invoiceMapper = new InvoiceMapperImpl(customerMapper, invoiceProductMapper);
    }

    @Test
    void testToInvoiceListDTO() {
        // Given
        Customer customer = new Customer();
        customer.setName("John Doe");

        Invoice invoice = new Invoice();
        invoice.setCustomer(customer);
        invoice.setId(UUID.randomUUID());
        invoice.setInvoiceDate(LocalDateTime.now());
        invoice.setInvoiceAmount(BigDecimal.valueOf(123.45));

        // When
        InvoiceListDTO result = invoiceMapper.toInvoiceListDTO(invoice);

        // Then
        assertEquals(invoice.getCustomer().getName(), result.getCustomerName());
        assertEquals(invoice.getInvoiceAmount(), result.getInvoiceAmount());
        assertEquals(invoice.getInvoiceDate().toLocalDate(), result.getInvoiceDate());
    }

    @Test
    void testToInvoice_ID1() {
        // Given
        InvoiceListDTO invoiceListDTO = new InvoiceListDTO();
        invoiceListDTO.setCustomerName("John Doe");
        invoiceListDTO.setInvoiceDate(LocalDateTime.now().toLocalDate());
        invoiceListDTO.setInvoiceAmount(BigDecimal.valueOf(123.45));

        // When
        Invoice result = invoiceMapper.toInvoice(invoiceListDTO);

        // Then
        assertEquals(invoiceListDTO.getCustomerName(), result.getCustomer().getName());
        assertEquals(invoiceListDTO.getInvoiceAmount(), result.getInvoiceAmount());
        assertEquals(invoiceListDTO.getInvoiceDate().atStartOfDay(), result.getInvoiceDate());
    }

    @Test
    void testToInvoicesDTO() {
        // Given
        Customer customer = new Customer();
        customer.setId(UUID.randomUUID());
        customer.setName("John Doe");

        Invoice invoice = new Invoice();
        invoice.setCustomer(customer);
        invoice.setId(UUID.randomUUID());
        invoice.setInvoiceDate(LocalDateTime.now());
        invoice.setInvoiceAmount(BigDecimal.valueOf(123.45));

        // When
        InvoiceDTO result = invoiceMapper.toInvoicesDTO(invoice);

        // Then
        assertEquals(invoice.getCustomer().getId(), result.getCustomerId());
        assertEquals(invoice.getInvoiceAmount(), result.getInvoiceAmount());
        assertEquals(invoice.getInvoiceDate().toLocalDate(), result.getInvoiceDate());
    }

    @Test
    void testToInvoices() {
        // Given
        InvoiceDTO invoiceDTO = new InvoiceDTO();
        invoiceDTO.setCustomerId(UUID.randomUUID());
        invoiceDTO.setInvoiceDate(LocalDateTime.now().toLocalDate());
        invoiceDTO.setInvoiceAmount(BigDecimal.valueOf(123.45));

        // When
        Invoice result = invoiceMapper.toInvoices(invoiceDTO);

        System.out.println(result.getInvoiceDate());
        System.out.println(result.getInvoiceAmount());
        System.out.println(invoiceDTO.toString());

        // Then
        assertEquals(invoiceDTO.getCustomerId(), result.getCustomer().getId());
        assertEquals(invoiceDTO.getInvoiceAmount(), result.getInvoiceAmount());
        assertEquals(invoiceDTO.getInvoiceDate(), result.getInvoiceDate().toLocalDate());
    }

    @Test
    void testToInvoiceDetailDTO() {
        // Given
        Customer customer = new Customer();
        customer.setId(UUID.randomUUID());
        customer.setName("John Doe");

        CustomerDTO customerDTO = new CustomerDTO();
        customerDTO.setId(customer.getId());
        customerDTO.setName(customer.getName());

        Invoice invoice = new Invoice();
        invoice.setCustomer(customer);
        invoice.setId(UUID.randomUUID());
        invoice.setInvoiceDate(LocalDateTime.now());
        invoice.setInvoiceAmount(BigDecimal.valueOf(123.45));

        // When
        InvoiceDetailDTO result = invoiceMapper.toInvoiceDetailDTO(invoice);

        // Then
        assertEquals(invoice.getId(), result.getInvoiceId());
        assertEquals(invoice.getCustomer().getId(), result.getCustomer().getId());
        assertEquals(invoice.getInvoiceAmount(), result.getInvoiceAmount());
        assertEquals(invoice.getInvoiceDate().toLocalDate(), result.getInvoiceDate());
    }

    @Test
    void testToInvoice_ID2() {
        // Given
        InvoiceDetailDTO invoiceDetailDTO = new InvoiceDetailDTO();
        invoiceDetailDTO.setCustomer(new CustomerDTO());
        invoiceDetailDTO.getCustomer().setId(UUID.randomUUID());
        invoiceDetailDTO.getCustomer().setName("John Doe");
        invoiceDetailDTO.setInvoiceAmount(new BigDecimal("10.00"));
        invoiceDetailDTO.setInvoiceDate(LocalDateTime.now().toLocalDate());

        Customer customer = new Customer();
        customer.setId(invoiceDetailDTO.getCustomer().getId());

        // When
        Invoice result = invoiceMapper.toInvoice(invoiceDetailDTO);

        // Then
        assertEquals(invoiceDetailDTO.getCustomer().getId(), result.getCustomer().getId());
        assertEquals(invoiceDetailDTO.getInvoiceAmount(), result.getInvoiceAmount());
        assertEquals(invoiceDetailDTO.getInvoiceDate(), result.getInvoiceDate().toLocalDate());
    }
}
