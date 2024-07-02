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

## 2.4 Q: Diference between BeanFactory and ApplicationContext ?​

| ​Feature | BeanFactory | ApplicationContext |
| --- | --- | --- |
| **Container Type** | Basic container | Advanced container |
| **Initialization** | Lazy initialized by default | Eager initialized by default |
| **Lightweight** | Yes | No (due to additional features) |
| **Customization** | Basic support for dependency injection and lifecycle management | Extensible with additional features and configuration options |
| **Core Interface** | Yes | Yes, extends BeanFactory |
| **Automatic Post-processing** | No | Yes |
| **Internationalization** | No | Yes, supports message resolution for localization |
| **Event Handling** | No | Yes, supports event propagation within the container |
| **Hierarchy Support** | No | Yes |
| **Common Implementations** | `XmlBeanFactory`, `DefaultListableBeanFactory` | `ClassPathXmlApplicationContext`, `AnnotationConfigApplicationContext` |
| **Suitable For** | Basic applications, resource-constrained environments | Most enterprise applications, larger systems |

### BeanFactory

BeanFactory is the most basic version of inversion of control containers.

1. Container type, BeanFactory provides basic mechanism for dependency injection and bean lifecycle management.
2. Lazy initialization, Beans are lazily initialized by default, means a bean created and its dependencies are injected only when requested via `getBean()` method.
3. Lightweight, BeanFactory is lightweight because it only initializes beans when needed.
4. Customization, BeanFactory provides extends and customization by impleemnting custom factories or extending existing implementations.
5. Core interface, it is the core interface for accessing and managing beans in Spring.
6. Commpon implementations including `XmlBeanFactory` and `DefaultListableBeanFactory`.
7. Suitable for basic application or environment with resource contstraints where lightweight and lazy init of beans are preferred.

### ApplicationContext

1. Container type, this extends the BeanFactory and provides more advanced container with additional enterprise features.
2. Eager initialization, beans are eagerly initialized by default, all singleton beans are created and their dependencies are injected at application startup unless marked as lazy.
3. Automatic post-processing, allowing more extensive configuration and customization of dependency injection.
4. Internationalization, provides messages based on locale-specific properties file or database resources.
5. Event handling, ApplicationContext supports event propagation within the container context, with communication between components through events and listeners.
6. Hieararchi support, where one ApplicationContext can be a parent of another.
7. Common implementations include `ClassPathXmlApplicationContext` and `AnnotationConfigApplicationContext`.
8. Suitable for most enterprise application and larger system where advanced features are required.