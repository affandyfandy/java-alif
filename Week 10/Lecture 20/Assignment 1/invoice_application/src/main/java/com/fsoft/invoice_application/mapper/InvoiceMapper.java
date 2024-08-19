package com.fsoft.invoice_application.mapper;

import com.fsoft.invoice_application.dto.InvoiceDto;
import com.fsoft.invoice_application.model.Invoice;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface InvoiceMapper {
    InvoiceDto toDto(Invoice invoice);
    Invoice toEntity(InvoiceDto invoiceDto);
}
