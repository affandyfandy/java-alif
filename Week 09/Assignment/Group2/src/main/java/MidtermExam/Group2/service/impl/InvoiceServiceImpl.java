package MidtermExam.Group2.service.impl;

import MidtermExam.Group2.criteria.InvoiceSearchCriteria;
import MidtermExam.Group2.dto.InvoiceDTO;
import MidtermExam.Group2.dto.InvoiceDetailDTO;
import MidtermExam.Group2.dto.InvoiceListDTO;
import MidtermExam.Group2.entity.Invoice;
import MidtermExam.Group2.entity.Customer;
import MidtermExam.Group2.entity.Invoice;
import MidtermExam.Group2.mapper.InvoiceMapper;
import MidtermExam.Group2.repository.CustomerRepository;
import MidtermExam.Group2.repository.InvoiceRepository;
import MidtermExam.Group2.repository.InvoiceSpecification;
import MidtermExam.Group2.service.InvoiceService;

import java.util.Optional;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

@Service
@Transactional
public class InvoiceServiceImpl implements InvoiceService {
    private final InvoiceRepository invoiceRepository;
    private final InvoiceMapper invoiceMapper;
    private final CustomerRepository customerRepository;

    @Autowired
    public InvoiceServiceImpl(InvoiceRepository invoiceRepository, InvoiceMapper invoiceMapper,
            CustomerRepository customerRepository) {
        this.invoiceRepository = invoiceRepository;
        this.invoiceMapper = invoiceMapper;
        this.customerRepository = customerRepository;
    }

    @Override
    public Page<InvoiceListDTO> getAllInvoices(Pageable pageable, InvoiceSearchCriteria criteria) {
        InvoiceSpecification invoiceSpecification = new InvoiceSpecification(criteria);
        return invoiceRepository.findAll(invoiceSpecification, pageable).map(invoiceMapper::toInvoiceListDTO);
    }

    @Override
    public InvoiceDTO getInvoiceById(UUID id) {
        Invoice invoice = invoiceRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invoice not found"));
        return invoiceMapper.toInvoicesDTO(invoice);
    }

    @Override
    public InvoiceDTO addInvoice(InvoiceDTO invoiceDTO) {
        UUID customerId = invoiceDTO.getCustomerId();
        Optional<Customer> customerOpt = customerRepository.findById(customerId);
        if (customerOpt.isEmpty()) {
            throw new IllegalArgumentException("Customer not found");
        }

        Customer customer = customerOpt.get();

        if (customer.getStatus().name().equals("INACTIVE")) {
            throw new IllegalArgumentException("Customer is inactive");
        }

        Invoice invoice = invoiceMapper.toInvoices(invoiceDTO);

        LocalDate invoiceDateOnly = invoiceDTO.getInvoiceDate();
        invoice.setInvoiceDate(LocalDateTime.of(invoiceDateOnly, LocalTime.MIDNIGHT));

        if (invoice.getInvoiceAmount() == null) {
            invoice.setInvoiceAmount(BigDecimal.ZERO);
        }

        invoice.setCustomer(customer);
        invoice.setCreatedTime(LocalDateTime.now());
        invoice.setUpdatedTime(LocalDateTime.now());
        invoice = invoiceRepository.save(invoice);
        return invoiceMapper.toInvoicesDTO(invoice);
    }

    @Override
    public InvoiceDTO editInvoice(UUID id, InvoiceDTO invoiceDTO) {
        Invoice invoice = invoiceRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invoice not found"));

        // Throw exception if invoice is older than 10 minutes
        if (invoice.getCreatedTime().plusMinutes(10).isBefore(LocalDateTime.now())) {
            throw new IllegalArgumentException("Invoice cannot be edited after 10 minutes");
        }

        Customer customer = customerRepository.findById(invoiceDTO.getCustomerId())
                .orElseThrow(() -> new IllegalArgumentException("Customer not found"));

        if (customer.getStatus().name().equals("INACTIVE")) {
            throw new IllegalArgumentException("Customer is inactive");
        }

        invoice.setCustomer(customer);
        invoice.setInvoiceDate(LocalDateTime.of(invoiceDTO.getInvoiceDate(), LocalTime.MIDNIGHT));

        invoice = invoiceRepository.save(invoice);
        return invoiceMapper.toInvoicesDTO(invoice);
    }

    @Override
    public InvoiceDetailDTO getInvoiceDetail(UUID invoiceId) {
        Optional<Invoice> invoiceOpt = invoiceRepository.findById(invoiceId);
        if (invoiceOpt.isPresent()) {
            return invoiceMapper.toInvoiceDetailDTO(invoiceOpt.get());
        } else {
            throw new RuntimeException("Invoice not found");
        }
    }
}
