package MidtermExam.Group2.mapper;

import MidtermExam.Group2.dto.InvoiceProductDTO;
import MidtermExam.Group2.dto.InvoiceProductWithoutProductIdDTO;
import MidtermExam.Group2.entity.Invoice;
import MidtermExam.Group2.entity.InvoiceProduct;
import MidtermExam.Group2.entity.Product;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;


class InvoiceProductMapperTest {

    private InvoiceProductMapper invoiceProductMapper;

    @BeforeEach
    void setUp() {
        invoiceProductMapper = Mappers.getMapper(InvoiceProductMapper.class);
    }

    @Test
    void toInvoiceProductDTO() {
        // Given
        InvoiceProduct invoiceProduct = new InvoiceProduct();
        invoiceProduct.setInvoice(new Invoice());
        invoiceProduct.setProduct(new Product());

        // When
        InvoiceProductDTO result = invoiceProductMapper.toInvoiceProductDTO(invoiceProduct);

        // Then
        assertEquals(invoiceProduct.getInvoice().getId(), result.getInvoiceId());
        assertEquals(invoiceProduct.getProduct().getId(), result.getProductId());
    }

    @Test
    void testToInvoiceProduct_ID1() {
        // Given
        Invoice invoice = new Invoice();
        Product product = new Product();
        product.setName("Product A");

        InvoiceProductDTO invoiceProductDTO = new InvoiceProductDTO();
        invoiceProductDTO.setInvoiceId(invoice.getId());
        invoiceProductDTO.setProductId(product.getId());
        invoiceProductDTO.setProductName(product.getName());

        // When
        InvoiceProduct result = invoiceProductMapper.toInvoiceProduct(invoiceProductDTO, invoice, product);

        // Then
        assertEquals(invoiceProductDTO.getInvoiceId(), result.getInvoice().getId());
        assertEquals(invoiceProductDTO.getProductId(), result.getProduct().getId());
        assertEquals(invoiceProductDTO.getProductName(), result.getProduct().getName());
    }

    @Test
    void testToInvoiceProductWithoutIdDTO() {
        // Given
        InvoiceProduct invoiceProduct = new InvoiceProduct();
        invoiceProduct.setInvoice(new Invoice());
        invoiceProduct.setProduct(new Product());
        invoiceProduct.setQuantity(1);
        invoiceProduct.setAmount(new BigDecimal("10.0"));

        // When
        InvoiceProductWithoutProductIdDTO result = invoiceProductMapper.toInvoiceProductWithoutIdDTO(invoiceProduct);

        // Then
        assertEquals(invoiceProduct.getInvoice().getId(), result.getInvoiceId());
        assertEquals(invoiceProduct.getProduct().getName(), result.getProductName());
        assertEquals(invoiceProduct.getQuantity(), result.getQuantity());
        assertEquals(invoiceProduct.getAmount(), result.getAmount());
        assertEquals(invoiceProduct.getProduct().getPrice(), result.getPrice());
    }

    @Test
    void testToInvoiceProduct_ID2() {
        // Given
        Invoice invoice = new Invoice();
        Product product = new Product();
        product.setName("Product A");
        product.setPrice(new BigDecimal("10.0"));

        InvoiceProductWithoutProductIdDTO invoiceProductWithoutProductIdDTO = new InvoiceProductWithoutProductIdDTO();
        invoiceProductWithoutProductIdDTO.setInvoiceId(invoice.getId());
        invoiceProductWithoutProductIdDTO.setProductName(product.getName());
        invoiceProductWithoutProductIdDTO.setQuantity(1);
        invoiceProductWithoutProductIdDTO.setPrice(new BigDecimal("10.0"));
        invoiceProductWithoutProductIdDTO.setAmount(invoiceProductWithoutProductIdDTO.getPrice().
                multiply(BigDecimal.valueOf(
                        invoiceProductWithoutProductIdDTO.getQuantity()
                )));

        // When
        InvoiceProduct result = invoiceProductMapper.toInvoiceProduct(invoiceProductWithoutProductIdDTO, invoice, product);

        // Then
        assertEquals(invoiceProductWithoutProductIdDTO.getInvoiceId(), result.getInvoice().getId());
        assertEquals(invoiceProductWithoutProductIdDTO.getProductName(), result.getProduct().getName());
        assertEquals(invoiceProductWithoutProductIdDTO.getQuantity(), result.getQuantity());
        assertEquals(invoiceProductWithoutProductIdDTO.getAmount(), result.getAmount());
        assertEquals(invoiceProductWithoutProductIdDTO.getPrice(), result.getProduct().getPrice());
    }
}
