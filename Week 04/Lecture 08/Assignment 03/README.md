# Assignment 3 - Lecture 8

## 3.1 Change assignment 2 to create datasoure using bean, not application properties (research multiple datasource)​

[DataSourceConfig.java](employee_manager\src\main\java\com\example\employee_manager\config\DataSourceConfig.java)

Using `DataSourceBuilder` to create datasource bean objects.

```java
@Configuration
public class DataSourceConfig {

    @Bean
    public DataSource getDataSource() {
        DataSourceBuilder<?> dataSourceBuilder = DataSourceBuilder.create();
        dataSourceBuilder.driverClassName("com.mysql.cj.jdbc.Driver");
        dataSourceBuilder.url("jdbc:mysql://localhost:3306/fsoft-lecture");
        dataSourceBuilder.username("root");
        dataSourceBuilder.password("");
        return dataSourceBuilder.build();
    }
}
```

The `DataSourceBuilder<?> dataSourceBuilder = DataSourceBuilder.create()` will creates an instance of `DataSourceBuilder` that simplifies the creation of `DataSource` objects. The `dataSourceBuilder` variable will configure the data source with the necessary parameters for the application. Lastly, it will builds the `DataSource` instance to return it, and it can be injected into other component where the datasource is needed.

## 3.2 Handle transaction when insert/update data​

[EmployeeDAO.java](employee_manager/src/main/java/com/example/employee_manager/dao/EmployeeDAO.java)

Using `@Transactional` annotation to handle the transaction when performing insert or update operations. I will add that annotation in `EmployeeDAO` that used for insert/update data operation in database.

**Transactional**
```java
public class EmployeeDAO {

    // ... other code

    // Create the Employee
    @Transactional
    public int save(Employee employee) {
        return jdbcTemplate.update(INSERT_QUERY, employee.getId(), employee.getName(), employee.getAddress(), employee.getDepartment());
    }

    // Update the Employee
    @Transactional
    public int update(Employee employee) {
        return jdbcTemplate.update(UPDATE_QUERY, employee.getName(), employee.getAddress(), employee.getDepartment(), employee.getId());
    }

    // Delete the Employee
    @Transactional
    public int delete(String id) {
        return jdbcTemplate.update(DELETE_QUERY, id);
    }

    // ... other code
}
```

Ensure the operations are executed within a transaction by adding the `@Transactional` annotation to the `save()`, `update()`, and `delete()` methods. If any exception occurs during the transaction it'll rollback automatically, also ensures data integrity.

**Enable Transaction Management**

[Main_Application_Class](employee_manager/src/main/java/com/example/employee_manager/EmployeeManagerApplication.java)

Explicitly enable the transaction management if using `spring-boot-starter-jdbc` dependency, by add the `@EnableTransactionManagement` annotation at the main application class or configuration class.

```java
@SpringBootApplication
@EnableTransactionManagement
public class EmployeeManagerApplication {
	public static void main(String[] args) {
		SpringApplication.run(EmployeeManagerApplication.class, args);
	}
}
```

The `@EnableTransactionManagement` annotation enables Spring's annotation-driven transaction management, that allows the use of `@Transactional` on methods or classes to manage transactions of data. 

## 3.3 Research Lombok and add to project​

[Employee.java](employee_manager/src/main/java/com/example/employee_manager/model/Employee.java)

Firstly, add the `Lombok` dependency to the `pom.xml` file.

```xml
<dependency>
    <groupId>org.projectlombok</groupId>
    <artifactId>lombok</artifactId>
    <version>1.18.24</version>
    <scope>provided</scope>
</dependency>
```

Then, import the `Lombok` to the class that needed, and the `Lombok` can reduce boilerplate code. In this case, I'll use `Lombok` in the `Employee` class.

```java
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Employee {
    private String id;
    private String name;
    private String address;
    private String department;
}
```

The `@Data` annotation generates getters, setters, toString, equals, and hashCode methods. The `@NoArgsConstructor` and `AllArgsConstructor` annotations will generate a no argument constructor and all field argument constructor.