# Lecture 5: Assignment 3​

A `REST API` or `RESTful API` stands for representational state transfer, and this is an application programming interface (`API` or web `API`) that conforms to the constraints of `REST` architectural style and allows for interaction with `RESTful` web services.

## Compare restful api best practice article with your Assignment 2 – lecture 5​

When developing a `REST API`, there is some best practices as a guidelines to ensure API is clear and efficient for developers to interact with. From the given article of "Best practices for REST API design", there is `eight` best practice for designing a REST API.

### Accept and respond with JSON

**Best Practice Key Points**

- Accept and respond with `JSON`.
    1. Use `JSON` for request payloads.
    2. Ensure endpoints return a `JSON` responses.
    3. Set `content-type` in the response header to `application/json`, use `Form Data` to send files.

**My Assignment 2 - Lecture 5**

1. Use `JSON` for request payloads.

    The `@RequestBody` annotations in in `post` and `put` method of the controller indicating that `JSON` is being accepted as the request payloads.

    ```java
    @PostMapping
    public ResponseEntity<Employee> saveEmployee(@RequestBody Employee employee)
    @PutMapping("/{id}")
    public ResponseEntity<Employee> updateEmployee(@PathVariable("id") String id, @RequestBody Employee employeeDetails)
    ```

2. Endpoints return a `JSON` responses.

    The `ResponEntity` with objects or lists indicating that Spring Boot automatically converts the objects or lists to `JSON`

    ```java
    public ResponseEntity<List<Employee>> listAllEmployees()
    public ResponseEntity<Employee> findEmployee(@PathVariable("id") String id)
    public ResponseEntity<Employee> saveEmployee(@RequestBody Employee employee)
    public ResponseEntity<Employee> updateEmployee(@PathVariable("id") String id, @RequestBody Employee employeeDetails)
    public ResponseEntity<String> deleteEmployee(@PathVariable("id") String id)
    public ResponseEntity<String> importEmployeesFromCsv(@RequestParam("file") MultipartFile file)
    public ResponseEntity<List<Employee>> getEmployeesByDepartment(@PathVariable("department") String department)
    ```
3. `content-type` of response header.

    Spring boot by default includes `content-type: application/json` in the response headers for these endpoints. But in my application, there is a method that handles files upload using `MultipartFile`, this handling using `Form Data` to upload files.

    ```java
    public ResponseEntity<String> importEmployeesFromCsv(@RequestParam("file") MultipartFile file)
    ```

**Summary**

The code has followed this best practice by accepting `JSON` payloads for requests and responses, `content-type` header of `JSON` by Spring Boot, also handle file with `Form Data`.

### Use nouns instead of verbs in endpoint paths

**Best Practice Key Points**

- Use `nouns` for endpoint paths
    1. Path names should represent resources using `nouns`.
    2. Use `HTTP` methods to indicate actions.

**My Assignment 2 - Lecture 5**

```java
@RestController
@RequestMapping("/api/v1/employees")
public class EmployeeController {

    @GetMapping
    public ResponseEntity<List<Employee>> listAllEmployees() { ... }

    @GetMapping("/{id}")
    public ResponseEntity<Employee> findEmployee(@PathVariable("id") String id) { ... }

    @PostMapping
    public ResponseEntity<Employee> saveEmployee(@RequestBody Employee employee) { ... }

    @PutMapping("/{id}")
    public ResponseEntity<Employee> updateEmployee(@PathVariable("id") String id, @RequestBody Employee employeeDetails) { ... }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteEmployee(@PathVariable("id") String id) { ... }

    @PostMapping("/import")
    public ResponseEntity<String> importEmployeesFromCsv(@RequestParam("file") MultipartFile file) { ... }

    @GetMapping("/department/{department}")
    public ResponseEntity<List<Employee>> getEmployeesByDepartment(@PathVariable("department") String department) { ... }
}
```

1. Path names should represent resources using `nouns`.

    My endpoint paths already using `nouns` to represent the resource. The `@RequestMapping("/api/v1/employees")` annotations indicating that the path names is represent `Employee` resources using `nouns`. The `GET /api/v1/employees/department/{department}` retrieves employees by department using "department" as a noun. But, the `POST /api/v1/employees/import` using `verb`.

2. Use `HTTP` methods to indicate actions.

    The `CRUD` operations has used the `HTTP` methods to indicate actions.

    - GET /api/v1/employees - Retrieves all employees.
    - GET /api/v1/employees/{id} - Retrieves a specific employee by ID.
    - POST /api/v1/employees - Creates a new employee.
    - PUT /api/v1/employees/{id} - Updates an existing employee.
    - DELETE /api/v1/employees/{id} - Deletes an employee by ID.

**Summary**

The endpoint paths has used `nouns` (except the import method that use verb) and `HTTP` methods to indicate actions.

### Use logical nesting on endpoints

**Best Practice Key Points**

- Logical nesting on endpoints.
    1. Associate information in a nested group.
    2. Parent-child relationship in the endpoint paths.
    3. Limit to two or three levels of nesting.

**My Assignment 2 - Lecture 5**

my code doesn't implement logical nesting for endpoints yet. Logical nesting helps in organizing structuring API endpoints relationship between resources. Also this is my endpoints that doesn't implement that yet:

- /api/v1/employees - Retrieves all employees.
- /api/v1/employees/{id} - Retrieves a specific employee by ID.
- /api/v1/employees/import - Imports employees from a CSV file.
- /api/v1/employees/department/{department} - Retrieves employees by department.

### Handle errors gracefully and return standard error codes

**Best Practice Key Points**

- Handle the errors and return standard error codes
    1. Standard `HTTP` status code to indicate the type of error.
    2. Clear error `message` to maintains code.
    3. Error `message` doesn't expose sensitive information.

**My Assignment 2 - Lecture 5**

1. Standard `HTTP` status code to indicate the type of error.
    My code was already defines standard `HTTP` status code, it can be the built-in spring boot method like `ResponseEntity.notFound().build();`. This is my current error that handled by `HTTP` status code:

    - `404 Not Found`: Used in `findEmployee` and `updateEmployee` methods when an employee is not found.
    - `400 Bad Request`: Used in `saveEmployee` method when trying to save an existing employee.
    - `500 Internal Server Error`: Used in `importEmployeesFromCsv` for generic errors.

2. Clear error `message` to maintains code.
    Some of my method doesn't implementing the error `message`, but my `importEmployeesFromCsv()` method has an error `message` that is "Error importing file: " + e.getMessage()".

3. Error `message` doesn't expose sensitive information.
    The error `message` in the `importEmployeeFromCSV()` method doesn't expose the sensitive information.

**Summary**

The current code handles errors well, but still need more improvements like:
- Use a common error response structure.
- Differentiate between types of errors.
- Ensure consistent error messages and codes.

### Allow filtering, sorting, and pagination

**Best Practice Key Points**

1. Allow filtering, sorting, and pagination:
    - `Filtering`: Enable filtering based on specific criteria, typically provided through query parameters.
    - `Sorting`: Allow sorting of results based on specified fields and directions.
    - `Pagination`: Limit the number of results returned per request, reducing resource consumption.

**My Assignment 2 - Lecture 5**

1. Filtering, sorting, and pagination.

    My code doesn't implementing sorting and pagination, but there is filtering by `department` to filter the `Employee` by the `department` using JPARepository that is `List<Employee> findByDepartment(String department)`.

### Maintain good security practices

**Best Practice Key Points**

- Maintaining good security on communication.
    1. Use `SSL`/`TLS` encryption to ensure all communication is secure.
    2. `Role-Based` Access Control to restrict accsess based on iser roles and permissions.
    3. Principle of least privilege to minimize access to sensitive information and features.
    4. Use `preset roles` to manage permissions for groups of users.

**My Assignment 2 - Lecture 5**

My code doesn't handle the maintaining good security practices. There is no explicitly handled `SSL`/`TLS` configuration. There is no implementation of `role-based` access control. Also, the code doesn't implement `roles` or `permissions` to restrict access.

### Cache data to improve performance

**Best Practice Key Points**

- Improving the performance by cache data.
    1. Implement `caching` mechanisms to store frequently accessed data in memory or in dedicated cache stores.
    2. Use `caching` to reduce database load and improve response times.
    3. `Expiration` times for cached data to balance between performance and data freshness.
    4. Include `Cache-Control` headers in responses.
 
**My Assignment 2 - Lecture 5**

In my code, there's no implementation of `caching` mechanisms for improving performance, also the `cache-control` headers are not utilized in the responses.

### Versioning our APIs

**Best Practice Key Points**

- Versioning the APIs:
    1. Implement `API` versioning to manage changes and updates without breaking existing client implementations.
    2. Use `semantic` versioning (e.g., `/v1/`, `/v2/`) in API paths to indicate different versions.
    3. Maintain backward compatibility for older API versions to support clients that have not yet migrated to newer versions.
    4. Clearly document API versions and changes to facilitate communication with API consumers.

**My Assignment 2 - Lecture 5**

```java
@RequestMapping("/api/v1/employees")
```

This path indicates that the application has API versioning, the API version is `v1`. By starting with `/v1/` it can be maintains for the future version without breaking current API.
