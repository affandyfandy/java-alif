package MidtermExam.Group2.service.impl;

import MidtermExam.Group2.entity.Invoice;
import MidtermExam.Group2.entity.InvoiceProduct;
import MidtermExam.Group2.repository.InvoiceRepository;
import MidtermExam.Group2.service.ExportService;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.awt.*;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.UUID;

@Service
@Transactional
public class ExportServiceImpl implements ExportService {

    private final InvoiceRepository invoiceRepository;

    @Autowired
    public ExportServiceImpl(InvoiceRepository invoiceRepository) {
        this.invoiceRepository = invoiceRepository;
    }

    public ByteArrayInputStream exportInvoicesToExcel(UUID customerId, Integer month, Integer year) throws IOException {
        List<Invoice> invoices = invoiceRepository.findByCustomerAndDate(customerId, month, year);

        if (invoices.isEmpty()) {
            throw new IllegalArgumentException("No invoices found");
        }

        try (Workbook workbook = new XSSFWorkbook()) {
            Sheet sheet = workbook.createSheet("Invoices");

            sheet.setColumnWidth(1, 40 * 256);
            sheet.setColumnWidth(2, 20 * 256);
            sheet.setColumnWidth(3, 15 * 256);
            sheet.setColumnWidth(4, 40 * 256);
            sheet.setColumnWidth(5, 20 * 256);
            sheet.setColumnWidth(6, 40 * 256);
            sheet.setColumnWidth(7, 40 * 256);
            sheet.setColumnWidth(8, 17 * 256);
            sheet.setColumnWidth(9, 17 * 256);
            sheet.setColumnWidth(10, 17 * 256);

            int rowNum = 0;

            Font headerFont = workbook.createFont();
            headerFont.setBold(true);

            CellStyle headerStyle = workbook.createCellStyle();
            headerStyle.setFont(headerFont);

            if (invoices.isEmpty()) {
                Row emptyRow = sheet.createRow(rowNum++);
                emptyRow.createCell(0).setCellValue("No invoices found");
            }

            Row headerRow = sheet.createRow(rowNum++);

            Cell noHeader = headerRow.createCell(0);
            noHeader.setCellValue("No");
            noHeader.setCellStyle(headerStyle);

            Cell invoiceIdHeader = headerRow.createCell(1);
            invoiceIdHeader.setCellValue("Invoice ID");
            invoiceIdHeader.setCellStyle(headerStyle);

            Cell invoiceDateHeader = headerRow.createCell(2);
            invoiceDateHeader.setCellValue("Invoice Date");
            invoiceDateHeader.setCellStyle(headerStyle);

            Cell invoiceAmountHeader = headerRow.createCell(3);
            invoiceAmountHeader.setCellValue("Invoice Amount");
            invoiceAmountHeader.setCellStyle(headerStyle);

            Cell customerIdHeader = headerRow.createCell(4);
            customerIdHeader.setCellValue("Customer ID");
            customerIdHeader.setCellStyle(headerStyle);

            Cell customerNameHeader = headerRow.createCell(5);
            customerNameHeader.setCellValue("Customer Name");
            customerNameHeader.setCellStyle(headerStyle);

            Cell productIdHeader = headerRow.createCell(6);
            productIdHeader.setCellValue("Product ID");
            productIdHeader.setCellStyle(headerStyle);

            Cell productNameHeader = headerRow.createCell(7);
            productNameHeader.setCellValue("Product Name");
            productNameHeader.setCellStyle(headerStyle);

            Cell productPriceHeader = headerRow.createCell(8);
            productPriceHeader.setCellValue("Product Price");
            productPriceHeader.setCellStyle(headerStyle);

            Cell productQuantityHeader = headerRow.createCell(9);
            productQuantityHeader.setCellValue("Product Quantity");
            productQuantityHeader.setCellStyle(headerStyle);

            Cell productAmountHeader = headerRow.createCell(10);
            productAmountHeader.setCellValue("Product Amount");
            productAmountHeader.setCellStyle(headerStyle);

            for (Invoice invoice : invoices) {
                rowNum = createInvoiceSection(sheet, invoice, rowNum);
            }

            ByteArrayOutputStream out = new ByteArrayOutputStream();
            workbook.write(out);
            return new ByteArrayInputStream(out.toByteArray());
        }
    }

    private int createInvoiceSection(Sheet sheet, Invoice invoice, int rowNum) {

        CellStyle leftAlignStyle = sheet.getWorkbook().createCellStyle();
        leftAlignStyle.setAlignment(HorizontalAlignment.LEFT);

        Row invoiceRow = sheet.createRow(rowNum++);
        invoiceRow.createCell(0).setCellValue(rowNum - 1);
        invoiceRow.createCell(1).setCellValue(invoice.getId().toString());
        invoiceRow.createCell(2).setCellValue(invoice.getInvoiceDate().format(DateTimeFormatter.ofPattern("MMMM dd, yyyy HH:mm:ss")));
        invoiceRow.createCell(3).setCellValue(invoice.getInvoiceAmount().doubleValue());
        invoiceRow.createCell(4).setCellValue(invoice.getCustomer().getId().toString());
        invoiceRow.createCell(5).setCellValue(invoice.getCustomer().getName());

        for (int j = 0; j < invoice.getInvoiceProducts().size(); j++) {
            InvoiceProduct product = invoice.getInvoiceProducts().get(j);

            if (j > 0) {
                invoiceRow = sheet.createRow(rowNum++);
            }

            invoiceRow.createCell(0).setCellValue(rowNum - 1);
            invoiceRow.createCell(6).setCellValue(product.getProduct().getId().toString());
            invoiceRow.createCell(7).setCellValue(product.getProduct().getName());
            invoiceRow.createCell(8).setCellValue(product.getProduct().getPrice().doubleValue());
            invoiceRow.createCell(9).setCellValue(product.getQuantity());
            invoiceRow.createCell(10).setCellValue(product.getAmount().doubleValue());
        }

        return rowNum;
    }
}
