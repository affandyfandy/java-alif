package com.example.assignment_code;

import com.example.assignment_code.config.AppConfig;
import com.example.assignment_code.service.*;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

@SpringBootApplication
public class AssignmentCodeApplication {

	public static void main(String[] args) {
		SpringApplication.run(AssignmentCodeApplication.class, args);

		AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(AppConfig.class);

		EmployeeServiceConstructor employeeServiceConstructor = context.getBean(EmployeeServiceConstructor.class);
		employeeServiceConstructor.notifyEmployee("john@example.com", "Here is details of your work");
		System.out.println();

		EmployeeServiceField employeeServiceField = context.getBean(EmployeeServiceField.class);
		employeeServiceField.notifyEmployee("john@example.com", "Here is details of your work");
		System.out.println();

		EmployeeServiceSetter employeeServiceSetter = context.getBean(EmployeeServiceSetter.class);
		employeeServiceSetter.notifyEmployee("john@example.com", "Here is details of your work");

		context.close();
	}

}
