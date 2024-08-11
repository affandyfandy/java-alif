package MidtermExam.Group2.controller;

import MidtermExam.Group2.criteria.InvoiceSearchCriteria;
import MidtermExam.Group2.dto.InvoiceDTO;
import MidtermExam.Group2.dto.InvoiceDetailDTO;
import MidtermExam.Group2.dto.InvoiceListDTO;
import MidtermExam.Group2.exception.PdfGenerationException;
import MidtermExam.Group2.service.ExportService;
import MidtermExam.Group2.service.InvoiceService;
import MidtermExam.Group2.service.PdfService;
import jakarta.validation.Valid;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.method.annotation.StreamingResponseBody;

@RestController
@RequestMapping("/api/v1/invoices")
public class InvoiceController {
    private final InvoiceService invoiceService;
    private final ExportService exportService;
    private final PdfService pdfService;

    @Autowired
    public InvoiceController(InvoiceService invoiceService, ExportService exportService, PdfService pdfService) {
        this.invoiceService = invoiceService;
        this.exportService = exportService;
        this.pdfService = pdfService;
    }

    @GetMapping
    public ResponseEntity<Page<InvoiceListDTO>> getAllInvoices(Pageable pageable, InvoiceSearchCriteria criteria) {
        return ResponseEntity.ok(invoiceService.getAllInvoices(pageable, criteria));
    }

    @GetMapping("/{id}")
    public ResponseEntity<InvoiceDetailDTO> getInvoiceDetail(@PathVariable("id") UUID invoiceId) {
        InvoiceDetailDTO invoiceDetail = invoiceService.getInvoiceDetail(invoiceId);
        return new ResponseEntity<>(invoiceDetail, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<InvoiceDTO> addInvoice(@Valid @RequestBody InvoiceDTO invoiceDTO) {
        InvoiceDTO addedInvoice = invoiceService.addInvoice(invoiceDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(addedInvoice);
    }

    @PutMapping("/{id}")
    public ResponseEntity<InvoiceDTO> editInvoice(@PathVariable("id") UUID id,
            @Valid @RequestBody InvoiceDTO invoiceDTO) {
        InvoiceDTO editedInvoice = invoiceService.editInvoice(id, invoiceDTO);
        return ResponseEntity.ok(editedInvoice);
    }

    @GetMapping("/excel")
    public ResponseEntity<Object> exportInvoicesToExcel(@RequestParam(required = false) UUID customerId,
                                                   @RequestParam(required = false) Integer month,
                                                   @RequestParam(required = false) Integer year) {
        try {
            ByteArrayInputStream excelFile = exportService.exportInvoicesToExcel(customerId, month, year);

            HttpHeaders headers = new HttpHeaders();
            headers.add("Content-Disposition", "attachment; filename=invoices.xlsx");

            return ResponseEntity
                    .ok()
                    .headers(headers)
                    .contentType(MediaType.parseMediaType("application/vnd.ms-excel"))
                    .body(excelFile.readAllBytes());
        } catch (IOException e) {
            return ResponseEntity.status(500).body("Failed to export invoices to Excel");
        }
    }

    @GetMapping("/{id}/pdf")
    public ResponseEntity<StreamingResponseBody> generateInvoicePdf(@PathVariable("id") UUID invoiceId) {
        try {
            InvoiceDetailDTO invoiceDetail = invoiceService.getInvoiceDetail(invoiceId);

            StreamingResponseBody stream = outputStream -> {
                try (InputStream is = pdfService.generatePdf(invoiceDetail)) {
                    byte[] buffer = new byte[2048];
                    int bytesRead;
                    while ((bytesRead = is.read(buffer)) != -1) {
                        outputStream.write(buffer, 0, bytesRead);
                    }
                } catch (PdfGenerationException e) {
                    throw new IOException("An error occurred while generating PDF", e);
                }
            };

            HttpHeaders headers = new HttpHeaders();
            headers.add("Content-Disposition", "attachment; filename=invoice.pdf");

            return ResponseEntity
                    .ok()
                    .headers(headers)
                    .contentType(MediaType.APPLICATION_PDF)
                    .body(stream);
        } catch (Exception e) {
            return ResponseEntity.status(500).body(null);
        }
    }
}
