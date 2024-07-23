package com.example.employee.service;

import com.example.employee.dto.TitleDTO;

public interface TitleService {
    TitleDTO addTitle(Integer id, TitleDTO titleDTO);
    TitleDTO updateTitle(Integer id, TitleDTO titleDTO);
}
