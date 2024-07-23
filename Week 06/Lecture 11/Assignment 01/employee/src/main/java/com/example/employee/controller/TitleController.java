package com.example.employee.controller;

import com.example.employee.dto.TitleDTO;
import com.example.employee.service.TitleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/titles")
public class TitleController {
    private final TitleService titleService;

    @Autowired
    public TitleController(TitleService titleService) {
        this.titleService = titleService;
    }

    @PostMapping("/{id}")
    public ResponseEntity<TitleDTO> addTitle(@PathVariable("id") Integer id, @RequestBody TitleDTO titleDTO) {
        TitleDTO newTitleDTO = titleService.addTitle(id, titleDTO);
        return ResponseEntity.ok(newTitleDTO);
    }

    @PutMapping("/{id}")
    public ResponseEntity<TitleDTO> updateTitle(@PathVariable("id") Integer id, @RequestBody TitleDTO titleDTO) {
        TitleDTO updatedTitleDTO = titleService.updateTitle(id, titleDTO);
        return ResponseEntity.ok(updatedTitleDTO);
    }
}
