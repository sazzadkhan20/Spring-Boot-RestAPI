package com.company.bookmanagement.model.dto.request;

import com.company.bookmanagement.validator.YearRange;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Request body for updating an existing book")
public class UpdateBookRequest {
    @Size(min = 1, max = 200, message = "Title must be between 1 and 200 characters")
    @Schema(
            description = "New title of the book (optional)",
            example = "The Great Gatsby - Revised Edition",
            minLength = 1,
            maxLength = 200
    )
    private String title;
    @Size(min = 1, max = 200, message = "Author must be between 1 and 200 characters")
    @Schema(
            description = "New author of the book (optional)",
            example = "Francis Scott Fitzgerald",
            minLength = 1,
            maxLength = 200
    )
    private String author;
    @YearRange(message = "Year must be between 1800 and current year")
    @Schema(
            description = "New publication year (optional)",
            example = "1926",
            minimum = "1800"
    )
    private Integer year;
    @Size(max = 100, message = "Genre must not exceed 100 characters")
    @Schema(
            description = "New genre of the book (optional)",
            example = "Classic Fiction",
            maxLength = 100
    )
    private String genre;
}