package MidtermExam.Group2.service;

import java.io.InputStream;

import MidtermExam.Group2.dto.InvoiceDetailDTO;

public interface PdfService {
    InputStream generatePdf(InvoiceDetailDTO invoiceDetail) throws Exception;
}
