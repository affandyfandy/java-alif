package MidtermExam.Group2.service;

import MidtermExam.Group2.entity.Customer;
import MidtermExam.Group2.entity.Invoice;
import MidtermExam.Group2.entity.InvoiceProduct;
import MidtermExam.Group2.entity.Product;
import MidtermExam.Group2.repository.InvoiceRepository;
import MidtermExam.Group2.service.impl.ExportServiceImpl;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {ExportServiceImpl.class})
class ExportServiceTest {
    @Autowired
    private ExportServiceImpl exportService;

    @MockBean
    private InvoiceRepository invoiceRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testExportInvoicesToExcel_WithValidInvoices() throws IOException {
        // Given
        UUID customerId = UUID.randomUUID();
        int month = 8;
        int year = 2024;

        List<Invoice> invoices = new ArrayList<>();
        Invoice invoice = new Invoice();
        invoice.setId(UUID.randomUUID());
        invoice.setInvoiceDate(LocalDateTime.now());
        invoice.setInvoiceAmount(BigDecimal.valueOf(1000));

        Customer customer = new Customer();
        customer.setId(customerId);
        customer.setName("John Doe");
        invoice.setCustomer(customer);

        List<InvoiceProduct> invoiceProducts = new ArrayList<>();
        InvoiceProduct product = new InvoiceProduct();
        Product productEntity = new Product();
        productEntity.setId(UUID.randomUUID());
        productEntity.setName("Product 1");
        productEntity.setPrice(BigDecimal.valueOf(100));

        product.setProduct(productEntity);
        product.setQuantity(2);
        product.setAmount(BigDecimal.valueOf(200));
        invoiceProducts.add(product);
        invoice.setInvoiceProducts(invoiceProducts);

        invoices.add(invoice);

        when(invoiceRepository.findByCustomerAndDate(customerId, month, year)).thenReturn(invoices);

        // When
        ByteArrayInputStream excelStream = exportService.exportInvoicesToExcel(customerId, month, year);

        // Then
        assertNotNull(excelStream);

        // Verify Excel content (basic check)
        Workbook workbook = new XSSFWorkbook(excelStream);
        assertEquals(1, workbook.getNumberOfSheets());
        assertEquals("Invoices", workbook.getSheetName(0));

        // Verify row count (including header)
        int numberOfRows = workbook.getSheetAt(0).getPhysicalNumberOfRows();
        assertTrue(numberOfRows > 1); // Header + at least one data row

        workbook.close();
        verify(invoiceRepository, times(1)).findByCustomerAndDate(customerId, month, year);
    }

    @Test
    void testExportInvoicesToExcel_NoInvoicesFound() {
        // Given
        UUID customerId = UUID.randomUUID();
        int month = 8;
        int year = 2024;

        when(invoiceRepository.findByCustomerAndDate(customerId, month, year)).thenReturn(new ArrayList<>());

        // When / Then
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            exportService.exportInvoicesToExcel(customerId, month, year);
        });

        assertEquals("No invoices found", exception.getMessage());
        verify(invoiceRepository, times(1)).findByCustomerAndDate(customerId, month, year);
    }
}
