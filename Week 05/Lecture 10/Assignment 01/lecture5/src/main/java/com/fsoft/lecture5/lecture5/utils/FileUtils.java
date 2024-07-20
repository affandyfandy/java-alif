package com.fsoft.lecture5.lecture5.utils;

import com.fsoft.lecture5.lecture5.mapper.EmployeeMapper;
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
import java.util.UUID;

@Getter
@Component
public class FileUtils {

    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private EmployeeMapper employeeMapper;

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
            UUID id = UUID.randomUUID();
            String name = row[0];
            LocalDate dateOfBirth = DateUtils.parseDate(row[1]);
            String address = row[2];
            String department = row[3];
            String email = row[4];
            String phone = row[5];

            Employee employee = new Employee(id, name, dateOfBirth, address, department, email, phone);
            employeeRepository.save(employee);
        }
    }
}