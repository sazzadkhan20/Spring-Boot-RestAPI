// src/main/java/com/company/bookmanagement/validator/YearRangeValidator.java
package com.company.bookmanagement.validator;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.time.Year;

/**
 * Year Range Validator Implementation
 *
 * Implements the validation logic for @YearRange annotation.
 *
 * VALIDATION LOGIC:
 * 1. Null values are valid (use @NotNull for required years)
 * 2. Year must be >= min (default 1800)
 * 3. Year must be <= current year
 *
 * EXAMPLE:
 * - Input: 2020 -> Valid (between 1800 and current year)
 * - Input: 1799 -> Invalid (below minimum)
 * - Input: 2050 -> Invalid (above current year)
 * - Input: null -> Valid (optional field)
 */
public class YearRangeValidator implements ConstraintValidator<YearRange, Integer> {

    private int minYear;

    /**
     * Initializes the validator with annotation parameters
     *
     * @param constraintAnnotation The annotation instance
     */
    @Override
    public void initialize(YearRange constraintAnnotation) {
        this.minYear = constraintAnnotation.min();
    }

    /**
     * Validates the year value
     *
     * @param value   The year value to validate
     * @param context Validation context
     * @return true if valid, false otherwise
     */
    @Override
    public boolean isValid(Integer value, ConstraintValidatorContext context) {
        // Null is valid (use @NotNull separately if required)
        if (value == null) {
            return true;
        }

        int currentYear = Year.now().getValue();

        // Check if year is within valid range
        return value >= minYear && value <= currentYear;
    }
}