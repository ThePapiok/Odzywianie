package com.Odzywianie.Validators;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class PatternPasswordValidator implements ConstraintValidator<PatternPassword, String> {
    @Override
    public void initialize(PatternPassword patternPassword) {
    }
    @Override
    public boolean isValid(String string, ConstraintValidatorContext context) {

        return string.matches(".*[a-z].*") && string.matches(".*[A-Z].*") && string.matches(".*\\d.*") && string.matches(".*[@$!%*?&].*");
    }
}
