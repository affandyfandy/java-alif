package MidtermExam.Group2.controller;

import MidtermExam.Group2.dto.RevenueDTO;
import MidtermExam.Group2.service.impl.RevenueServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.math.BigDecimal;
import java.time.LocalDate;

import static org.mockito.Mockito.when;

@WebMvcTest(controllers = RevenueController.class)
public class RevenueControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private RevenueServiceImpl revenueService;

    private RevenueDTO revenueDTO;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        revenueDTO = new RevenueDTO("2024-07-05", new BigDecimal(1000));
    }

    @Test
    void testGetRevenueByDay() throws Exception {
        LocalDate date = LocalDate.of(2024, 7, 5);

        when(revenueService.getRevenueByDay(date)).thenReturn(revenueDTO);

        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/revenue/day")
                .param("date", date.toString()))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.period").value(revenueDTO.getPeriod()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.totalRevenue").value(revenueDTO.getTotalRevenue().toString()));
    }

    @Test
    void testGetRevenueByMonth() throws Exception {
        int year = 2024;
        int month = 7;

        when(revenueService.getRevenueByMonth(year, month)).thenReturn(revenueDTO);

        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/revenue/month")
                .param("year", String.valueOf(year))
                .param("month", String.valueOf(month)))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.period").value(revenueDTO.getPeriod()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.totalRevenue").value(revenueDTO.getTotalRevenue().toString()));
    }

    @Test
    void testGetRevenueByYear() throws Exception {
        int year = 2024;

        when(revenueService.getRevenueByYear(year)).thenReturn(revenueDTO);

        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/revenue/year")
                .param("year", String.valueOf(year)))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.period").value(revenueDTO.getPeriod()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.totalRevenue").value(revenueDTO.getTotalRevenue().toString()));
    }
}
