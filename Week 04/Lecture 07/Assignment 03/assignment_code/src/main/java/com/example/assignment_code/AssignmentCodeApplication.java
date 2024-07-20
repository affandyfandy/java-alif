package com.example.assignment_code;

import com.example.assignment_code.config.AppConfig;
import com.example.assignment_code.service.*;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

@SpringBootApplication
public class AssignmentCodeApplication {

	public static void main(String[] args) {
		SpringApplication.run(AssignmentCodeApplication.class, args);

		ConfigurableApplicationContext context = new AnnotationConfigApplicationContext(AppConfig.class);

		// Get the EmployeeServiceSetter bean (singleton)
		EmployeeServiceConstructor employeeServiceConstructor = context.getBean(EmployeeServiceConstructor.class);
		employeeServiceConstructor.notifyEmployee("john@example.com", "Here is details of your work");
		// Print the scope of the bean
		System.out.println("Scope of EmployeeServiceConstructor: " + context.isSingleton("employeeServiceConstructor"));

		String[] beanNamesForType = context.getBeanNamesForType(EmployeeServiceConstructor.class);
		for (String beanName : beanNamesForType) {
			String scope = context.getBeanFactory().getBeanDefinition(beanName).getScope();
			System.out.println("Bean: " + beanName + ", Scope: " + scope);
		}

		System.out.println();

		// Get the EmployeeServiceField bean (prototype)
		EmployeeServiceField employeeServiceField = context.getBean(EmployeeServiceField.class);
		employeeServiceField.notifyEmployee("john@example.com", "Here is details of your work");

		// Prototype beans will be different instances
		EmployeeServiceField employeeServiceField2 = context.getBean(EmployeeServiceField.class);
		employeeServiceField2.notifyEmployee("michael@example.com", "Here are details of your work");

		// Print the scope of the bean
		String[] beanNamesForTypeField = context.getBeanNamesForType(EmployeeServiceField.class);
		for (String beanName : beanNamesForTypeField) {
			String scope = context.getBeanFactory().getBeanDefinition(beanName).getScope();
			System.out.println("Bean: " + beanName + ", Scope: " + scope);
		}
	}

}
