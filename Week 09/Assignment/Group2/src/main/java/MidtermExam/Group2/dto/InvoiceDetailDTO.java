package MidtermExam.Group2.dto;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class InvoiceDetailDTO {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID invoiceId;
    private BigDecimal invoiceAmount;
    private LocalDate invoiceDate;
    private CustomerDTO customer;
    private List<InvoiceProductWithoutProductIdDTO> products;
}