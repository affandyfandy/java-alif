package com.fsoft.invoice_application.repository;

import com.fsoft.invoice_application.model.Invoice;
import org.springframework.data.jpa.repository.JpaRepository;

public interface InvoiceRepository extends JpaRepository<Invoice, Long> {
}
