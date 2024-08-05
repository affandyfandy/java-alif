package MidtermExam.Group2.service;

import MidtermExam.Group2.criteria.InvoiceSearchCriteria;
import MidtermExam.Group2.dto.InvoiceDTO;
import MidtermExam.Group2.dto.InvoiceDetailDTO;
import MidtermExam.Group2.dto.InvoiceListDTO;

import java.util.UUID;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.UUID;

public interface InvoiceService {
    Page<InvoiceListDTO> getAllInvoices(Pageable pageable, InvoiceSearchCriteria criteria);

    InvoiceDTO getInvoiceById(UUID id);

    InvoiceDTO addInvoice(InvoiceDTO invoiceDTO);

    InvoiceDTO editInvoice(UUID id, InvoiceDTO invoiceDTO);

    InvoiceDetailDTO getInvoiceDetail(UUID invoiceId);

}
