package MidtermExam.Group2.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "invoices")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Invoice {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @NotNull(message = "Customer cannot be null")
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "customer_id", referencedColumnName = "id", nullable = false)
    private Customer customer;

    @NotNull(message = "Invoice amount cannot be null")
    @DecimalMin(value = "0.0", inclusive = true, message = "Invoice amount must be greater than or equal to 0")
    @Column(name = "invoice_amount", precision = 10, scale = 2, nullable = false)
    private BigDecimal invoiceAmount;

    @NotNull(message = "Invoice date cannot be null")
    @Column(name = "invoice_date", nullable = false)
    private LocalDateTime invoiceDate;

    @NotNull(message = "Created at cannot be null")
    @Column(name = "created_time", nullable = false)
    private LocalDateTime createdTime;

    @NotNull(message = "Updated at cannot be null")
    @Column(name = "updated_time", nullable = false)
    private LocalDateTime updatedTime;

    @OneToMany(mappedBy = "invoice", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<InvoiceProduct> invoiceProducts;

    @PrePersist
    public void prePersist() {
        this.createdTime = LocalDateTime.now();
        this.updatedTime = LocalDateTime.now();
    }

    @PreUpdate
    public void preUpdate() {
        this.updatedTime = LocalDateTime.now();
    }
}