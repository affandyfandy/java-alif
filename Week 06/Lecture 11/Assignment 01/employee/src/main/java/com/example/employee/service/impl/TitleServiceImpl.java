package com.example.employee.service.impl;

import com.example.employee.dto.TitleDTO;
import com.example.employee.entity.Employee;
import com.example.employee.entity.Title;
import com.example.employee.entity.TitleId;
import com.example.employee.repository.EmployeeRepository;
import com.example.employee.repository.TitleRepository;
import com.example.employee.service.TitleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TitleServiceImpl implements TitleService {

    private final TitleRepository titleRepository;
    private final EmployeeRepository employeeRepository;

    @Autowired
    public TitleServiceImpl(TitleRepository titleRepository, EmployeeRepository employeeRepository) {
        this.titleRepository = titleRepository;
        this.employeeRepository = employeeRepository;
    }

    /**
     * Add title for employee
     * @param id employee id
     * @param titleDTO title information
     * @return title DTO
     */
    @Override
    public TitleDTO addTitle(Integer id, TitleDTO titleDTO) {
        Employee employee = employeeRepository.findById(id).
                orElseThrow(() -> new RuntimeException("Employee not found"));
        Title title = new Title();
        title.setEmployee(employee);
        title.setTitle(titleDTO.getTitle());
        title.setFromDate(titleDTO.getFromDate());
        title.setToDate(titleDTO.getToDate());
        title = titleRepository.save(title);
        return mapToTitleDTO(title);
    }

    /**
     * Update title for employee
     * @param id employee id
     * @param titleDTO title information
     * @return updated title DTO
     */
    @Override
    public TitleDTO updateTitle(Integer id, TitleDTO titleDTO) {
        Title title = titleRepository.findById(new TitleId(id, titleDTO.getTitle(), titleDTO.getFromDate())).
                orElseThrow(() -> new RuntimeException("Title not found"));
        title.setTitle(titleDTO.getTitle());
        title.setToDate(titleDTO.getToDate());
        title = titleRepository.save(title);
        return mapToTitleDTO(title);
    }

    /**
     * Map title entity to title DTO
     * @param title title entity
     * @return title DTO
     */
    private TitleDTO mapToTitleDTO(Title title) {
        TitleDTO titleDTO = new TitleDTO();
        titleDTO.setTitle(title.getTitle());
        titleDTO.setFromDate(title.getFromDate());
        titleDTO.setToDate(title.getToDate());
        return titleDTO;
    }
}
