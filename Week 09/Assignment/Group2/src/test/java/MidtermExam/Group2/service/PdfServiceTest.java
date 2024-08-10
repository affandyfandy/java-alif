package MidtermExam.Group2.service;

import MidtermExam.Group2.dto.CustomerDTO;
import MidtermExam.Group2.dto.InvoiceDetailDTO;
import MidtermExam.Group2.service.impl.PdfServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.core.io.ClassPathResource;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;
import org.xhtmlrenderer.pdf.ITextRenderer;

import java.io.InputStream;
import java.math.BigDecimal;
import java.nio.charset.StandardCharsets;
import java.util.Collections;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {PdfServiceImpl.class})
class PdfServiceTest {

    @Autowired
    private PdfServiceImpl pdfService;

    @MockBean
    private TemplateEngine templateEngine;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGeneratePdf() throws Exception {
        // Prepare data for testing
        InvoiceDetailDTO invoiceDetail = new InvoiceDetailDTO();
        invoiceDetail.setInvoiceId(UUID.randomUUID());
        invoiceDetail.setInvoiceAmount(BigDecimal.valueOf(500.00));
        invoiceDetail.setCustomer(new CustomerDTO(UUID.randomUUID(), "John Doe", "johndoe@example.com", "1234567890"));
        invoiceDetail.setProducts(Collections.emptyList());

        // Load the HTML template content
        ClassPathResource resource = new ClassPathResource("templates/pdf-template.html");
        String htmlTemplate = new String(resource.getInputStream().readAllBytes(), StandardCharsets.UTF_8);

        when(templateEngine.process(eq("pdf-template"), any(Context.class))).thenReturn(htmlTemplate);

        InputStream result = pdfService.generatePdf(invoiceDetail);

        assertThat(result).isNotNull();

        byte[] pdfBytes = result.readAllBytes();
        assertThat(pdfBytes).isNotEmpty();

        // verify PDF content if needed
        ITextRenderer renderer = new ITextRenderer();
        renderer.setDocumentFromString(htmlTemplate);
        renderer.layout();
    }
}
