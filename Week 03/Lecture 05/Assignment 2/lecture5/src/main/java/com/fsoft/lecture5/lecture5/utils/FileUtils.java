package com.fsoft.lecture5.lecture5.utils;

import com.fsoft.lecture5.lecture5.model.Employee;
import com.fsoft.lecture5.lecture5.repository.EmployeeRepository;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;
import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvValidationException;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Getter
@Component
public class FileUtils {

    @Autowired
    private EmployeeRepository employeeRepository;

    public void importData(MultipartFile file) throws IOException, CsvValidationException {
        List<String[]> csvData = new ArrayList<>();

        try (CSVReader reader = new CSVReader(new InputStreamReader(file.getInputStream()))) {
            String[] line;
            while ((line = reader.readNext()) != null) {
                csvData.add(line);
            }
        }

        for (int i = 1; i < csvData.size(); i++) {
            String[] row = csvData.get(i);
            String id = row[0];
            String name = row[1];
            LocalDate dateOfBirth = DateUtils.parseDate(row[2]);
            String address = row[3];
            String department = row[4];

            Employee employee = new Employee(id, name, dateOfBirth, address, department);
            employeeRepository.save(employee);
        }
    }
}