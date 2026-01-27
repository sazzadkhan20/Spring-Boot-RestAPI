// src/main/java/com/company/bookmanagement/validator/YearRange.java
package com.company.bookmanagement.validator;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

/**
 * Custom Year Range Validation Annotation
 *
 * Validates that a year field is within the valid range (1800 to current year).
 *
 * USAGE:
 * @YearRange
 * private Integer year;
 *
 * @YearRange(message = "Custom error message")
 * private Integer year;
 *
 * HOW IT WORKS:
 * 1. Annotation is placed on a field
 * 2. @Constraint links to YearRangeValidator class
 * 3. When validation runs, YearRangeValidator.isValid() is called
 * 4. Returns true (valid) or false (invalid)
 */
@Documented
@Constraint(validatedBy = YearRangeValidator.class)
@Target({ElementType.FIELD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
public @interface YearRange {

    /**
     * Default error message
     */
    String message() default "Year must be between 1800 and current year";

    /**
     * Validation groups (required by JSR-380)
     */
    Class<?>[] groups() default {};

    /**
     * Payload for additional metadata (required by JSR-380)
     */
    Class<? extends Payload>[] payload() default {};

    /**
     * Minimum allowed year
     */
    int min() default 1800;
}