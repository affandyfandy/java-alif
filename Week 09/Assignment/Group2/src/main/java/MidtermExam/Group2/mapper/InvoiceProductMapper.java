package MidtermExam.Group2.mapper;

import MidtermExam.Group2.dto.InvoiceProductDTO;
import MidtermExam.Group2.dto.InvoiceProductWithoutProductIdDTO;
import MidtermExam.Group2.entity.Invoice;
import MidtermExam.Group2.entity.InvoiceProduct;
import MidtermExam.Group2.entity.Product;
import org.mapstruct.Context;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = { InvoiceMapper.class, ProductMapper.class }, injectionStrategy = org.mapstruct.InjectionStrategy.CONSTRUCTOR)
public interface InvoiceProductMapper {

        @Mapping(source = "invoice.id", target = "invoiceId")
        @Mapping(source = "product.id", target = "productId")
        @Mapping(source = "product.name", target = "productName")
        InvoiceProductDTO toInvoiceProductDTO(InvoiceProduct invoiceProduct);

        @Mapping(source = "invoiceId", target = "invoice.id")
        @Mapping(source = "productId", target = "product.id")
        @Mapping(source = "productName", target = "product.name")
        InvoiceProduct toInvoiceProduct(InvoiceProductDTO invoiceProductDTO, @Context Invoice invoice,
                        @Context Product product);

        @Mapping(source = "invoice.id", target = "invoiceId")
        @Mapping(source = "product.name", target = "productName")
        @Mapping(source = "quantity", target = "quantity")
        @Mapping(source = "amount", target = "amount")
        @Mapping(source = "product.price", target = "price")
        InvoiceProductWithoutProductIdDTO toInvoiceProductWithoutIdDTO(InvoiceProduct invoiceProduct);

        @Mapping(source = "invoiceId", target = "invoice.id")
        @Mapping(source = "productName", target = "product.name")
        @Mapping(source = "quantity", target = "quantity")
        @Mapping(source = "amount", target = "amount")
        @Mapping(source = "price", target = "product.price")
        InvoiceProduct toInvoiceProduct(InvoiceProductWithoutProductIdDTO invoiceProductWithoutProductIdDTO, @Context Invoice invoice,
                                        @Context Product product);
}