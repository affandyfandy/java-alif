package MidtermExam.Group2.service.impl;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import MidtermExam.Group2.dto.RevenueDTO;
import MidtermExam.Group2.repository.InvoiceRepository;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class RevenueServiceImpl {

    private final InvoiceRepository invoiceRepository;

    @Autowired
    public RevenueServiceImpl(InvoiceRepository invoiceRepository) {
        this.invoiceRepository = invoiceRepository;
    }

    public RevenueDTO getRevenueByDay(LocalDate date) {
        LocalDateTime startDateTime = date.atStartOfDay();
        LocalDateTime endDateTime = date.atTime(LocalTime.MAX);
        BigDecimal totalRevenue = invoiceRepository.calculateTotalRevenueByDateTime(startDateTime, endDateTime);
        return createRevenueReportDTO(date.toString(), totalRevenue);
    }

    public RevenueDTO getRevenueByMonth(int year, int month) {
        LocalDate startDate = LocalDate.of(year, month, 1);
        LocalDate endDate = startDate.withDayOfMonth(startDate.lengthOfMonth());
        LocalDateTime startDateTime = startDate.atStartOfDay();
        LocalDateTime endDateTime = endDate.atTime(LocalTime.MAX);
        BigDecimal totalRevenue = invoiceRepository.calculateTotalRevenueByDateTime(startDateTime, endDateTime);
        return createRevenueReportDTO(startDate.getMonth().toString() + " " + year, totalRevenue);
    }

    public RevenueDTO getRevenueByYear(int year) {
        LocalDate startDate = LocalDate.of(year, 1, 1);
        LocalDate endDate = LocalDate.of(year, 12, 31);
        LocalDateTime startDateTime = startDate.atStartOfDay();
        LocalDateTime endDateTime = endDate.atTime(LocalTime.MAX);
        BigDecimal totalRevenue = invoiceRepository.calculateTotalRevenueByDateTime(startDateTime, endDateTime);
        return createRevenueReportDTO(String.valueOf(year), totalRevenue);
    }

    private RevenueDTO createRevenueReportDTO(String period, BigDecimal totalRevenue) {
        RevenueDTO reportDTO = new RevenueDTO();
        reportDTO.setPeriod(period);
        reportDTO.setTotalRevenue(totalRevenue);
        return reportDTO;
    }
}
