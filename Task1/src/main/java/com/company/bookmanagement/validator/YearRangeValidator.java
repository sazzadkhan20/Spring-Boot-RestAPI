package com.company.bookmanagement.validator;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.time.Year;

public class YearRangeValidator implements ConstraintValidator<YearRange, Integer> {

    private int minYear;
    @Override
    public void initialize(YearRange constraintAnnotation) {
        this.minYear = constraintAnnotation.min();
    }
    @Override
    public boolean isValid(Integer value, ConstraintValidatorContext context) {
        if (value == null) {
            return true;
        }

        int currentYear = Year.now().getValue();
        return value >= minYear && value <= currentYear;
    }
}