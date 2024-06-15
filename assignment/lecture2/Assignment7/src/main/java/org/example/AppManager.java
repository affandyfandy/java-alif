package org.example;

import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class AppManager {
    private static AppManager instance = null;
    private List<Employee> employees;

    private AppManager() {
        employees = new ArrayList<>();
    }

    public static AppManager getInstance() {
        if (instance == null) {
            instance = new AppManager();
        }
        return instance;
    }

    public void start() {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.println("0 - Break");
            System.out.println("1 - Select file, import data");
            System.out.println("2 - Add new Employee");
            System.out.println("3 - Filter by name (like), id (like), date of birth - year (equal), department (equal)");
            System.out.println("4 - Filter and export to csv file, order by date of birth");
            System.out.println("5 - Print all employees");
            System.out.print("Enter your choice: ");

            int choice = scanner.nextInt();
            scanner.nextLine();
            switch (choice) {
                case 0:
                    System.out.println("Exiting...");
                    return;
                case 1:
                    importData(scanner);
                    break;
                case 2:
                    addNewEmployee(scanner);
                    break;
                case 3:
                    filterEmployees(scanner);
                    break;
                case 4:
                    exportFilteredEmployees(scanner);
                    break;
                case 5:
                    printAllEmployees();
                    break;
                default:
                    System.out.println("Invalid choice, please try again.");
            }
        }
    }

    private void importData(Scanner scanner) {
        System.out.print("Enter the path of the xlsx file: ");
//        String xlsxFilePath = scanner.nextLine();
        // src/main/java/org/example/file/ImportData.xlsx
        String xlsxFilePath = "src/main/java/org/example/file/ImportData.xlsx";
        System.out.print("Enter the path to save the csv file: ");
//        String csvFilePath = scanner.nextLine();
        // src/main/java/org/example/file/ExportData.csv
        String csvFilePath = "src/main/java/org/example/file/ExportData.csv";
        try {
            FileUtils.convertXlsxToCsv(xlsxFilePath, csvFilePath);
            System.out.println("File converted successfully. Data in the CSV file:");

            List<String[]> csvData = FileUtils.readCsv(csvFilePath);
            int i = 0;

            for (String[] row : csvData) {
                System.out.println(String.join(", ", row));
                if (i == 0) {
                    i++;
                    continue;
                }
                String id = row[0];
                String name = row[1];
                LocalDate dateOfBirth = DateUtils.parseDate(row[2]);
                String address = row[3];
                String department = row[4];

                Employee employee = new Employee(id, name, dateOfBirth, address, department);
                employees.add(employee);
            }
        } catch (IOException e) {
            System.out.println("Error converting file: " + e.getMessage());
        }
    }

    private void addNewEmployee(Scanner scanner) {
        System.out.print("Enter employee ID: ");
        String id = scanner.nextLine();
        System.out.print("Enter employee name: ");
        String name = scanner.nextLine();
        System.out.print("Enter date of birth (dd/MM/yyyy): ");
        String dobStr = scanner.nextLine();
        LocalDate dateOfBirth = DateUtils.parseDate(dobStr);
        System.out.print("Enter address: ");
        String address = scanner.nextLine();
        System.out.print("Enter department: ");
        String department = scanner.nextLine();

        Employee employee = new Employee(id, name, dateOfBirth, address, department);
        employees.add(employee);
        System.out.println("Employee added successfully.");
    }

    private void filterEmployees(Scanner scanner) {
        System.out.print("Enter filter criteria (name, id, date of birth (yyyy), department): ");
        String filterCriteria = scanner.nextLine();
        boolean isNumeric = filterCriteria.chars().allMatch(Character::isDigit);

        List<Employee> filteredEmployees = new ArrayList<>();
        for (Employee emp : employees) {
            boolean matches = emp.getName().contains(filterCriteria) ||
                    emp.getId().contains(filterCriteria) ||
                    emp.getDepartment().equals(filterCriteria);

            if (isNumeric) {
                try {
                    int year = Integer.parseInt(filterCriteria);
                    matches = matches || emp.getDateOfBirth().getYear() == year;
                } catch (NumberFormatException e) {
                    System.out.println("Invalid input: " + filterCriteria);
                }
            }
            if (matches) {
                filteredEmployees.add(emp);
            }
        }

        System.out.println("Filtered Employees:");
        for (Employee emp : filteredEmployees) {
            System.out.println(emp);
        }
    }

    private void exportFilteredEmployees(Scanner scanner) {
        System.out.print("Enter the path to save the csv file: ");
//        String csvFilePath = scanner.nextLine();
        String csvFilePath = "src/main/java/org/example/file/ExportData.csv";

        employees.sort((e1, e2) -> e1.getDateOfBirth().compareTo(e2.getDateOfBirth()));

        List<String[]> data = new ArrayList<>();
        data.add(new String[]{"ID", "Name", "Date of Birth", "Address", "Department"});
        for (Employee emp : employees) {
            data.add(new String[]{emp.getId(), emp.getName(), DateUtils.formatDate(emp.getDateOfBirth()), emp.getAddress(), emp.getDepartment()});
        }

        try {
            FileUtils.writeCsv(csvFilePath, data);
            System.out.println("Employees exported successfully.");
        } catch (IOException e) {
            System.out.println("Error exporting employees: " + e.getMessage());
        }
    }

    public void printAllEmployees() {
        System.out.println("All Employees:");
        for (Employee emp : employees) {
            System.out.println(emp);
        }
    }
}

