package com.example.fsoft.employee_manager.controller;

import com.example.fsoft.employee_manager.model.Employee;
import com.example.fsoft.employee_manager.service.EmployeeService;
import com.example.fsoft.employee_manager.service.PdfGenerateService;
import com.example.fsoft.employee_manager.utils.FileUtils;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Controller
@RequestMapping("/employees")
public class EmployeeController {

    private final EmployeeService employeeService;

    private final PdfGenerateService pdfGenerateService;

    @Autowired
    public EmployeeController(EmployeeService employeeService, PdfGenerateService pdfGenerateService) {
        this.employeeService = employeeService;
        this.pdfGenerateService = pdfGenerateService;
    }


    @GetMapping
    public String getAllEmployees(Model model) {
        model.addAttribute("employees", employeeService.getAllEmployees());
        return "employees";
    }

    @GetMapping("/create")
    public String createEmployee(Model model) {
        model.addAttribute("employee", new Employee());
        model.addAttribute("endpoint", "create");
        return "form-employee";
    }

    @PostMapping("/create")
    public String createEmployee(@ModelAttribute("employee") Employee employee, Model model) {
        employeeService.addEmployee(employee);
        model.addAttribute("message", "Employee added successfully");
        return "redirect:/employees";
    }

    @GetMapping("/update/{id}")
    public String updateEmployee(@PathVariable String id, Model model) {
        model.addAttribute("employee", employeeService.getEmployeeById(id));
        model.addAttribute("endpoint", "update/" + id);
        return "form-employee";
    }

    @PostMapping("/update/{id}")
    public String updateEmployee(@PathVariable String id, @ModelAttribute("employee") Employee employee, Model model) {
        employeeService.updateEmployee(employee);
        model.addAttribute("message", "Employee updated successfully");
        return "redirect:/employees";
    }

    @PostMapping("/delete/{id}")
    public String deleteEmployee(@PathVariable String id, Model model) {
        employeeService.deleteEmployee(id);
        model.addAttribute("message", "Employee deleted successfully");
        return "redirect:/employees";
    }

    @PostMapping("/import")
    public String importEmployees(@RequestParam("file") MultipartFile file, Model model) {
        if (file.isEmpty()) {
            model.addAttribute("message", "Please select a CSV file to upload");
            return "import-employees";
        }

        try {
            List<Employee> employees = FileUtils.parseCSVFile(file);
            employeeService.saveAllEmployees(employees);
            model.addAttribute("message", "File uploaded successfully");
        } catch (Exception e) {
            model.addAttribute("message", "Could not upload the file: " + e.getMessage());
            e.printStackTrace();
        }
        return "redirect:/employees";
    }

    @GetMapping("/pdf")
    public void generatePdf(HttpServletResponse response) {
        List<Employee> employees = employeeService.getAllEmployees();
        try {
            pdfGenerateService.generatePdf(employees, response);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
