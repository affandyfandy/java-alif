# Assignment 2 - Lecture 7 Spring Core

## 2.1 Working with annotations

[Full Code](assignment_code/src/main/java/com/example/assignment_code/)

Project Structure

```bash
/src/main/java/com/example/assignment_code/
│
├── AssignmentCodeApplication.java
│
├── service/
│   ├── EmailService.java
│   ├── imp/
│   │   └── EmailServiceImpl.java
│   │
│   ├── EmployeeServiceConstructor.java
│   ├── EmployeeServiceField.java
│   └── EmployeeServiceSetter.java
│
└── config/
    └── AppConfig.java
```

### Create EmailService interface and EmailServiceImpl, add method for sending email 

`EmailService` interface:

[EmailService.java](assignment_code/src/main/java/com/example/assignment_code/service/EmailService.java)

```java
package com.example.demo.service;

public interface EmailService {
    void sendEmail(String to, String subject, String body);
}
```

This `interface` defines the contract for sending emails in the method. It declares a method `sendEmail` that takes a `to` recipient email address, `subject`, and `body` of the email.

\
`EmailServiceImpl` implementation:

[EmailService.java](assignment_code/src/main/java/com/example/assignment_code/service/impl/EmailServiceImpl.java)

```java
@Component
@Qualifier("primaryEmailService")
public class EmailServiceImpl implements EmailService {
    // Send an email
    @Override
    public void sendEmail(String to, String subject, String body) {
        System.out.println("To: " + to);
        System.out.println("Subject: " + subject);
        System.out.println("Body: " + body);
    }
}
```

Implementation of the `EmailService` interface. The `@Service` annotation indicate this class is a service component and registered as a `bean`. `@Qualifier("primaryEmailService")` used to give this service a specific qualifier name, helps in distinguishing it when multiple implementations are available.

### Create EmployeeService class and using EmailService with DI to send email to employee about their works, demo with 3 ways (constructor, field, setter)

`EmployeeService` with constructor-based DI implementation:

[EmployeeServiceConstructor.java](assignment_code/src/main/java/com/example/assignment_code/service/EmployeeServiceConstructor.java)

```java
@Service
public class EmployeeServiceConstructor {
    private final EmailService emailService;

    @Autowired
    public EmployeeServiceConstructor(@Qualifier("primaryEmailService") EmailService emailService) {
        this.emailService = emailService;
    }

    public void notifyEmployee(String receiver, String workDetails) {
        emailService.sendEmail(receiver, "(Constructor) Work Notification", workDetails);
    }
}
```

This class uses `constructor`-based dependency injection to inject the `EmailService`. The `@Autowired` on the constructor to use this constructor to inject dependencies. `@Qualifier("primaryEmailService")` ensures the `primaryEmailService` implementation is injected.

\
`EmployeeService` with field-based DI implementation:

[EmployeeServiceField.java](assignment_code/src/main/java/com/example/assignment_code/service/EmployeeServiceField.java)

```java
@Service
public class EmployeeServiceField {
    @Autowired
    @Qualifier("primaryEmailService")
    private EmailService emailService;

    public void notifyEmployee(String receiver, String workDetails) {
        emailService.sendEmail(receiver, "(Field) Work Notification", workDetails);
    }
}
```

This class uses `field`-based injection to inject the `EmailService`. The `@Autowired` on the field inject the dependency directly into the field.

\
`EmployeeService` with setter-based DI implementation:

[EmployeeServiceSetter.java](assignment_code/src/main/java/com/example/assignment_code/service/EmployeeServiceSetter.java)

```java
@Service
public class EmployeeServiceSetter {
    private EmailService emailService;

    @Autowired
    public void setEmailService(@Qualifier("primaryEmailService") EmailService emailService) {
        this.emailService = emailService;
    }

    public void notifyEmployee(String receiver, String workDetails) {
        emailService.sendEmail(receiver, "(Setter) Work Notification", workDetails);
    }
}
```

This class uses `setter`-based dependency injection to inject the `EmailService`. The `@Autowired` on the setter method indicates to use this method to inject the dependencies.

\
`AppConfig` configuration implementation:

[AppConfig.java](assignment_code/src/main/java/com/example/assignment_code/config/AppConfig.java)

```java
@Configuration
@ComponentScan("com.example.assignment_code")
public class AppConfig { }
```

This class is a configuration class, the `@Configuration` indicates as a source of bean definitions. The `@ComponentScan(basePackages = "com.example.assignment_code")` scan the specified package for Spring components such as `@Service`, `@Component`, `@Controller`, etc.

\
`AssignmentCodeApplication` Main class and method of the application implementation:

[AssignmentCodeApplication.java](assignment_code/src/main/java/com/example/assignment_code/AssignmentCodeApplication.java)

```java
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
```

This class initializes a Spring Boot application and demonstrates the `dependency injection` to retrieve and use beans of `EmployeeServiceConstructor`, `EmployeeServiceField`, and `EmployeeServiceSetter` that configured in the `AppConfig` class. Each service bean utilizes the `EmailService` to send an email notification to the specified recipient that is `john@example.com` for example, with a custom message. And in the end it closes the context to clean up resources.

\
`Output` from that application:

```
To: john@gmail,com
Subject: (Constructor) Work Notification
Body: Here is details of your work

To: john@gmail,com
Subject: (Field) Work Notification
Body: Here is details of your work

To: john@gmail,com
Subject: (Setter) Work Notification
Body: Here is details of your work
```

## 2.2 Q: Compare these 3 types. Which one is better? Which one is recommended?​

We will compare the 3 types of dependency injection of `EmployeeService`.  

### Constructor Injection

`Constructor injection` provides a dependency using a client class constructor, used when a class object is created.

- Dependencies clearly defined and required at object creation time, makes the class functionate properly. Once initialized, dependency usually immutable (`final`) and makes thread-safe.
    
- Simplifies unit testing because dependencies can be easily mocked in tests.

- Dependencies are resolved at compile time, reducing chances of runtime errors.

### Field Injection

`Field injection` provides dependent objects without a need to define or create them. While convenient, it has some drawbacks:

- Dependencies are not explicitly required for object instantiation, which can lead to runtime failures.

- Harder to mock dependencies in testing purposes because doesn't have control in the initialization.

- Can lead to tighter coupling classes, which make harder to refactor.

### Setter Injection

`Setter Injection` provides a dependency using the setter method.

- Setter injection provides flexibility, allows to change dependencies after object creation.

- The dependency can be less clear, potentially leading to issues if the setter methods are not called in the correct order.

### Which one is better? Which one is recommended?​

Based on that points, `constructor injection` is generally considered better and recommended over field and setter injection for the following reasons:

- It promotes `clearer` and more `robuset` code by explicitly declaring dependencies at the time of object creation by constructor.

- It leads to more `predictable` and maintainable.

- It helps in enforcing `immutability` of dependencies, which is a good in design principle.

While all three types of injection are supported by Spring, `constructor` injection is often considered the best practice and is recommended unless there are compelling reasons to choose one of the other methods.

## 2.3 Q: Research about “Circular dependency injection” ?

Circular dependency injection occurs when two or more `beans` have dependencies on each other directly or indirectly. The example, when a bean `A` dependes on another bean `B`, and the bean `B` depends on bean `A` as well. It's make bean `A` → bean `B` → bean `A`. The example of more beans bean `A` → bean `B` → bean `C` → bean `D` → bean `E` → bean `A`.

When the Spring context loads all the `beans`, it tries to create beans in order needed. When in the context of `circular dependency`, Spring cannot decide which of the beans should be created first, since they depend on one another. In these cases, Spring will raise a `BeanCurrentlyInCreationException` while loading context. It can happen in Spring when using `constructor injection`, but if using other types of injections, it shouldn't have this problem since the dependencies will be injected when they are needed and not on the context loading.

### Example

First, defining two beans that depend on one another with constructor dependency injection.

```java
@Component
public class CircularDependencyA {

    private CircularDependencyB circB;

    @Autowired
    public CircularDependencyA(CircularDependencyB circB) {
        this.circB = circB;
    }
}
```

```java
@Component
public class CircularDependencyB {

    private CircularDependencyA circA;

    @Autowired
    public CircularDependencyB(CircularDependencyA circA) {
        this.circA = circA;
    }
}
```

\
After that, create a `Configuration` class for the tests wich scanning for the components.

```java
@Configuration
@ComponentScan(basePackages = { "com.circulardependency" })
public class TestConfig {
}
```

\
Using JUnit test to check the circular dependency.

```java
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = { TestConfig.class })
public class CircularDependencyIntegrationTest {

    @Test
    public void givenCircularDependency_whenConstructorInjection_thenItFails() { }
}
```

\
Output, the exception will occur when this test is running.

```
BeanCurrentlyInCreationException: Error creating bean with name 'circularDependencyA':
Requested bean is currently in creation: Is there an unresolvable circular reference?
```

### Workarounds

The `circular` dependency can lead to issues during bean initialization if not handed properly. Also, Spring provides mehcanism to manage `circular` dependencies.

1. `Redesign`, try to redesign the components properly so that their hierarchy is well designed and no need for `circular` dependencies.
2. Use `@Lazy`, initialize on of the beans lazily, instead of fully initializing the bean. The injected bean will only be fully created when it's first needed.
3. Use `Setter`/`Field` injection, spring creates the beans, but the dependencies aren't injected until they are needed.
4. Use `@PostConstruct` annotation to set the other dependency.
5. Impelement `ApplicationContextAware`, and the bean has access to Spring context and can extract the other bean from there. Also implement `InitializingBean` to indicate that this bean has to do some actions after all its properties have been set.

## 2.4 Explain and give examples for some annotations in slide 19.​

Annotations in Spring Boot help to configure and manage `beans`, `dependencies`, and the `application context`.

### @Configuration

`@Configuration` indicates that the class can be used by the Spring inversion of control container as a source of beans.

```java
@Configuration
public class AppConfig { }
```

### @Bean

`@Bean` indicates that a method will produces a bean to can be managed by the Spring container.

```java
@Configuration
public class AppConfig {
    @Bean
    public UserService userService() {
        return new UserServiceImpl();
    }
}
```

### @ComponentScan

`@ComponentScan` configures component scanning directives so the Spring can take the component and use with `@Configuration` classes.

```java
@Configuration
@ComponentScan(basePackages = "com.example")
public class AppConfig { }
```

### @Component

`@Component` indicates that a class with this annotation be a `Component`. The class is considered for auto-detection using annotation-based configuration and classpath scanning.

```java
@Component
public class UserController { }
```

### @Service

`@Service` indicates that a class with this annotation be a `Service`, defined by domain driven design as an operation offered as an interface that stands alone in the model with no encapsulated data.

```java
@Service 
public class OrderService { }
```

### @Repository

`@Repository` indocates that a class with this annotation be a `Repository` that can be abstraction of data access and storage.

```java
@Repository
public class UserRepository { }
```

### @Autowired

`@Autowired` marks a constructor, field, setter, or config method as to be autowired to inject the depedencies by Spring.

```java
@Component
public class UserController {
    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }
}
```

### @Scope

`@Scope` configures the scope of the bean, like singleton, prototype, etc.

```java
@Component
@Scope("prototype")
public class TaskProcessor { }
```

### @Qualifier

`@Qualifier` is used when there are multiple beans of the same type, spescify the exact bean that should be wired with this annotation.

```java
@Component
public class UserController {
    private final UserService userService;

    @Autowired
    public UserController(@Qualifier("specificUserService") UserService userService) {
        this.userService = userService;
    }
}
```

### @PropertySource, @Value

`@PropertySource` adds a property source to Spring's environtment. `@Value` is used to inject values into fields from a property source.

```java
@Configuration
@PropertySource("classpath:application.properties")
public class AppConfig {

    @Value("${app.name}")
    private String appName;

    @Bean
    public ApplicationInfo applicationInfo() {
        return new ApplicationInfo(appName);
    }
}
```

### @PreDestroy, @PostConstruct

`@PreDestroy` annotation indicates a method to be invoked before the bean destroyed. `@PostConstruct` indicates a method to be invoked after the bean's initialization.

```java
@Component
public class DatabaseConnection {

    @PostConstruct
    public void init() {
        // To Do
    }

    @PreDestroy
    public void cleanup() {
        // To Do
    }
}
```

## Additional From PR Comment

**The comment/question**:
"Can we remove @autowire here? Pls explain"

```java
@Service
public class EmployeeServiceConstructor {
    private final EmailService emailService;

    @Autowired
    public EmployeeServiceConstructor(@Qualifier("primaryEmailService") EmailService emailService) {
        this.emailService = emailService;
    }

    public void notifyEmployee(String receiver, String workDetails) {
        emailService.sendEmail(receiver, "(Constructor) Work Notification", workDetails);
    }
}
```

**Answer**:
Yes, in this case, we can remove the `@Autowired` annotation from the construction. In Spring, when a class has a `single constructor`, the Spring will automatically use that constructor for dependency injection even if the `@Autowired` annotation is removed.

Spring automatically resolve and inject dependencies if there is only one constructor or `single constructor`. But, if there is multiple constructor, it need to give the annotation to one of them with `@Autowired` to tell Spring which one to use.