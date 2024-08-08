package MidtermExam.Group2.service;

import MidtermExam.Group2.dto.RevenueDTO;
import MidtermExam.Group2.repository.InvoiceRepository;
import MidtermExam.Group2.service.impl.RevenueServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {RevenueServiceImpl.class})
public class RevenueServiceTest {
    @Autowired
    private RevenueServiceImpl revenueServiceImpl;

    @MockBean
    private InvoiceRepository invoiceRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    /**
     * Method under test: {@link RevenueServiceImpl#getRevenueByDay(LocalDate)}
     */
    @Test
    void testGetRevenueByDay_ReturnRevenueDTO() {
        LocalDate date = LocalDate.of(2023, 8, 1);
        LocalDateTime startDateTime = date.atStartOfDay();
        LocalDateTime endDateTime = date.atTime(LocalTime.MAX);
        BigDecimal totalRevenue = BigDecimal.valueOf(1000);

        when(invoiceRepository.calculateTotalRevenueByDateTime(startDateTime, endDateTime)).thenReturn(totalRevenue);

        RevenueDTO result = revenueServiceImpl.getRevenueByDay(date);

        assertThat(result).isNotNull();
        assertThat(result.getPeriod()).isEqualTo("2023-08-01");
        assertThat(result.getTotalRevenue()).isEqualTo(totalRevenue);

        verify(invoiceRepository).calculateTotalRevenueByDateTime(startDateTime, endDateTime);
    }

    /**
     * Method under test: {@link RevenueServiceImpl#getRevenueByMonth(int, int)}
     */
    @Test
    void testGetRevenueByMonth_ReturnRevenueDTO() {
        int year = 2023;
        int month = 8;
        LocalDate startDate = LocalDate.of(year, month, 1);
        LocalDate endDate = startDate.withDayOfMonth(startDate.lengthOfMonth());
        LocalDateTime startDateTime = startDate.atStartOfDay();
        LocalDateTime endDateTime = endDate.atTime(LocalTime.MAX);
        BigDecimal totalRevenue = BigDecimal.valueOf(1000);

        when(invoiceRepository.calculateTotalRevenueByDateTime(startDateTime, endDateTime)).thenReturn(totalRevenue);

        RevenueDTO result = revenueServiceImpl.getRevenueByMonth(year, month);

        assertThat(result).isNotNull();
        assertThat(result.getPeriod()).isEqualTo("AUGUST 2023");
        assertThat(result.getTotalRevenue()).isEqualTo(totalRevenue);

        verify(invoiceRepository).calculateTotalRevenueByDateTime(startDateTime, endDateTime);
    }

    /**
     * Method under test: {@link RevenueServiceImpl#getRevenueByYear(int)}
     */
    @Test
    void testGetRevenueByYear_ReturnRevenueDTO() {
        int year = 2023;
        LocalDate startDate = LocalDate.of(year, 1, 1);
        LocalDate endDate = LocalDate.of(year, 12, 31);
        LocalDateTime startDateTime = startDate.atStartOfDay();
        LocalDateTime endDateTime = endDate.atTime(LocalTime.MAX);
        BigDecimal totalRevenue = BigDecimal.valueOf(1000);

        when(invoiceRepository.calculateTotalRevenueByDateTime(startDateTime, endDateTime)).thenReturn(totalRevenue);

        RevenueDTO result = revenueServiceImpl.getRevenueByYear(year);

        assertThat(result).isNotNull();
        assertThat(result.getPeriod()).isEqualTo("2023");
        assertThat(result.getTotalRevenue()).isEqualTo(totalRevenue);

        verify(invoiceRepository).calculateTotalRevenueByDateTime(startDateTime, endDateTime);
    }

}
