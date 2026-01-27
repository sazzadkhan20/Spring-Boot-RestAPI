// src/main/java/com/company/bookmanagement/config/SwaggerConfig.java
package com.company.bookmanagement.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

/**
 * Swagger/OpenAPI Configuration
 *
 * This configuration class sets up the OpenAPI documentation for our REST API.
 * It provides detailed API documentation accessible at /swagger-ui.html
 *
 * WORKFLOW:
 * 1. Spring Boot starts and scans for @Configuration classes
 * 2. This class creates an OpenAPI bean with API metadata
 * 3. SpringDoc automatically generates API documentation
 * 4. Documentation is accessible at http://localhost:8080/swagger-ui.html
 */
@Configuration
public class SwaggerConfig {

    @Value("${spring.application.name:Book Management API}")
    private String applicationName;

    @Value("${server.port:8080}")
    private String serverPort;

    /**
     * Creates and configures the OpenAPI documentation bean
     *
     * @return OpenAPI object with complete API documentation setup
     */
    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                // Server configuration - where the API is hosted
                .servers(List.of(
                        new Server()
                                .url("http://localhost:" + serverPort)
                                .description("Development Server"),
                        new Server()
                                .url("https://api.company.com")
                                .description("Production Server")
                ))
                // API Information
                .info(new Info()
                        .title(applicationName)
                        .version("1.0.0")
                        .description("""
                                ## Book Management REST API
                                
                                This API provides CRUD operations for managing books.
                                
                                ### Features:
                                - Create, Read, Update, Delete books
                                - Input validation with detailed error messages
                                - In-memory storage (stateless)
                                - RESTful design principles
                                
                                ### Error Handling:
                                - 400: Bad Request (validation errors)
                                - 404: Resource Not Found
                                - 500: Internal Server Error
                                """)
                        .contact(new Contact()
                                .name("API Support")
                                .email("support@company.com")
                                .url("https://company.com/support"))
                        .license(new License()
                                .name("Apache 2.0")
                                .url("https://www.apache.org/licenses/LICENSE-2.0")));
    }
}