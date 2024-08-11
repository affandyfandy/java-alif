package MidtermExam.Group2.mapper;

import MidtermExam.Group2.dto.InvoiceDTO;
import MidtermExam.Group2.dto.InvoiceDetailDTO;
import MidtermExam.Group2.dto.InvoiceListDTO;
import MidtermExam.Group2.entity.Invoice;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Mapper(componentModel = "spring", uses = { CustomerMapper.class, InvoiceProductMapper.class }, injectionStrategy = InjectionStrategy.CONSTRUCTOR)
public interface InvoiceMapper {

    @Mapping(source = "customer.name", target = "customerName")
    InvoiceListDTO toInvoiceListDTO(Invoice invoice);

    @Mapping(source = "customerName", target = "customer.name")
    @Mapping(source = "invoiceAmount", target = "invoiceAmount")
    @Mapping(source = "invoiceDate", target = "invoiceDate", qualifiedByName = "toLocalDateTime")
    Invoice toInvoice(InvoiceListDTO invoiceDTO);

    @Mapping(source = "customer.id", target = "customerId")
    InvoiceDTO toInvoicesDTO(Invoice invoice);

    @Mapping(source = "customerId", target = "customer.id")
    @Mapping(source = "invoiceAmount", target = "invoiceAmount")
    @Mapping(source = "invoiceDate", target = "invoiceDate", qualifiedByName = "toLocalDateTime")
    Invoice toInvoices(InvoiceDTO invoiceDTO);

    @Mapping(source = "invoice.id", target = "invoiceId")
    @Mapping(source = "customer", target = "customer")
    @Mapping(source = "invoiceProducts", target = "products")
    InvoiceDetailDTO toInvoiceDetailDTO(Invoice invoice);

    @Mapping(source = "customer", target = "customer")
    @Mapping(source = "products", target = "invoiceProducts")
    @Mapping(source = "invoiceDate", target = "invoiceDate", qualifiedByName = "toLocalDateTime")
    Invoice toInvoice(InvoiceDetailDTO invoiceDetailDTO);

    @Named("toLocalDate")
    default LocalDate mapToLocalDate(LocalDateTime localDateTime) {
        return localDateTime == null ? null : localDateTime.toLocalDate();
    }

    @Named("toLocalDateTime")
    default LocalDateTime mapToLocalDateTime(LocalDate localDate) {
        return localDate == null ? null : localDate.atStartOfDay();
    }
}
