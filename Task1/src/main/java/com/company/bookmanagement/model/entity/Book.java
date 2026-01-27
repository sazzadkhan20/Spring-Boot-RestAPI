// src/main/java/com/company/bookmanagement/model/entity/Book.java
package com.company.bookmanagement.model.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * Book Entity
 *
 * Represents a book in the system. This is the domain model that is stored
 * in the repository (in-memory for this implementation).
 *
 * FIELDS:
 * - id: Auto-generated unique identifier
 * - title: Book title (required, 1-200 chars)
 * - author: Book author (required, 1-200 chars)
 * - year: Publication year (optional, 1800-current year)
 * - genre: Book genre (optional, max 100 chars)
 * - createdAt: When the record was created
 * - updatedAt: When the record was last updated
 *
 * NOTE: In a real application with JPA, this would have @Entity annotation
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Book {

    /**
     * Unique identifier for the book
     * Auto-generated when creating a new book
     */
    private Long id;

    /**
     * Title of the book
     * Required field, must be 1-200 characters
     */
    private String title;

    /**
     * Author of the book
     * Required field, must be 1-200 characters
     */
    private String author;

    /**
     * Publication year of the book
     * Optional field, must be between 1800 and current year if provided
     */
    private Integer year;

    /**
     * Genre/category of the book
     * Optional field, maximum 100 characters
     */
    private String genre;

    /**
     * Timestamp when this book record was created
     */
    private LocalDateTime createdAt;

    /**
     * Timestamp when this book record was last updated
     */
    private LocalDateTime updatedAt;
}