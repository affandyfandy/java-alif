package com.example.fsoft.employee_manager.utils;

import com.example.fsoft.employee_manager.model.Employee;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.time.format.SignStyle;
import java.time.temporal.ChronoField;
import java.util.ArrayList;
import java.util.List;

public class FileUtils {
    public static List<Employee> parseCSVFile(MultipartFile file) throws Exception {
        List<Employee> employees = new ArrayList<>();
        DateTimeFormatter formatter = new DateTimeFormatterBuilder()
                .appendValue(ChronoField.DAY_OF_MONTH, 1, 2, SignStyle.NOT_NEGATIVE)
                .appendLiteral('/')
                .appendValue(ChronoField.MONTH_OF_YEAR, 1, 2, SignStyle.NOT_NEGATIVE)
                .appendLiteral('/')
                .appendValue(ChronoField.YEAR, 4)
                .toFormatter();

        try (BufferedReader br = new BufferedReader(new InputStreamReader(file.getInputStream(), StandardCharsets.UTF_8))) {
            String line;
            br.readLine(); // Skip the header
            while ((line = br.readLine()) != null) {
                String[] data = line.split(",");
                Employee employee = new Employee();
                employee.setId(data[0]);
                employee.setName(data[1]);
                employee.setDateOfBirth(LocalDate.parse(data[2], formatter));
                employee.setAddress(data[3]);
                employee.setDepartment(data[4]);
                employee.setSalary(Double.valueOf(data[5]));
                employees.add(employee);
            }
        } catch (Exception e) {
            throw new Exception("Failed to parse CSV file: " + e.getMessage());
        }

        return employees;
    }
}
