package com.example.employee.dto;

import com.example.employee.common.Gender;
import com.example.employee.entity.Employee;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EmployeeDTO {
    private Integer empNo;
    private LocalDate birthDate;
    private String firstName;
    private String lastName;
    private Gender gender;
    private LocalDate hireDate;
    private List<SalaryDTO> salaries;
    private List<TitleDTO> titles;

    public Employee mapToEmployeeEntity() {
        return Employee.builder()
                .empNo(this.getEmpNo())
                .birthDate(this.getBirthDate())
                .firstName(this.getFirstName())
                .lastName(this.getLastName())
                .gender(this.getGender())
                .hireDate(this.getHireDate())
                .salaries(Optional.ofNullable(this.getSalaries())
                        .map(salaries -> salaries.stream()
                                .map(SalaryDTO::mapToSalaryEntity)
                                .collect(Collectors.toSet()))
                        .orElse(Collections.emptySet()))
                .titles(Optional.ofNullable(this.getTitles())
                        .map(titles -> titles.stream()
                                .map(TitleDTO::mapToTitleEntity)
                                .collect(Collectors.toSet()))
                        .orElse(Collections.emptySet()))
                .build();
    }
}