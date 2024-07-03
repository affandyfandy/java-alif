package com.example.employee_manager;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@EnableTransactionManagement
public class EmployeeManagerApplication {

	public static void main(String[] args) {
		SpringApplication.run(EmployeeManagerApplication.class, args);
	}

}
