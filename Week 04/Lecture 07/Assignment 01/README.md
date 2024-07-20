# Assignment 1 - Lecture 7 Spring Core

## 1.1 What is advantages and drawbacks of DI ?​

`Dependency Injection` (DI) allows objects to declare their dependencies either through `constructor arguments`, `factory method arguments`, or `settable properties`. The container then injects these dependencies during the `bean`'s instantiation, contrasting with traditional approaches where objects directly manage their dependencies or use the Service Locator pattern. This `inversion of control` enhances modularity and testability in software design.

**Advantages of Dependency Injection in Spring Boot**

1. DI allowing components to be less dependent on each other. Each component focuses on its spesific role. By this, which make the application more `modular` and `flexible`.

2. Dependencies can be easily `mocked` in unit test, easier to test in `isolated` testing of indivitual components. The tests can focus on the behavior of the component. 

3. `Reusable` components, the components can be reused across different part or different applications.

4. DI allows centralized configuration of beans and the dependencies, also it support for different configuration environment like `development`, `testing`, and `production`. Which makes it `easier` to manage application configurations.

5. Build `scalable` architectures by easy integration of new components without modifying existing code, and changes in dependencies are easier to manage, which has a `scalability` and `maintainability`.

6. Simplified resource management, spring boot can manages the `lifecycle of beans`, `handling creation`, `initialization`, and `destruction`.

**Drawbacks of Dependency Injection in Spring Boot**

1. DI introduces additional layers of `abstraction`, which makes the code harder to follow.

2. `Misconfigurations` in the DI setup can lead to `runtime errors`, and it can be harder to trace and debug issues related to the wiring of components.

3. The process of `scanning`, `instantiating`, and `wiring beans` can add to the application's startup time, also managing a large number of beans and dependencies increasing memory consumption and can make performance overhead.

4. Too much reliance on DI can lead to `overcomplicated` designs and unnecessary abstraction layers, which can reduce the code clarity.

5. Managing transitive dependencies can become complex, and improper setup can makes circular dependencies that causing application startup `failures`.

## 1.2 Create class employee and convert given bean declaration in XML to Java Configuration using @Bean

[Full Code](demo/src/main/java/com/example/lecture7/assign1/)

Create an Employee class (id, name, age) and convert given bean declaration in XML to Java configuration using @Bean with constructor injection, using @Configuration, and using setter-based dependency injection and field dependency.

**EmployeeWork.java**

[EmployeeWork.java](demo/src/main/java/com/example/lecture7/assign1/EmployeeWork.java)

```java
public class EmployeeWork {
    public void work() {
        System.out.println("Working ....");
    }
}
```

This class defines a method `work()` that prints to the console.

**Employee.java with Setter-Based**

[Employee.java](demo/src/main/java/com/example/lecture7/assign1/Employee.java)

```java
public class Employee {
    private int id;
    private String name;
    private int age;
    private EmployeeWork employeeWork;

    public Employee(int id, String name, int age) {
        this.id = id;
        this.name = name;
        this.age = age;
    }

    @Autowired
    public void setEmployeeWork(EmployeeWork employeeWork) {
        this.employeeWork = employeeWork;
    }

    public void working() {
        System.out.println("My Name is: " + name);
        employeeWork.work();
    }
}
```

This class has a constructor `Employee()` that initializes the employee's `id`, `name` and `age`. `@Autowired` marks the `setEmployeeWork()` method to be used for dependency injection, this method will injects an instance of `EmployeeWork` into the `employeeWork` field.

**AppConfig.java with Setter-Based**

[AppConfig.java](demo/src/main/java/com/example/lecture7/assign1/AppConfig.java)

```java
@Configuration
public class AppConfig {

    @Bean
    public EmployeeWork employeeWork() {
        return new EmployeeWork();
    }

    @Bean
    public Employee employee() {
        Employee employee = new Employee(1,"GL", 25);
        employee.setEmployeeWork(employeeWork());
        return employee;
    }
}
```

The `@Configuration` annotation indicates that `AppConfig` class defines Spring bean configurations. The `@Bean public EmployeeWork employeeWork()` defines a bean of type EmployeeWork, this method creates and returns a new instance of EmployeeWork. Also, the `@Bean public Employee employee()` defines a bean of type Employee. This method will creates a new Employee instance, calls `setEmployeeWork(employeeWork())` to inject an instance of EmployeeWork into the Employee instance.

**Employee.java with Field-Based**

[Employee.java](demo/src/main/java/com/example/lecture7/assign1/Employee.java)

```java
public class Employee {
    private String name;

    @Autowired
    private EmployeeWork employeeWork;

    public Employee(String name) {
        this.name = name;
    }

    public void working() {
        System.out.println("My Name is: " + name);
        employeeWork.work();
    }
}

```

The `EmployeeWork` dependency in `employeeWork` field is injected directly into the field using `@Autowired`. The other field is set with the constructor.

**AppConfig.java with Field-Based**

[AppConfig.java](demo/src/main/java/com/example/lecture7/assign1/AppConfig.java)

```java
@Configuration
public class AppConfig {

    @Bean
    public EmployeeWork employeeWork() {
        return new EmployeeWork();
    }

    @Bean
    public Employee employee() {
        return new Employee(1,"GL", 25);
    }
}
```

`@Configuration` indicates that this class is a source of bean definitions, and processing to register beans and configure the Spring container. `@Bean` method for `EmployeeWork` indicating it returns a spring managed bead and returns a new instance of `EmployeeWork`. `@Bean` method for `Employee` does not manually set the `employeeWork` field, instead it relies on Spring field injection. So when spring creates the `Employee` bean, it detects the `@Autowired` annotation on the `employeeWork` field and automatically injects the `EmployeeWork` bean.

**Application**

[DemoApplication.java](demo/src/main/java/com/example/lecture7/assign1/DemoApplication.java)

```java
@SpringBootApplication
public class DemoApplication {

	public static void main(String[] args) {
		SpringApplication.run(DemoApplication.class, args);

		ApplicationContext context = new AnnotationConfigApplicationContext(AppConfig.class);
		Employee employee = context.getBean(Employee.class);
		employee.working();
	}

}
```

Entry point for the spring boot application. The `ApplicationContext context = new AnnotationConfigApplicationContext(AppConfig.class)` creates an application context using the Java configuration `AppConfig`. `context.getBean(Employee.class)` retrieves the Employee bean from the application context and calls the `working` method on the `Employee` bean.