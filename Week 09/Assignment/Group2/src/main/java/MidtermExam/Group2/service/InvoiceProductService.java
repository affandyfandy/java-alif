package MidtermExam.Group2.service;

import MidtermExam.Group2.dto.InvoiceListDTO;
import MidtermExam.Group2.dto.InvoiceProductDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.UUID;

public interface InvoiceProductService {
    Page<InvoiceProductDTO> getAllInvoiceProducts(Pageable pageable);
    InvoiceProductDTO addInvoiceProduct(InvoiceProductDTO invoiceProductDTO);
    InvoiceProductDTO editInvoiceProduct(InvoiceProductDTO invoiceProductDTO, UUID invoiceId, UUID productId);
    void deleteInvoiceProduct(UUID invoiceId, UUID productId);
}
