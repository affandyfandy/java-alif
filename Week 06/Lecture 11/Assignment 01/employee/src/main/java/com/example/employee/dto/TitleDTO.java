package com.example.employee.dto;

import com.example.employee.entity.Title;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TitleDTO {
    private String title;
    private LocalDate fromDate;
    private LocalDate toDate;

    public Title mapToTitleEntity() {
        return Title.builder()
                .title(this.getTitle())
                .fromDate(this.getFromDate())
                .toDate(this.getToDate())
                .build();
    }
}
