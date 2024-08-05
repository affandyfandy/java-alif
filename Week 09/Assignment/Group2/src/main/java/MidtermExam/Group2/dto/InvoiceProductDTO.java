package MidtermExam.Group2.dto;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class InvoiceProductDTO {
    private UUID invoiceId;
    private UUID productId;
    private String productName;

    @Min(value = 1, message = "Quantity must be greater than 0")
    private int quantity;

    private BigDecimal amount;
}
