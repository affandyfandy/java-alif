package com.fsoft.lecture5.lecture5.mapper;

import com.fsoft.lecture5.lecture5.dto.EmployeeDTO;
import com.fsoft.lecture5.lecture5.model.Employee;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface EmployeeMapper {
    EmployeeDTO toEmployeeDTO(Employee employee);
    Employee toEmployee(EmployeeDTO employeeDTO);
}

