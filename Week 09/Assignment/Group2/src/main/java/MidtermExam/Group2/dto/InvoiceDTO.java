package MidtermExam.Group2.dto;

import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class InvoiceDTO {
    private UUID id;

    @NotNull(message = "Customer ID is required")
    private UUID customerId;

    private BigDecimal invoiceAmount;

    @NotNull(message = "Invoice date is required")
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private LocalDate invoiceDate;
}

