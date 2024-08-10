package MidtermExam.Group2.criteria;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.UUID;

@Getter
@Setter
public class InvoiceSearchCriteria implements Serializable {
    private String customerName;
    private UUID customerId;
    private LocalDate invoiceDate;
    private String month;
    private String sortBy;
    private boolean sortAsc;
}
