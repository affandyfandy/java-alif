package MidtermExam.Group2.entity;

import java.io.Serializable;
import java.util.Objects;
import java.util.UUID;

public class InvoiceProductId implements Serializable {
    private UUID invoice;
    private UUID product;

    public InvoiceProductId() {
    }

    public InvoiceProductId(UUID invoice, UUID product) {
        this.invoice = invoice;
        this.product = product;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        InvoiceProductId that = (InvoiceProductId) o;
        return Objects.equals(invoice, that.invoice) && Objects.equals(product, that.product);
    }

    @Override
    public int hashCode() {
        return Objects.hash(invoice, product);
    }
}