package MidtermExam.Group2.dto;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class InvoiceListDTO {
    private UUID id;
    private BigDecimal invoiceAmount;

    @NotBlank(message = "Customer name is required")
    private String customerName;

    @NotBlank(message = "Invoice date is required")
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private LocalDate invoiceDate;
}
