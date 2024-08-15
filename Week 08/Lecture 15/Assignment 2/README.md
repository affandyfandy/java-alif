# Assignment 2 - Lecture 15

## Simple Filter

**Filters** are part of the web server and not the Spring framework. Used to intercept incoming requests, allowing manipulation or blocking requests before reach any servlet. `Filters` can also manipulate or block the responses before they are sent to the client.

**Project Structure**

```bash
com/fsoft/lecture15/assignment2/
├── Assignment2Application.java
├── config/
│   └── WebConfig.java
├── controller/
│   └── EmployeeController.java
├── entity/
│   ├── ApiKey.java
│   └── Employee.java
├── filter/
│   └── RequestResponseApiKeyFilter.java
└── repository/
    └── ApiKeyRepository.java
    └── EmployeeRepository.java
```

### Simple Employee CRUD

In this project, I've implemented a simple CRUD (Create, Read, Update, Delete) functionality for managing `Employee` data. This includes:

- [Employee.java](assignment2/src/main/java/com/fsoft/lecture15/assignment2/entity/Employee.java) that represents the data model.
- [EmployeeRepository.java](assignment2/src/main/java/com/fsoft/lecture15/assignment2/repository/EmployeeRepository.java) that handles database operations using Spring Data JPA.
- [EmployeeController.java](assignment2/src/main/java/com/fsoft/lecture15/assignment2/controller/EmployeeController.java) that provides RESTful API endpoints to interact with the `Employee` data.

This setup allows for easy data management and interaction using HTTP methods.

### Filter

In this project, I've implemented a filter that checks for a presence of a valid API key in the request headers, ensuring that only authorized requests are processed. This includes:

- [ApiKeyRepository.java](assignment2/src/main/java/com/fsoft/lecture15/assignment2/repository/ApiKeyRepository.java)
- [RequestResponseApiKeyFilter.java](assignment2/src/main/java/com/fsoft/lecture15/assignment2/filter/RequestResponseApiKeyFilter.java)
- [WebConfig.java](assignment2/src/main/java/com/fsoft/lecture15/assignment2/config/WebConfig.java)

```java
public interface ApiKeyRepository extends JpaRepository<ApiKey, Long> {
    boolean existsByKeyValue(String keyValue);
}
```

The `ApiKeyRepository` is a Spring Data JPA repository interface for accessing the API key data stored in the database. The `existsByKeyValue` method is particularly to verifies is an API key exists by cheking its value.

```java
@Component
@Order(1)
public class RequestResponseApiKeyFilter implements Filter {

    private final ApiKeyRepository apiKeyRepository;

    public RequestResponseApiKeyFilter(ApiKeyRepository apiKeyRepository) {
        this.apiKeyRepository = apiKeyRepository;
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest) servletRequest;
        HttpServletResponse httpResponse = (HttpServletResponse) servletResponse;

        Logger logger = org.slf4j.LoggerFactory.getLogger(RequestResponseApiKeyFilter.class);
        logger.info("Logging Request  {} : {}", httpRequest.getMethod(), httpRequest.getRequestURI());

        String apiKey = httpRequest.getHeader("API-KEY");

        if (apiKey == null || !apiKeyRepository.existsByKeyValue(apiKey)) {
            httpResponse.sendError(HttpServletResponse.SC_UNAUTHORIZED, "API key is missing or invalid");
            return;
        }

        httpResponse.setHeader("Source", "FPT Software");

        filterChain.doFilter(servletRequest, servletResponse);

        logger.info("Logging Response :{}", httpResponse.getStatus());

    }
}
```

The `RequestResponseApiKeyFilter` class is a filter to intercept HTTP requests and responses. It uses the `ApiKeyRepository` to validate the API key in the request header. If the key is missing or invalid, the filter sends an unauthorized error response. The filter sets a custom header, `"Source"`, in the response with the value of `"FPT Software"` This filter is annotated with `@Component `and `@Order(1)`, ensuring it is one of the first filters executed in the filter chain.

```java
String apiKey = httpRequest.getHeader("API-KEY");

if (apiKey == null || !apiKeyRepository.existsByKeyValue(apiKey)) {
    httpResponse.sendError(HttpServletResponse.SC_UNAUTHORIZED, "API key is missing or invalid");
    return;
}
```

With this code in the `RequestResponseApiKeyFilter` class, it extracts the `"API-KEY"` from the HTTP request headers. Checks if the key is null or doesn't exist in the database using the `apiKeyRepository.existsByKeyValue(apiKey)` method. If the API key is not valid, returns a 401 Unauthorized response, preventing access to the protected resources.

```java
httpResponse.setHeader("Source", "FPT Software");
```

Also, this code sets a custom header, `"Source"` in the HTTP response with the value `"FPT Software"`.

```java
@Configuration
public class WebConfig {
    @Bean
    public RequestResponseApiKeyFilter apiKeyFilter(ApiKeyRepository apiKeyRepository) {
        return new RequestResponseApiKeyFilter(apiKeyRepository);
    }

    @Bean
    public FilterRegistrationBean<RequestResponseApiKeyFilter> apiKeyFilterRegistration(RequestResponseApiKeyFilter apiKeyFilter) {
        FilterRegistrationBean<RequestResponseApiKeyFilter> registrationBean = new FilterRegistrationBean<>();
        registrationBean.setFilter(apiKeyFilter);
        registrationBean.addUrlPatterns("/api/v1/employees/*");
        return registrationBean;
    }
}
```

The `WebConfig` class is a configuration class that defines beans and their initialization. Registers the `RequestResponseApiKeyFilter` bean, ensuring the filter is available in the Spring context. The `FilterRegistrationBean` bean specifies the URL patterns that the filter should apply to all paths under `/api/v1/employees/*`.

## Result

After implements the application, test the system to verify that the API key validation and response header are functioning as expected. To do this, it can be use a `cURL` command to send a GET request to the Employee API endpoint, including an `"Api-Key"` header with a sample value.

```log
curl -vH "Api-Key: abc123xyz" localhost:8080/api/v1/employees
```

This `cURL` command initiates a GET request to the `/api/v1/employees` endpoint. The `-v` flag is verbose mode for detailed information about the request and response, while the `-H` flag adds a custom header, `"Api-Key: abc123xyz"` to the request.

```log
* Host localhost:8080 was resolved.
* IPv6: ::1
* IPv4: 127.0.0.1
*   Trying [::1]:8080...
* Connected to localhost (::1) port 8080
> GET /api/v1/employees HTTP/1.1
> Host: localhost:8080
> User-Agent: curl/8.7.1
> Accept: */*
> Api-Key: abc123xyz
>
* Request completely sent off
< HTTP/1.1 200
< Source: FPT Software
< Content-Type: application/json
< Transfer-Encoding: chunked
< Date: Mon, 29 Jul 2024 12:50:11 GMT
<
[{"id":1,"name":"Alice Johnson"},{"id":2,"name":"Bob Smith"},{"id":3,"name":"John"},{"id":4,"name":"Michael"}]* Connection #0 to host localhost left intact
```

In this verbose output, it includes the headers with the `Api-Key` header. The server responds with `200` status code of successful request. The response header include the custom `"Source: FPT Software"` header.

