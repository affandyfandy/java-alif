package MidtermExam.Group2.service.impl;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

import MidtermExam.Group2.dto.InvoiceDetailDTO;
import MidtermExam.Group2.exception.PdfGenerationException;
import MidtermExam.Group2.service.PdfService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;
import org.xhtmlrenderer.pdf.ITextRenderer;

@Service
@Transactional
public class PdfServiceImpl implements PdfService {

    private final TemplateEngine templateEngine;

    @Autowired
    public PdfServiceImpl(TemplateEngine templateEngine) {
        this.templateEngine = templateEngine;
    }

    public InputStream generatePdf(InvoiceDetailDTO invoiceDetail) throws PdfGenerationException {
        Map<String, Object> data = new HashMap<>();
        data.put("invoice", invoiceDetail);
        data.put("customer", invoiceDetail.getCustomer());
        data.put("items", invoiceDetail.getProducts());


        Context context = new Context();
        context.setVariables(data);

        String htmlContent = templateEngine.process("pdf-template", context);
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();

        ITextRenderer renderer = new ITextRenderer();
        renderer.setDocumentFromString(htmlContent);
        renderer.layout();
        try {
            renderer.createPDF(outputStream, false);
        } catch (Exception e) {
            throw new PdfGenerationException("An error occurred while generating PDF", e);
        } finally {
            renderer.finishPDF();
        }

        return new ByteArrayInputStream(outputStream.toByteArray());
    }
}
