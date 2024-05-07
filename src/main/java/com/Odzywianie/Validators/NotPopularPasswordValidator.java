package com.Odzywianie.Validators;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.util.Arrays;
import java.util.List;

public class NotPopularPasswordValidator implements ConstraintValidator<NotPopularPassword, String> {
    private static final List<String> popularPasswords = Arrays.asList("password", "12345678", "zaq12wsx", "hasło123","123456789","password1","iloveyou","1q2w3e4r","qwerty123","1qaz2wsx","qwertyuiop","asdfghjkl");

    @Override
    public void initialize(NotPopularPassword notPopularPassword) {
    }
    @Override
    public boolean isValid(String string, ConstraintValidatorContext context) {
        return !popularPasswords.contains(string);
    }
}
