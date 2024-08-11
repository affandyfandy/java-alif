package MidtermExam.Group2.service;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.UUID;

public interface ExportService {
    ByteArrayInputStream exportInvoicesToExcel(UUID customerId, Integer month, Integer year) throws IOException;
}
