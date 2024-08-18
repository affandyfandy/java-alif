# Assignment 2 - Lecture 19

## Gateway Service With Filter.

[Gateway Full-Code](gateway/src/main/java/com/fsoft/gateway/)
[Authentication Service Full-Code](authentication_service/src/main/java/com/fsoft/authentication_service/)

The **Gateway** Service serves as the entry point for the microservice architecture, routing incoming requests to the appropriate services (Invoice or Product). This **Gateway** also includes a security filter to verify API keys before forwarding requests.

### API Key Entity and Repository

[ApiKey Entity](authentication_service/src/main/java/com/fsoft/authentication_service/model/ApiKey.java)

This `ApiKey` entity represents the API keys that stored in the MySQL database.

```java
@Entity
public class ApiKey {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String apiKey;

    // Getters and setters...
}
```

The `ApiKeyRepository` provides an interface for database operations related to API keys.

[ApiKeyRepository.java](authentication_service/src/main/java/com/fsoft/authentication_service/repository/ApiKeyRepository.java)

```java
public interface ApiKeyRepository extends JpaRepository<ApiKey, Long> {
    boolean existsByApiKey(String apiKey);
}
```

This repository interface extends `JpaRepository`, offering methods for interacting with the database.

### Authentication Service

[AuthenticationService.java](authentication_service/src/main/java/com/fsoft/authentication_service/service/AuthenticationService.java)

The `AuthenticationService` is responsible for validating API keys that stored in the database.

```java
@Service
public class AuthenticationService {

    private final ApiKeyRepository apiKeyRepository;

    public AuthenticationService(ApiKeyRepository apiKeyRepository) {
        this.apiKeyRepository = apiKeyRepository;
    }

    public boolean isValidApiKey(String apiKey) {
        return apiKeyRepository.existsByApiKey(apiKey) && !apiKey.isEmpty();
    }
}
```

### Authentication Controller

[AuthenticationController.java](authentication_service/src/main/java/com/fsoft/authentication_service/controller/AuthenticationController.java)

The AuthenticationController exposes an endpoint to validate the API key.

```java
@RestController
public class AuthenticationController {

    private final AuthenticationService authenticationService;

    @Autowired
    public AuthenticationController(AuthenticationService authenticationService) {
        this.authenticationService = authenticationService;
    }

    @GetMapping("/auth")
    public ResponseEntity<String> validateApiKey(@RequestHeader("API-KEY") String apiKey) {
        if (authenticationService.isValidApiKey(apiKey)) {
            return ResponseEntity.ok("Valid API key");
        } else {
            return ResponseEntity.status(401).body("Invalid API key");
        }
    }
}
```

This endpoint listens for requests on `/auth` and checks the `API-KEY` header using the `AuthenticationService`. If the key is valid, it returns a `200 OK` response, if invalid it responds with a `401 Unauthorized`.

### API Key Filter

[ApiKeyGatewayFilterFactory.java](gateway/src/main/java/com/fsoft/gateway/filter/ApiKeyGatewayFilterFactory.java)

The Gateway uses a custom filter from `AbstractGatewayFilterFactory` to validate API keys. Ensure that only request with a valid `API-KEY` header are processed.

```java
@Component
public class ApiKeyGatewayFilterFactory extends AbstractGatewayFilterFactory<ApiKeyGatewayFilterFactory.Config> {
    @Autowired
    private WebClient webClientBuilder;

    public ApiKeyGatewayFilterFactory() {
        super(Config.class);
    }

    @Override
    public GatewayFilter apply(Config config) {
        return (exchange, chain) -> {
            HttpHeaders headers = exchange.getRequest().getHeaders();
            String apiKey = headers.getFirst("API-KEY");

            if (apiKey == null) {
                exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
                return exchange.getResponse().setComplete();
            }

            return webClientBuilder
                    .get()
                    .uri("http://localhost:8083/auth")
                    .header("API-KEY", apiKey)
                    .retrieve()
                    .onStatus(
                            status -> status.is4xxClientError(),
                            response -> Mono.error(new RuntimeException("Invalid API key")))
                    .bodyToMono(String.class)
                    .flatMap(response -> chain.filter(exchange))
                    .onErrorResume(error -> {
                        exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
                        return exchange.getResponse().setComplete();
                    });
        };
    }

    public static class Config {}
}
```

This filter makes an external call to the `Authentication Service` to validate the API key. It checks for an `API-KEY` header in the request and, if present, calls the `Authentication Service` running on `http://localhost:8083/auth` to validate the API key. If the key is invalid, the filter returns an UNAUTHORIZED status code.

### Application.yml

[application.yml](gateway/src/main/resources/application.yml)

This is the configured `application.yml` for the Gateway Service.

```yml
server:
  port: 8080

spring:
  application:
    name: gateway-service
  cloud:
    gateway:
      routes:
        - id: invoice-service
          uri: http://localhost:8081
          predicates:
            - Path=/api/v1/invoices/**
        - id: product-service
          uri: http://localhost:8082
          predicates:
            - Path=/api/v1/products/**
      default-filters:
        - name: ApiKey

logging:
  level:
    org:
      springframework.cloud.gateway: DEBUG
    reactor.netty.http.client: DEBUG
```

**Server Port**: The Gateway Service operates on port 8080.

**Routing Configuration**:
- Requests to /api/v1/invoices/** are routed to the Invoice Service at http://localhost:8081.
- Requests to /api/v1/products/** are routed to the Product Service at http://localhost:8082.

**API Key Filter**: The ApiKey filter is applied to both routes. It validates the presence of a valid API key in the request headers before forwarding the request to the respective service.

**Database Configuration**: The API keys are stored in a MySQL database. The application.yml file includes database connection details and Hibernate settings for automatic schema updates and SQL logging.

## API Testing Screenshot

- UNAUTHORIZED Without API KEYS
    ![without](img/without-apikey.png)

- UNAUTHORIZED with invalid API KEYS
    ![invalid](img/invalid.png)

- With valid API KEYS
    ![valid](img/with-apikey.png)