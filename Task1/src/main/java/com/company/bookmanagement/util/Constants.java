// src/main/java/com/company/bookmanagement/util/Constants.java
package com.company.bookmanagement.util;

/**
 * Application Constants
 *
 * Central location for all constant values used across the application.
 * This prevents magic numbers and strings throughout the codebase.
 *
 * BENEFITS:
 * 1. Single source of truth for constants
 * 2. Easy to modify values
 * 3. Better code readability
 * 4. Prevents typos and inconsistencies
 */
public final class Constants {

    // Private constructor to prevent instantiation
    private Constants() {
        throw new UnsupportedOperationException("This is a utility class and cannot be instantiated");
    }

    // ==================== VALIDATION CONSTANTS ====================

    /**
     * Minimum allowed year for book publication
     */
    public static final int MIN_YEAR = 1800;

    /**
     * Maximum length for book title
     */
    public static final int MAX_TITLE_LENGTH = 200;

    /**
     * Minimum length for book title
     */
    public static final int MIN_TITLE_LENGTH = 1;

    /**
     * Maximum length for author name
     */
    public static final int MAX_AUTHOR_LENGTH = 200;

    /**
     * Minimum length for author name
     */
    public static final int MIN_AUTHOR_LENGTH = 1;

    /**
     * Maximum length for genre
     */
    public static final int MAX_GENRE_LENGTH = 100;

    // ==================== API PATHS ====================

    /**
     * Base path for all API endpoints
     */
    public static final String API_BASE_PATH = "/api";

    /**
     * Path for book endpoints
     */
    public static final String BOOKS_PATH = "/books";

    // ==================== MESSAGES ====================

    /**
     * Success message for book creation
     */
    public static final String BOOK_CREATED_SUCCESS = "Book created successfully";

    /**
     * Success message for book update
     */
    public static final String BOOK_UPDATED_SUCCESS = "Book updated successfully";

    /**
     * Success message for book deletion
     */
    public static final String BOOK_DELETED_SUCCESS = "Book deleted successfully";

    /**
     * Success message for fetching books
     */
    public static final String BOOKS_FETCHED_SUCCESS = "Books retrieved successfully";
}