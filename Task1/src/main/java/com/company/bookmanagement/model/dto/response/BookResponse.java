// src/main/java/com/company/bookmanagement/model/dto/response/BookResponse.java
package com.company.bookmanagement.model.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * Book Response DTO
 *
 * Data Transfer Object returned to clients when requesting book data.
 * This separates the internal entity structure from the API response.
 *
 * BENEFITS OF USING DTOs:
 * 1. Hides internal entity structure
 * 2. Can add computed fields
 * 3. Controls what data is exposed
 * 4. Allows entity changes without API changes
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Book response object")
public class BookResponse {

    @Schema(description = "Unique identifier of the book", example = "1")
    private Long id;

    @Schema(description = "Title of the book", example = "The Great Gatsby")
    private String title;

    @Schema(description = "Author of the book", example = "F. Scott Fitzgerald")
    private String author;

    @Schema(description = "Publication year", example = "1925")
    private Integer year;

    @Schema(description = "Genre of the book", example = "Fiction")
    private String genre;

    @Schema(description = "When the book record was created")
    private LocalDateTime createdAt;

    @Schema(description = "When the book record was last updated")
    private LocalDateTime updatedAt;
}