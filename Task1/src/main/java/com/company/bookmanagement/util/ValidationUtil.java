// src/main/java/com/company/bookmanagement/util/ValidationUtil.java
package com.company.bookmanagement.util;

import com.company.bookmanagement.exception.ErrorCode;
import com.company.bookmanagement.exception.ValidationException;

import java.time.Year;
import java.util.HashMap;
import java.util.Map;

/**
 * Validation Utility Class
 *
 * Provides additional validation methods beyond Bean Validation.
 * Useful for complex business rule validation.
 *
 * USE CASES:
 * - Custom validation logic not possible with annotations
 * - Cross-field validation
 * - Business rule validation
 */
public final class ValidationUtil {

    // Private constructor to prevent instantiation
    private ValidationUtil() {
        throw new UnsupportedOperationException("This is a utility class and cannot be instantiated");
    }

    /**
     * Validates that a string is not null or blank
     *
     * @param value     The value to validate
     * @param fieldName The field name for error message
     * @throws ValidationException if validation fails
     */
    public static void validateNotBlank(String value, String fieldName) {
        if (value == null || value.isBlank()) {
            throw new ValidationException(
                    ErrorCode.VALIDATION_ERROR,
                    Map.of(fieldName, fieldName + " is required and cannot be blank")
            );
        }
    }

    /**
     * Validates string length
     *
     * @param value     The value to validate
     * @param fieldName The field name for error message
     * @param minLength Minimum allowed length
     * @param maxLength Maximum allowed length
     * @throws ValidationException if validation fails
     */
    public static void validateStringLength(String value, String fieldName, int minLength, int maxLength) {
        if (value != null) {
            int length = value.length();
            if (length < minLength || length > maxLength) {
                throw new ValidationException(
                        ErrorCode.VALIDATION_ERROR,
                        Map.of(fieldName, String.format("%s must be between %d and %d characters",
                                fieldName, minLength, maxLength))
                );
            }
        }
    }

    /**
     * Validates year is within allowed range
     *
     * @param year The year to validate
     * @throws ValidationException if validation fails
     */
    public static void validateYear(Integer year) {
        if (year != null) {
            int currentYear = Year.now().getValue();
            if (year < Constants.MIN_YEAR || year > currentYear) {
                throw new ValidationException(
                        ErrorCode.INVALID_YEAR,
                        Map.of("year", String.format("Year must be between %d and %d",
                                Constants.MIN_YEAR, currentYear))
                );
            }
        }
    }

    /**
     * Validates that an ID is positive
     *
     * @param id        The ID to validate
     * @param fieldName The field name for error message
     * @throws ValidationException if validation fails
     */
    public static void validatePositiveId(Long id, String fieldName) {
        if (id == null || id <= 0) {
            throw new ValidationException(
                    ErrorCode.INVALID_PATH_PARAMETER,
                    Map.of(fieldName, fieldName + " must be a positive number")
            );
        }
    }

    /**
     * Combines multiple validation errors
     *
     * @param errors Map of field errors
     * @throws ValidationException if there are any errors
     */
    public static void throwIfErrors(Map<String, String> errors) {
        if (!errors.isEmpty()) {
            throw new ValidationException(errors);
        }
    }
}