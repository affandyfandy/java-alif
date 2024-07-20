package com.example.fsoft.employee_manager.service;

import com.example.fsoft.employee_manager.model.Employee;
import com.itextpdf.html2pdf.HtmlConverter;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.OptionalDouble;

@Service
public class PdfGenerateService {

    @Autowired
    private TemplateEngine templateEngine;

    public void generatePdf(List<Employee> employees, HttpServletResponse response) throws IOException {
        Context context = new Context();
        context.setVariable("employees", employees);

        // Calculate
        OptionalDouble maxSalary = employees.stream()
                .mapToDouble(Employee::getSalary)
                .max();
        Optional<Employee> maxSalaryEmployee = employees.stream()
                .filter(employee -> employee.getSalary() == maxSalary.getAsDouble())
                .findFirst();

        OptionalDouble minSalary = employees.stream()
                .mapToDouble(Employee::getSalary)
                .min();
        Optional<Employee> minSalaryEmployee = employees.stream()
                .filter(employee -> employee.getSalary() == minSalary.getAsDouble())
                .findFirst();

        double averageSalary = employees.stream()
                .mapToDouble(Employee::getSalary)
                .average()
                .orElse(0.0);

        String dateNow = java.time.LocalDate.now().toString();

        context.setVariable("maxSalary", maxSalary.orElse(0.0));
        context.setVariable("maxSalaryEmployee", maxSalaryEmployee.orElse(new Employee()).getName());
        context.setVariable("minSalary", minSalary.orElse(0.0));
        context.setVariable("minSalaryEmployee", minSalaryEmployee.orElse(new Employee()).getName());
        context.setVariable("averageSalary", averageSalary);
        context.setVariable("length", employees.size());
        context.setVariable("dateNow", dateNow);

        String htmlContent = templateEngine.process("pdf-template", context);

        ByteArrayOutputStream target = new ByteArrayOutputStream();
        HtmlConverter.convertToPdf(htmlContent, target);
        byte[] bytes = target.toByteArray();

        response.setContentType("application/pdf");
        response.setHeader("Content-Disposition", "attachment; filename=employees.pdf");
        response.setContentLength(bytes.length);
        response.getOutputStream().write(bytes);
    }
}
