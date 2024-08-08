package MidtermExam.Group2.service;

import MidtermExam.Group2.criteria.InvoiceSearchCriteria;
import MidtermExam.Group2.dto.InvoiceDTO;
import MidtermExam.Group2.dto.InvoiceDetailDTO;
import MidtermExam.Group2.dto.InvoiceListDTO;
import MidtermExam.Group2.entity.*;
import MidtermExam.Group2.mapper.CustomerMapper;
import MidtermExam.Group2.mapper.InvoiceMapper;
import MidtermExam.Group2.mapper.InvoiceProductMapper;
import MidtermExam.Group2.repository.CustomerRepository;
import MidtermExam.Group2.repository.InvoiceRepository;
import MidtermExam.Group2.repository.InvoiceSpecification;
import MidtermExam.Group2.service.impl.InvoiceServiceImpl;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
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

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {InvoiceServiceImpl.class})
public class InvoiceServiceTest {

    @Autowired
    private InvoiceServiceImpl invoiceService;

    @MockBean
    private InvoiceRepository invoiceRepository;

    @MockBean
    private CustomerRepository customerRepository;

    @MockBean
    private InvoiceMapper invoiceMapper;

    @MockBean
    private CustomerMapper customerMapper;

    @MockBean
    private InvoiceProductMapper invoiceProductMapper;

    private Invoice invoice;
    private InvoiceDTO invoiceDTO;
    private InvoiceDetailDTO invoiceDetailDTO;
    private InvoiceListDTO invoiceListDTO;

    private Customer customer;
    private Product product;
    private InvoiceProduct invoiceProduct;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        customer = new Customer();
        customer.setId(UUID.randomUUID());
        customer.setName("Alif T");
        customer.setPhoneNumber("+628123456789");
        customer.setStatus(Status.ACTIVE);

        product = new Product();
        product.setId(UUID.randomUUID());
        product.setName("Apple");
        product.setPrice(BigDecimal.valueOf(10000));
        product.setStatus(Status.ACTIVE);

        invoiceProduct = new InvoiceProduct();
        invoiceProduct.setProduct(product);
        invoiceProduct.setQuantity(1);
        invoiceProduct.setProductName(product.getName());
        invoiceProduct.setAmount(product.getPrice().multiply(BigDecimal.valueOf(invoiceProduct.getQuantity())));

        invoice = new Invoice();
        invoice.setId(UUID.randomUUID());
        invoice.setCustomer(customer);
        invoice.setInvoiceProducts(List.of(invoiceProduct));
        invoice.setInvoiceDate(LocalDateTime.now());
        invoice.setInvoiceAmount(invoiceProduct.getAmount());

        invoiceProduct.setInvoice(invoice);
        customer.setInvoice(List.of(invoice));

        invoiceDTO = new InvoiceDTO();
        invoiceDTO.setId(invoice.getId());
        invoiceDTO.setCustomerId(invoice.getCustomer().getId());
        invoiceDTO.setInvoiceDate(invoice.getInvoiceDate().toLocalDate());
        invoiceDTO.setInvoiceAmount(invoice.getInvoiceAmount());

        invoiceDetailDTO = new InvoiceDetailDTO();
        invoiceDetailDTO.setInvoiceId(invoice.getId());
        invoiceDetailDTO.setCustomer(customerMapper.toDTO(customer));
        invoiceDetailDTO.setInvoiceAmount(invoice.getInvoiceAmount());
        invoiceDetailDTO.setProducts(Collections.emptyList());
        invoiceDetailDTO.setInvoiceDate(invoice.getInvoiceDate().toLocalDate());

        invoiceListDTO = new InvoiceListDTO();
        invoiceListDTO.setId(invoice.getId());
        invoiceListDTO.setCustomerName(invoice.getCustomer().getName());
        invoiceListDTO.setInvoiceDate(invoice.getInvoiceDate().toLocalDate());
        invoiceListDTO.setInvoiceAmount(invoice.getInvoiceAmount());
    }

    /**
     * Method under test: {@link InvoiceServiceImpl#getAllInvoices(Pageable, InvoiceSearchCriteria)}
     */
    @Test
    void testGetAllInvoices_ReturnPageOfOneInvoice() {
        Pageable pageable = PageRequest.of(0, 10);
        InvoiceSearchCriteria criteria = new InvoiceSearchCriteria();

        when(invoiceRepository.findAll(any(InvoiceSpecification.class), any(Pageable.class)))
                .thenReturn(new PageImpl<>(Collections.singletonList(invoice)));
        when(invoiceMapper.toInvoiceListDTO(invoice)).thenReturn(invoiceListDTO);

        Page<InvoiceListDTO> result = invoiceService.getAllInvoices(pageable, criteria);

        assertThat(result).isNotNull();
        assertThat(result.getContent()).hasSize(1);
        assertThat(result.getContent().get(0)).isEqualTo(invoiceListDTO);
    }

    /**
     * Method under test: {@link InvoiceServiceImpl#getInvoiceById(UUID)}
     */
    @Test
    void testGetInvoiceById_ReturnInvoice() {
        UUID id = UUID.randomUUID();

        when(invoiceRepository.findById(id)).thenReturn(Optional.of(invoice));
        when(invoiceMapper.toInvoicesDTO(invoice)).thenReturn(invoiceDTO);

        InvoiceDTO result = invoiceService.getInvoiceById(id);

        assertThat(result).isNotNull();
        assertThat(result).isEqualTo(invoiceDTO);
    }

    /**
     * Method under test: {@link InvoiceServiceImpl#getInvoiceById(UUID)}
     */
    @Test
    void testGetInvoiceById_ThrowIllegalArgumentException_InvoiceNotFound() {
        UUID id = UUID.randomUUID();

        when(invoiceRepository.findById(id)).thenReturn(Optional.empty());

        Exception exception =  assertThrows(IllegalArgumentException.class, () -> invoiceService.getInvoiceById(id));

        assertThat(exception.getMessage()).isEqualTo("Invoice not found");
    }

    /**
     * Method under test: {@link InvoiceServiceImpl#addInvoice(InvoiceDTO)}
     */
    @Test
    void testAddInvoice_ReturnInvoiceDTO() {
        when(customerRepository.findById(customer.getId())).thenReturn(Optional.of(customer));
        when(invoiceMapper.toInvoices(invoiceDTO)).thenReturn(invoice);
        when(invoiceRepository.save(invoice)).thenReturn(invoice);
        when(invoiceMapper.toInvoicesDTO(invoice)).thenReturn(invoiceDTO);

        InvoiceDTO result = invoiceService.addInvoice(invoiceDTO);
        System.out.println(result);

        assertThat(result).isNotNull();
        assertThat(result).isEqualTo(invoiceDTO);
    }

    /**
     * Method under test: {@link InvoiceServiceImpl#addInvoice(InvoiceDTO)}
     */
    @Test
    void testAddInvoice_ThrowIllegalArgumentException_CustomerNotFound() {
        when(customerRepository.findById(customer.getId())).thenReturn(Optional.empty());

        Exception exception = assertThrows(IllegalArgumentException.class, () -> invoiceService.addInvoice(invoiceDTO));

        assertThat(exception.getMessage()).isEqualTo("Customer not found");
    }

    /**
     * Method under test: {@link InvoiceServiceImpl#addInvoice(InvoiceDTO)}
     */
    @Test
    void testAddInvoice_ThrowIllegalArgumentException_CustomerInactive() {
        customer.setStatus(Status.INACTIVE);
        when(customerRepository.findById(customer.getId())).thenReturn(Optional.of(customer));

        Exception exception = assertThrows(IllegalArgumentException.class, () -> invoiceService.addInvoice(invoiceDTO));

        assertThat(exception.getMessage()).isEqualTo("Customer is inactive");
    }

    /**
     * Method under test: {@link InvoiceServiceImpl#editInvoice(UUID, InvoiceDTO)}
     */
    @Test
    void testEditInvoice_ReturnInvoiceDTO() {
        invoice.setCreatedTime(LocalDateTime.now().minusMinutes(5));

        when(invoiceRepository.findById(invoice.getId())).thenReturn(Optional.of(invoice));
        when(invoiceMapper.toInvoices(invoiceDTO)).thenReturn(invoice);
        when(customerRepository.findById(customer.getId())).thenReturn(Optional.of(customer));
        when(invoiceRepository.save(invoice)).thenReturn(invoice);
        when(invoiceMapper.toInvoicesDTO(invoice)).thenReturn(invoiceDTO);

        InvoiceDTO result = invoiceService.editInvoice(invoice.getId(), invoiceDTO);

        assertThat(result).isNotNull();
        assertThat(result).isEqualTo(invoiceDTO);
    }

    /**
     * Method under test: {@link InvoiceServiceImpl#editInvoice(UUID, InvoiceDTO)}
     */
    @Test
    void testEditInvoice_ThrowIllegalArgumentException_InvoiceNotFound() {
        UUID id = UUID.randomUUID();

        when(invoiceRepository.findById(id)).thenReturn(Optional.empty());

        Exception exception = assertThrows(IllegalArgumentException.class, () -> invoiceService.editInvoice(id, invoiceDTO));

        assertThat(exception.getMessage()).isEqualTo("Invoice not found");
    }

    /**
     * Method under test: {@link InvoiceServiceImpl#editInvoice(UUID, InvoiceDTO)}
     */
    @Test
    void testEditInvoice_ThrowIllegalArgumentException_InvoiceOlderThan10Minutes() {
        invoice.setCreatedTime(LocalDateTime.now().minusMinutes(15));

        when(invoiceRepository.findById(invoice.getId())).thenReturn(Optional.of(invoice));

        Exception exception = assertThrows(IllegalArgumentException.class, () -> invoiceService.editInvoice(invoice.getId(), invoiceDTO));

        assertThat(exception.getMessage()).isEqualTo("Invoice cannot be edited after 10 minutes");
    }

    /**
     * Method under test: {@link InvoiceServiceImpl#editInvoice(UUID, InvoiceDTO)}
     */
    @Test
    void testEditInvoice_ThrowIllegalArgumentException_CustomerNotFound() {
        invoice.setCreatedTime(LocalDateTime.now().minusMinutes(5));

        when(invoiceRepository.findById(invoice.getId())).thenReturn(Optional.of(invoice));
        when(customerRepository.findById(customer.getId())).thenReturn(Optional.empty());

        Exception exception = assertThrows(IllegalArgumentException.class, () -> invoiceService.editInvoice(invoice.getId(), invoiceDTO));

        assertThat(exception.getMessage()).isEqualTo("Customer not found");
    }

    /**
     * Method under test: {@link InvoiceServiceImpl#editInvoice(UUID, InvoiceDTO)}
     */
    @Test
    void testEditInvoice_ThrowIllegalArgumentException_CustomerInactive() {
        invoice.setCreatedTime(LocalDateTime.now().minusMinutes(5));
        customer.setStatus(Status.INACTIVE);

        when(invoiceRepository.findById(invoice.getId())).thenReturn(Optional.of(invoice));
        when(customerRepository.findById(customer.getId())).thenReturn(Optional.of(customer));

        Exception exception = assertThrows(IllegalArgumentException.class, () -> invoiceService.editInvoice(invoice.getId(), invoiceDTO));

        assertThat(exception.getMessage()).isEqualTo("Customer is inactive");
    }

    /**
     * Method under test: {@link InvoiceServiceImpl#getInvoiceDetail(UUID)}
     */
    @Test
    void testGetInvoiceDetail_ReturnInvoiceDetailDTO() {
        UUID id = UUID.randomUUID();

        when(invoiceRepository.findById(id)).thenReturn(Optional.of(invoice));
        when(invoiceMapper.toInvoiceDetailDTO(invoice)).thenReturn(invoiceDetailDTO);

        InvoiceDetailDTO result = invoiceService.getInvoiceDetail(id);

        assertThat(result).isNotNull();
        assertThat(result).isEqualTo(invoiceDetailDTO);
    }

    /**
     * Method under test: {@link InvoiceServiceImpl#getInvoiceDetail(UUID)}
     */
    @Test
    void testGetInvoiceDetail_ThrowRuntimeException_InvoiceNotFound() {
        UUID id = UUID.randomUUID();

        when(invoiceRepository.findById(id)).thenReturn(Optional.empty());

        Exception exception = assertThrows(RuntimeException.class, () -> invoiceService.getInvoiceDetail(id));

        assertThat(exception.getMessage()).isEqualTo("Invoice not found");
    }
}
