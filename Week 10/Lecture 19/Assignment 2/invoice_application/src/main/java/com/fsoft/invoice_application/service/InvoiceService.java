package com.fsoft.invoice_application.service;

import org.springframework.stereotype.Service;
import com.fsoft.invoice_application.client.ProductFeignClient;
import com.fsoft.invoice_application.dto.InvoiceDto;
import com.fsoft.invoice_application.dto.ProductDto;
import com.fsoft.invoice_application.mapper.InvoiceMapper;
import com.fsoft.invoice_application.model.Invoice;
import com.fsoft.invoice_application.repository.InvoiceRepository;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class InvoiceService {

    private final InvoiceRepository invoiceRepository;
    private final InvoiceMapper invoiceMapper;

    public InvoiceService(InvoiceRepository invoiceRepository, InvoiceMapper invoiceMapper) {
        this.invoiceRepository = invoiceRepository;
        this.invoiceMapper = invoiceMapper;
    }

    public List<InvoiceDto> getAllInvoices() {
        return invoiceRepository.findAll().stream()
                .map(invoiceMapper::toDto)
                .toList();
    }

    public Optional<InvoiceDto> getInvoiceById(Long id) {
        return invoiceRepository.findById(id)
                .map(invoiceMapper::toDto);
    }

    public InvoiceDto createInvoice(InvoiceDto invoiceDto) {
        Invoice invoice = invoiceMapper.toEntity(invoiceDto);
        return invoiceMapper.toDto(invoiceRepository.save(invoice));
    }

    public InvoiceDto updateInvoice(Long id, InvoiceDto invoiceDto) {
        Invoice invoice = invoiceMapper.toEntity(invoiceDto);
        invoice.setId(id);
        return invoiceMapper.toDto(invoiceRepository.save(invoice));
    }

    public void deleteInvoice(Long id) {
        invoiceRepository.deleteById(id);
    }
}
