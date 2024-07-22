package com.example.employee.entity;

import com.example.employee.dto.TitleDTO;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.Date;

@Entity
@Table(name = "titles")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@IdClass(TitleId.class)
public class Title {

    @Id
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "emp_no", nullable = false)
    private Employee employee;

    @Id
    @Column(name = "title", length = 50)
    private String title;

    @Id
    @Column(name = "from_date")
    private LocalDate fromDate;

    @Column(name = "to_date")
    private LocalDate toDate;

    public TitleDTO mapToTitleDTO() {
        return TitleDTO.builder()
                .title(this.getTitle())
                .fromDate(this.getFromDate())
                .toDate(this.getToDate())
                .build();
    }
}