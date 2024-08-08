package MidtermExam.Group2.controller;

import MidtermExam.Group2.criteria.InvoiceSearchCriteria;
import MidtermExam.Group2.dto.CustomerDTO;
import MidtermExam.Group2.dto.InvoiceDTO;
import MidtermExam.Group2.dto.InvoiceDetailDTO;
import MidtermExam.Group2.dto.InvoiceListDTO;
import MidtermExam.Group2.service.ExportService;
import MidtermExam.Group2.service.InvoiceService;
import MidtermExam.Group2.service.PdfService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.io.ByteArrayInputStream;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Collections;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@WebMvcTest(controllers = InvoiceController.class)
public class InvoiceControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private InvoiceService invoiceService;

    @MockBean
    private ExportService exportService;

    @MockBean
    private PdfService pdfService;

    private InvoiceDTO invoiceDTO;
    private InvoiceDetailDTO invoiceDetailDTO;
    private InvoiceListDTO invoiceListDTO;
    private Pageable pageable;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        UUID invoiceId = UUID.randomUUID();
        UUID customerId = UUID.randomUUID();
        BigDecimal invoiceAmount = new BigDecimal(1000);
        LocalDate invoiceDate = LocalDate.of(2024, 7, 5);

        CustomerDTO customerDTO = new CustomerDTO(customerId, "Alif T", "+6281234567890", "ACTIVE");

        invoiceDTO = new InvoiceDTO(invoiceId, customerId, invoiceAmount, invoiceDate);
        invoiceDetailDTO = new InvoiceDetailDTO(invoiceId, invoiceAmount, invoiceDate, customerDTO, Collections.emptyList());
        invoiceListDTO = new InvoiceListDTO(invoiceId, invoiceAmount, customerDTO.getName(), invoiceDate);
        pageable = PageRequest.of(0, 10);
    }

    @Test
    void testGetAllInvoices() throws Exception {
        Page<InvoiceListDTO> page = new PageImpl<>(Collections.singletonList(invoiceListDTO), pageable, 1);
        InvoiceSearchCriteria criteria = new InvoiceSearchCriteria();
        criteria.setCustomerName(invoiceListDTO.getCustomerName());
        criteria.setInvoiceDate(invoiceListDTO.getInvoiceDate());

        when(invoiceService.getAllInvoices(any(Pageable.class), any(InvoiceSearchCriteria.class))).thenReturn(page);

        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/invoices")
                .param("customerName", criteria.getCustomerName())
                .param("invoiceDate", criteria.getInvoiceDate().toString())
                .contentType("application/json"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.content[0].id").value(invoiceListDTO.getId().toString()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.content[0].invoiceAmount").value(invoiceListDTO.getInvoiceAmount().toString()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.content[0].customerName").value(invoiceListDTO.getCustomerName()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.content[0].invoiceDate").value(invoiceListDTO.getInvoiceDate().toString()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.pageable.pageSize").value(pageable.getPageSize()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.pageable.pageNumber").value(pageable.getPageNumber()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.totalElements").value(page.getTotalElements()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.totalPages").value(page.getTotalPages()));
    }

    @Test
    void testGetInvoiceDetail() throws Exception {
        UUID id = invoiceDetailDTO.getInvoiceId();

        when(invoiceService.getInvoiceDetail(id)).thenReturn(invoiceDetailDTO);

        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/invoices/{id}", id)
                .contentType("application/json"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.invoiceId").value(invoiceDetailDTO.getInvoiceId().toString()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.invoiceAmount").value(invoiceDetailDTO.getInvoiceAmount().toString()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.invoiceDate").value(invoiceDetailDTO.getInvoiceDate().toString()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.customer.id").value(invoiceDetailDTO.getCustomer().getId().toString()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.customer.name").value(invoiceDetailDTO.getCustomer().getName()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.customer.phoneNumber").value(invoiceDetailDTO.getCustomer().getPhoneNumber()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.customer.status").value(invoiceDetailDTO.getCustomer().getStatus()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.products").isArray());
    }

    @Test
    void testAddInvoice() throws Exception {
        when(invoiceService.addInvoice(invoiceDTO)).thenReturn(invoiceDTO);

        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/invoices")
                .contentType("application/json")
                .content("{\"id\":\"" + invoiceDTO.getId() + "\",\"customerId\":\"" + invoiceDTO.getCustomerId() + "\",\"invoiceAmount\":\"" + invoiceDTO.getInvoiceAmount() + "\",\"invoiceDate\":\"" + invoiceDTO.getInvoiceDate() + "\"}"))
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(invoiceDTO.getId().toString()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.customerId").value(invoiceDTO.getCustomerId().toString()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.invoiceAmount").value(invoiceDTO.getInvoiceAmount().toString()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.invoiceDate").value(invoiceDTO.getInvoiceDate().toString()));
    }

    @Test
    void testEditInvoice() throws Exception {
        UUID id = invoiceDTO.getId();

        when(invoiceService.editInvoice(id, invoiceDTO)).thenReturn(invoiceDTO);

        mockMvc.perform(MockMvcRequestBuilders.put("/api/v1/invoices/{id}", id)
                .contentType("application/json")
                .content("{\"id\":\"" + invoiceDTO.getId() + "\",\"customerId\":\"" + invoiceDTO.getCustomerId() + "\",\"invoiceAmount\":\"" + invoiceDTO.getInvoiceAmount() + "\",\"invoiceDate\":\"" + invoiceDTO.getInvoiceDate() + "\"}"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(invoiceDTO.getId().toString()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.customerId").value(invoiceDTO.getCustomerId().toString()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.invoiceAmount").value(invoiceDTO.getInvoiceAmount().toString()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.invoiceDate").value(invoiceDTO.getInvoiceDate().toString()));
    }

    @Test
    void testExportInvoiceToExcel() throws Exception {
        UUID id = invoiceDTO.getCustomerId();
        when(exportService.exportInvoicesToExcel(id, 7, 5)).thenReturn(new ByteArrayInputStream(new byte[0]));

        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/invoices/excel")
                .param("customerId", id.toString())
                .param("month", "7")
                .param("year", "5"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/vnd.ms-excel"))
                .andExpect(MockMvcResultMatchers.header().string(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=invoices.xlsx"));
    }

    @Test
    void testGenerateInvoicePdf() throws Exception {
        UUID id = invoiceDTO.getId();
        when(invoiceService.getInvoiceDetail(id)).thenReturn(invoiceDetailDTO);
        when(pdfService.generatePdf(invoiceDetailDTO)).thenReturn(new ByteArrayInputStream(new byte[0]));

        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/invoices/{id}/pdf", id)
                .contentType("application/json"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/pdf"))
                .andExpect(MockMvcResultMatchers.header().string(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=invoice.pdf"));
    }
}
