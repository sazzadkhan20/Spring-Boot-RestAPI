// src/main/java/com/company/bookmanagement/config/WebConfig.java
package com.company.bookmanagement.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * Web MVC Configuration
 *
 * Configures web-related settings including CORS (Cross-Origin Resource Sharing).
 * This is important for allowing frontend applications to call our API.
 *
 * WORKFLOW:
 * 1. Implements WebMvcConfigurer to customize Spring MVC configuration
 * 2. Configures CORS mappings for all endpoints
 * 3. Spring applies these settings to incoming HTTP requests
 */
@Configuration
public class WebConfig implements WebMvcConfigurer {

    /**
     * Configures CORS settings for the API
     *
     * CORS is necessary when:
     * - Frontend and backend are on different domains
     * - Different ports (e.g., React on 3000, API on 8080)
     *
     * @param registry CorsRegistry to add mappings to
     */
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/api/**")  // Apply to all API endpoints
                .allowedOrigins("*")     // Allow all origins (restrict in production)
                .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS")
                .allowedHeaders("*")
                .maxAge(3600);           // Cache preflight response for 1 hour
    }
}