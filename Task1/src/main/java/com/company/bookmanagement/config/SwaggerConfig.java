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

@Configuration
public class SwaggerConfig {

    @Value("${spring.application.name:Book Management API}")
    private String applicationName;

    @Value("${server.port:9000}")
    private String serverPort;

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .servers(List.of(
                        new Server()
                                .url("http://localhost:" + serverPort)
                                .description("Development Server"),
                        new Server()
                                .url("https://api.company.com")
                                .description("Production Server")
                ))
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