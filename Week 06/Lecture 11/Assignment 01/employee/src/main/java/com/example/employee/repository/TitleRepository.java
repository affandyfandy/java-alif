package com.example.employee.repository;

import com.example.employee.entity.Title;
import com.example.employee.entity.TitleId;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TitleRepository extends JpaRepository<Title, TitleId> {
}
