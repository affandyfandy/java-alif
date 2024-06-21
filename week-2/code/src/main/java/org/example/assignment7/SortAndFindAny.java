package org.example.assignment7;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.ArrayList;

public class SortAndFindAny {
    // Method to sort a list by any field
    public static <T> void sortByField(List<T> list, Comparator<? super T> comparator) {
        list.sort(comparator);
    }

    // Method to find the item with the max value of any field
    public static <T> Optional<T> findMaxByField(List<T> list, Comparator<? super T> comparator) {
        return list.stream().max(comparator);
    }

    public static void main(String[] args) {
        List<Employee> employees = new ArrayList<>();
        employees.add(new Employee(101, "Alice", 30, 50000));
        employees.add(new Employee(102, "Bob", 25, 60000));
        employees.add(new Employee(103, "Charlie", 35, 55000));

        // Sorting by age
        sortByField(employees, Comparator.comparingInt(Employee::getAge));
        System.out.println("\nSorted by age: " + employees);

        // Sorting by salary
        sortByField(employees, Comparator.comparingInt(Employee::getSalary));
        System.out.println("\nSorted by salary: " + employees);

        // Finding the employee with the maximum age
        Optional<Employee> maxAgeEmployee = findMaxByField(employees, Comparator.comparingInt(Employee::getAge));
        maxAgeEmployee.ifPresent(employee -> System.out.println("\nEmployee with max age: " + employee));

        // Finding the employee with the maximum salary
        Optional<Employee> maxSalaryEmployee = findMaxByField(employees, Comparator.comparingInt(Employee::getSalary));
        maxSalaryEmployee.ifPresent(employee -> System.out.println("\nEmployee with max salary: " + employee));

    }
}
