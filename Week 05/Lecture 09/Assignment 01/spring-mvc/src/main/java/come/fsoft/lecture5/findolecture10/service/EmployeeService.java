package come.fsoft.lecture5.findolecture10.service;

import come.fsoft.lecture5.findolecture10.model.Employee;

import java.util.List;

public interface EmployeeService {
    List<Employee> findAll();

    Employee findById(int theId);

    void save(Employee theEmployee);

    void deleteById(int theId);
}
