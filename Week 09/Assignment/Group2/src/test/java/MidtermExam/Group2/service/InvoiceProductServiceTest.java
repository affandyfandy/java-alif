package MidtermExam.Group2.service;

import MidtermExam.Group2.dto.InvoiceProductDTO;
import MidtermExam.Group2.entity.*;
import MidtermExam.Group2.mapper.InvoiceProductMapper;
import MidtermExam.Group2.repository.InvoiceProductRepository;
import MidtermExam.Group2.repository.InvoiceRepository;
import MidtermExam.Group2.repository.ProductRepository;
import MidtermExam.Group2.service.impl.InvoiceProductServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
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
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {InvoiceProductServiceImpl.class})
public class InvoiceProductServiceTest {
    @Autowired
    private InvoiceProductServiceImpl invoiceProductService;

    @MockBean
    private InvoiceProductRepository invoiceProductRepository;

    @MockBean
    private InvoiceProductMapper invoiceProductMapper;

    @MockBean
    private InvoiceRepository invoiceRepository;

    @MockBean
    private ProductRepository productRepository;

    private Invoice invoice;
    private Product product;
    private InvoiceProduct invoiceProduct;
    private InvoiceProductDTO invoiceProductDTO;
    private UUID invoiceId;
    private UUID productId;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        invoiceId = UUID.randomUUID();
        productId = UUID.randomUUID();

        invoice = new Invoice();
        invoice.setId(invoiceId);
        invoice.setInvoiceDate(LocalDateTime.now());
        invoice.setInvoiceAmount(new BigDecimal("10.00"));
        invoice.setCreatedTime(LocalDateTime.now());
        invoice.setUpdatedTime(LocalDateTime.now());

        product = new Product();
        product.setId(productId);
        product.setName("Product A");
        product.setPrice(new BigDecimal("10.00"));
        product.setStatus(Status.ACTIVE);

        invoiceProduct = new InvoiceProduct();
        invoiceProduct.setInvoice(invoice);
        invoiceProduct.setProduct(product);
        invoiceProduct.setQuantity(1);
        invoiceProduct.setAmount(invoiceProduct.getProduct().getPrice().multiply(BigDecimal.valueOf(invoiceProduct.getQuantity())));
        invoiceProduct.setCreatedTime(LocalDateTime.now());
        invoiceProduct.setUpdatedTime(LocalDateTime.now());

        invoiceProductDTO = new InvoiceProductDTO();
        invoiceProductDTO.setInvoiceId(invoiceProduct.getInvoice().getId());
        invoiceProductDTO.setProductId(invoiceProduct.getProduct().getId());
        invoiceProductDTO.setQuantity(invoiceProduct.getQuantity());
        invoiceProductDTO.setProductName(invoiceProduct.getProductName());
        invoiceProductDTO.setAmount(invoiceProduct.getAmount());
    }

    /**
     * Method under test: {@link InvoiceProductServiceImpl#getAllInvoiceProducts(Pageable)}
     */
    @Test
    void testGetAllInvoiceProducts_ReturnPageOfInvoiceProducts() {
        Pageable pageable = PageRequest.of(0,10);

        when(invoiceProductRepository.findAll(any(Pageable.class)))
                .thenReturn(new PageImpl<>(Collections.singletonList(invoiceProduct)));
        when(invoiceProductMapper.toInvoiceProductDTO(invoiceProduct)).thenReturn(invoiceProductDTO);

        Page<InvoiceProductDTO> result = invoiceProductService.getAllInvoiceProducts(pageable);

        assertThat(result).isNotNull();
        assertThat(result.getContent()).hasSize(1);
        assertThat(result.getContent().get(0)).isEqualTo(invoiceProductDTO);

        verify(invoiceProductRepository).findAll(pageable);
        verify(invoiceProductMapper).toInvoiceProductDTO(invoiceProduct);
    }

    /**
     * Method under test: {@link InvoiceProductServiceImpl#addInvoiceProduct(InvoiceProductDTO)}
     */
    @Test
    void testAddInvoiceProduct_ReturnInvoiceProductDTO() {
        when(invoiceRepository.findById(invoiceId)).thenReturn(Optional.of(invoice));
        when(productRepository.findById(productId)).thenReturn(Optional.of(product));
        when(invoiceProductRepository.findById(any())).thenReturn(Optional.empty());
        when(invoiceProductMapper.toInvoiceProduct(invoiceProductDTO, invoice, product)).thenReturn(invoiceProduct);
        when(invoiceProductRepository.save(invoiceProduct)).thenReturn(invoiceProduct);
        when(invoiceProductRepository.calculateTotalAmountByInvoiceId(invoice.getId())).thenReturn(BigDecimal.TEN);
        when(invoiceProductMapper.toInvoiceProductDTO(invoiceProduct)).thenReturn(invoiceProductDTO);

        InvoiceProductDTO result = invoiceProductService.addInvoiceProduct(invoiceProductDTO);

        System.out.println("Result" + result.toString());

        assertThat(result).isNotNull();
        assertThat(result.getInvoiceId()).isEqualTo(invoiceId);
        assertThat(result.getProductId()).isEqualTo(productId);
        assertThat(result.getQuantity()).isEqualTo(1);
        assertThat(result.getAmount()).isEqualTo(new BigDecimal("10.00"));

        verify(invoiceRepository).findById(invoiceId);
        verify(productRepository).findById(productId);
        verify(invoiceProductRepository).findById(any());
        verify(invoiceProductRepository).save(invoiceProduct);
        verify(invoiceRepository).save(invoice);
        verify(invoiceProductMapper).toInvoiceProductDTO(invoiceProduct);
    }

    /**
     * Method under test: {@link InvoiceProductServiceImpl#addInvoiceProduct(InvoiceProductDTO)}
     */
    @Test
    void testAddInvoiceProduct_ThrowIllegalArgumentException_ProductNotActive() {
        product.setStatus(Status.INACTIVE);

        when(invoiceRepository.findById(invoiceId)).thenReturn(Optional.of(invoice));
        when(productRepository.findById(productId)).thenReturn(Optional.of(product));

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            invoiceProductService.addInvoiceProduct(invoiceProductDTO);
        });

        assertThat(exception).isNotNull();
        assertThat(exception.getMessage()).isEqualTo("Product is not active");

        verify(productRepository).findById(productId);
        verify(invoiceProductRepository, never()).save(any());
        verify(invoiceRepository, never()).save(any());
    }

    /**
     * Method under test: {@link InvoiceProductServiceImpl#addInvoiceProduct(InvoiceProductDTO)}
     */
    @Test
    void testAddInvoiceProduct_ThrowIllegalArgumentException_InvoiceOrProductNotFound() {
        when(invoiceRepository.findById(any())).thenReturn(Optional.empty());
        when(productRepository.findById(any())).thenReturn(Optional.empty());

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            invoiceProductService.addInvoiceProduct(invoiceProductDTO);
        });

        assertThat(exception).isNotNull();
        assertThat(exception.getMessage()).isEqualTo("Invoice or Product not found");

        verify(invoiceRepository).findById(invoiceId);
        verify(productRepository).findById(productId);
        verify(invoiceProductRepository, never()).save(any());
        verify(invoiceRepository, never()).save(any());
    }

    /**
     * Method under test: {@link InvoiceProductServiceImpl#addInvoiceProduct(InvoiceProductDTO)}
     */
    @Test
    void testAddInvoiceProduct_ThrowIllegalArgumentException_InvoiceProductAlreadyExists() {
        when(invoiceRepository.findById(invoiceId)).thenReturn(Optional.of(invoice));
        when(productRepository.findById(productId)).thenReturn(Optional.of(product));
        when(invoiceProductRepository.findById(any())).thenReturn(Optional.of(invoiceProduct));

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            invoiceProductService.addInvoiceProduct(invoiceProductDTO);
        });

        assertThat(exception).isNotNull();
        assertThat(exception.getMessage()).isEqualTo("Invoice Product already exists");

        verify(invoiceRepository).findById(invoiceId);
        verify(productRepository).findById(productId);
        verify(invoiceProductRepository).findById(any());
        verify(invoiceProductRepository, never()).save(any());
        verify(invoiceRepository, never()).save(any());
    }

    /**
     * Method under test: {@link InvoiceProductServiceImpl#editInvoiceProduct(InvoiceProductDTO, UUID, UUID)}
     */
    @Test
    void testEditInvoiceProduct_ReturnInvoiceProductDTO() {
        InvoiceProductDTO invoiceProductDTO = new InvoiceProductDTO();
        invoiceProductDTO.setQuantity(2);

        when(invoiceProductRepository.findById(any())).thenReturn(Optional.of(invoiceProduct));
        when(invoiceRepository.findById(invoiceId)).thenReturn(Optional.of(invoice));
        when(productRepository.findById(productId)).thenReturn(Optional.of(product));
        when(invoiceProductRepository.save(invoiceProduct)).thenReturn(invoiceProduct);
        when(invoiceProductRepository.calculateTotalAmountByInvoiceId(invoice.getId())).thenReturn(BigDecimal.TEN);
        when(invoiceProductMapper.toInvoiceProductDTO(invoiceProduct)).thenReturn(invoiceProductDTO);

        InvoiceProductDTO result = invoiceProductService.editInvoiceProduct(invoiceProductDTO, invoiceId, productId);

        assertThat(result).isNotNull();
        assertThat(result.getQuantity()).isEqualTo(2);

        verify(invoiceProductRepository).findById(any());
        verify(invoiceRepository).findById(invoiceId);
        verify(productRepository).findById(productId);
        verify(invoiceProductRepository).save(invoiceProduct);
        verify(invoiceRepository).save(invoice);
        verify(invoiceProductMapper).toInvoiceProductDTO(invoiceProduct);
    }

    /**
     * Method under test: {@link InvoiceProductServiceImpl#editInvoiceProduct(InvoiceProductDTO, UUID, UUID)}
     */
    @Test
    void testEditInvoiceProduct_ThrowIllegalArgumentException_InvoiceProductNotFound() {
        when(invoiceProductRepository.findById(any())).thenReturn(Optional.empty());

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            invoiceProductService.editInvoiceProduct(invoiceProductDTO, invoiceId, productId);
        });

        assertThat(exception).isNotNull();
        assertThat(exception.getMessage()).isEqualTo("Invoice Product not found");

        verify(invoiceProductRepository).findById(any());
        verify(invoiceRepository, never()).findById(any());
        verify(productRepository, never()).findById(any());
        verify(invoiceProductRepository, never()).save(any());
        verify(invoiceRepository, never()).save(any());
        verify(invoiceProductMapper, never()).toInvoiceProductDTO(any());
    }

    /**
     * Method under test: {@link InvoiceProductServiceImpl#editInvoiceProduct(InvoiceProductDTO, UUID, UUID)}
     */
    @Test
    void testEditInvoiceProduct_ThrowIllegalArgumentException_InvoiceNotFound() {
        when(invoiceProductRepository.findById(any())).thenReturn(Optional.of(invoiceProduct));
        when(invoiceRepository.findById(any())).thenReturn(Optional.empty());

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            invoiceProductService.editInvoiceProduct(invoiceProductDTO, invoiceId, productId);
        });

        assertThat(exception).isNotNull();
        assertThat(exception.getMessage()).isEqualTo("Invoice not found");

        verify(invoiceProductRepository).findById(any());
        verify(invoiceRepository).findById(invoiceId);
        verify(productRepository, never()).findById(any());
        verify(invoiceProductRepository, never()).save(any());
        verify(invoiceRepository, never()).save(any());
    }

    /**
     * Method under test: {@link InvoiceProductServiceImpl#editInvoiceProduct(InvoiceProductDTO, UUID, UUID)}
     */
    @Test
    void testEditInvoiceProduct_ThrowIllegalArgumentException_ProductNotFound() {
        when(invoiceProductRepository.findById(any())).thenReturn(Optional.of(invoiceProduct));
        when(invoiceRepository.findById(invoiceId)).thenReturn(Optional.of(invoice));
        when(productRepository.findById(any())).thenReturn(Optional.empty());

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            invoiceProductService.editInvoiceProduct(invoiceProductDTO, invoiceId, productId);
        });

        assertThat(exception).isNotNull();
        assertThat(exception.getMessage()).isEqualTo("Product not found");

        verify(invoiceProductRepository).findById(any());
        verify(invoiceRepository).findById(invoiceId);
        verify(productRepository).findById(productId);
        verify(invoiceProductRepository, never()).save(any());
        verify(invoiceRepository, never()).save(any());
    }

    /**
     * Method under test: {@link InvoiceProductServiceImpl#editInvoiceProduct(InvoiceProductDTO, UUID, UUID)}
     */
    @Test
    void testEditInvoiceProduct_ThrowIllegalArgumentException_InvoiceOlderThan10Minutes() {
        invoice.setCreatedTime(LocalDateTime.now().minusMinutes(11));

        when(invoiceProductRepository.findById(any())).thenReturn(Optional.of(invoiceProduct));
        when(invoiceRepository.findById(invoiceId)).thenReturn(Optional.of(invoice));

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            invoiceProductService.editInvoiceProduct(invoiceProductDTO, invoiceId, productId);
        });

        assertThat(exception).isNotNull();
        assertThat(exception.getMessage()).isEqualTo("Invoice cannot be edited after 10 minutes");

        verify(invoiceProductRepository).findById(any());
        verify(invoiceRepository).findById(invoiceId);
        verify(productRepository, never()).findById(productId);
        verify(invoiceProductRepository, never()).save(any());
        verify(invoiceRepository, never()).save(any());
    }

    /**
     * Method under test: {@link InvoiceProductServiceImpl#editInvoiceProduct(InvoiceProductDTO, UUID, UUID)}
     */
    @Test
    void testEditInvoiceProduct_ThrowIllegalArgumentException_ProductNotActive() {
        product.setStatus(Status.INACTIVE);

        when(invoiceProductRepository.findById(any())).thenReturn(Optional.of(invoiceProduct));
        when(invoiceRepository.findById(invoiceId)).thenReturn(Optional.of(invoice));
        when(productRepository.findById(productId)).thenReturn(Optional.of(product));

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            invoiceProductService.editInvoiceProduct(invoiceProductDTO, invoiceId, productId);
        });

        assertThat(exception).isNotNull();
        assertThat(exception.getMessage()).isEqualTo("Product is not active");

        verify(invoiceProductRepository).findById(any());
        verify(invoiceRepository).findById(invoiceId);
        verify(productRepository).findById(productId);
        verify(invoiceProductRepository, never()).save(any());
        verify(invoiceRepository, never()).save(any());
    }

    /**
     * Method under test: {@link InvoiceProductServiceImpl#deleteInvoiceProduct(UUID, UUID)}
     */
    @Test
    void testDeleteInvoiceProduct() {
        when(invoiceProductRepository.findById(any())).thenReturn(Optional.of(invoiceProduct));
        when(invoiceRepository.findById(invoiceId)).thenReturn(Optional.of(invoice));

        invoiceProductService.deleteInvoiceProduct(invoiceId, productId);

        verify(invoiceProductRepository, times(1)).deleteById(new InvoiceProductId(invoiceId, productId));

        verify(invoiceProductRepository).findById(any());
        verify(invoiceRepository).findById(invoiceId);
        verify(invoiceProductRepository).deleteById(any());
        verify(invoiceRepository).save(invoice);
    }

    /**
     * Method under test: {@link InvoiceProductServiceImpl#deleteInvoiceProduct(UUID, UUID)}
     */
    @Test
    void testDeleteInvoiceProduct_ThrowIllegalArgumentException_InvoiceProductNotFound() {
        when(invoiceProductRepository.findById(any())).thenReturn(Optional.empty());

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            invoiceProductService.deleteInvoiceProduct(invoiceId, productId);
        });

        assertThat(exception).isNotNull();
        assertThat(exception.getMessage()).isEqualTo("Invoice Product not found");

        verify(invoiceProductRepository).findById(any());
        verify(invoiceRepository, never()).findById(any());
        verify(invoiceRepository, never()).save(any());
        verify(invoiceProductRepository, never()).deleteById(any());
    }

    /**
     * Method under test: {@link InvoiceProductServiceImpl#deleteInvoiceProduct(UUID, UUID)}
     */
    @Test
    void testDeleteInvoiceProduct_ThrowIllegalArgumentException_InvoiceNotFound() {
        when(invoiceProductRepository.findById(any())).thenReturn(Optional.of(invoiceProduct));
        when(invoiceRepository.findById(any())).thenReturn(Optional.empty());

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            invoiceProductService.deleteInvoiceProduct(invoiceId, productId);
        });

        assertThat(exception).isNotNull();
        assertThat(exception.getMessage()).isEqualTo("Invoice not found");

        verify(invoiceProductRepository).findById(any());
        verify(invoiceRepository).findById(invoiceId);
        verify(invoiceRepository, never()).save(any());
        verify(invoiceProductRepository, never()).deleteById(any());
    }

    /**
     * Method under test: {@link InvoiceProductServiceImpl#deleteInvoiceProduct(UUID, UUID)}
     */
    @Test
    void testDeleteInvoiceProduct_ThrowIllegalArgumentException_InvoiceOlderThan10Minutes() {
        invoice.setCreatedTime(LocalDateTime.now().minusMinutes(11));

        when(invoiceProductRepository.findById(any())).thenReturn(Optional.of(invoiceProduct));
        when(invoiceRepository.findById(invoiceId)).thenReturn(Optional.of(invoice));

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            invoiceProductService.deleteInvoiceProduct(invoiceId, productId);
        });

        assertThat(exception).isNotNull();
        assertThat(exception.getMessage()).isEqualTo("Invoice cannot be edited after 10 minutes");

        verify(invoiceProductRepository).findById(any());
        verify(invoiceRepository).findById(invoiceId);
        verify(invoiceRepository, never()).save(any());
        verify(invoiceProductRepository, never()).deleteById(any());
    }
}
