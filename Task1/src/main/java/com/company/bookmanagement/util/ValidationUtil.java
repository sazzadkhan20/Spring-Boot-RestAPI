package com.company.bookmanagement.util;

import com.company.bookmanagement.exception.ErrorCode;
import com.company.bookmanagement.exception.ValidationException;

import java.time.Year;
import java.util.HashMap;
import java.util.Map;

public final class ValidationUtil {
    private ValidationUtil() {
        throw new UnsupportedOperationException("This is a utility class and cannot be instantiated");
    }
    public static void validateNotBlank(String value, String fieldName) {
        if (value == null || value.isBlank()) {
            throw new ValidationException(
                    ErrorCode.VALIDATION_ERROR,
                    Map.of(fieldName, fieldName + " is required and cannot be blank")
            );
        }
    }
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
    public static void validatePositiveId(Long id, String fieldName) {
        if (id == null || id <= 0) {
            throw new ValidationException(
                    ErrorCode.INVALID_PATH_PARAMETER,
                    Map.of(fieldName, fieldName + " must be a positive number")
            );
        }
    }
    public static void throwIfErrors(Map<String, String> errors) {
        if (!errors.isEmpty()) {
            throw new ValidationException(errors);
        }
    }
}