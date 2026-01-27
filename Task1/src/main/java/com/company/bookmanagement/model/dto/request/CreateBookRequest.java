package com.company.bookmanagement.model.dto.request;

import com.company.bookmanagement.validator.YearRange;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Request body for creating a new book")
public class CreateBookRequest {
    @NotBlank(message = "Title is required")
    @Size(min = 1, max = 200, message = "Title must be between 1 and 200 characters")
    @Schema(
            description = "Title of the book",
            example = "The Great Gatsby",
            requiredMode = Schema.RequiredMode.REQUIRED,
            minLength = 1,
            maxLength = 200
    )
    private String title;
    @NotBlank(message = "Author is required")
    @Size(min = 1, max = 200, message = "Author must be between 1 and 200 characters")
    @Schema(
            description = "Author of the book",
            example = "F. Scott Fitzgerald",
            requiredMode = Schema.RequiredMode.REQUIRED,
            minLength = 1,
            maxLength = 200
    )
    private String author;
    @YearRange(message = "Year must be between 1800 and current year")
    @Schema(
            description = "Publication year (optional, must be between 1800 and current year)",
            example = "1925",
            minimum = "1800"
    )
    private Integer year;
    @Size(max = 100, message = "Genre must not exceed 100 characters")
    @Schema(
            description = "Genre of the book (optional)",
            example = "Fiction",
            maxLength = 100
    )
    private String genre;
}