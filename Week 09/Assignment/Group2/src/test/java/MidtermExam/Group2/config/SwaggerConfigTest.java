package MidtermExam.Group2.config;

import io.swagger.v3.oas.models.OpenAPI;
import org.junit.jupiter.api.Test;
import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class SwaggerConfigTest {
    @Autowired
    private ApplicationContext applicationContext;

    @Test
    void testGroupedOpenApiBeanExists() {
        // Check if the GroupedOpenApi bean is loaded in the context
        assertNotNull(applicationContext.getBean(GroupedOpenApi.class));

        GroupedOpenApi groupedOpenApi = applicationContext.getBean(GroupedOpenApi.class);
        assertEquals("public-api", groupedOpenApi.getGroup());
        assertTrue(groupedOpenApi.getPathsToMatch().contains("/api/v1/**"));
    }

    @Test
    void testOpenAPIBeanExists() {
        // Check if the OpenAPI bean is loaded in the context
        assertNotNull(applicationContext.getBean(OpenAPI.class));

        OpenAPI openAPI = applicationContext.getBean(OpenAPI.class);
        assertEquals("Point Of Sale System API Documentation", openAPI.getInfo().getTitle());
        assertEquals("v1", openAPI.getInfo().getVersion());

        // Check if security scheme is correctly configured
        assertNotNull(openAPI.getComponents().getSecuritySchemes().get("bearerAuth"));
        assertEquals("bearer", openAPI.getComponents().getSecuritySchemes().get("bearerAuth").getScheme());
        assertEquals("JWT", openAPI.getComponents().getSecuritySchemes().get("bearerAuth").getBearerFormat());
    }
}
