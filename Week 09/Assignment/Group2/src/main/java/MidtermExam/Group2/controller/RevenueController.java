package MidtermExam.Group2.controller;

import java.time.LocalDate;
import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import MidtermExam.Group2.dto.RevenueDTO;
import MidtermExam.Group2.service.impl.RevenueServiceImpl;

@RestController
@RequestMapping("/api/v1/revenue")
public class RevenueController {

    private RevenueServiceImpl revenueService;

    @Autowired
    public RevenueController(RevenueServiceImpl revenueService) {
        this.revenueService = revenueService;
    }

    @GetMapping("/day")
    public ResponseEntity<RevenueDTO> getRevenueByDay(
            @RequestParam("date") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDate date) {
        RevenueDTO revenue = revenueService.getRevenueByDay(date);
        return ResponseEntity.ok(revenue);
    }

    @GetMapping("/month")
    public ResponseEntity<RevenueDTO> getRevenueByMonth(
            @RequestParam("year") int year,
            @RequestParam("month") int month) {
        RevenueDTO revenue = revenueService.getRevenueByMonth(year, month);
        return ResponseEntity.ok(revenue);
    }

    @GetMapping("/year")
    public ResponseEntity<RevenueDTO> getRevenueByYear(@RequestParam("year") int year) {
        RevenueDTO revenue = revenueService.getRevenueByYear(year);
        return ResponseEntity.ok(revenue);
    }
}
