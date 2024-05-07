package com.Odzywianie.Validators;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = PatternPasswordValidator.class)
@Target({ ElementType.METHOD, ElementType.FIELD })
@Retention(RetentionPolicy.RUNTIME)
public @interface PatternPassword {
    String message() default "Hasło powinno zawierać przynajmniej jedną małą literę, jedną dużą literę, jedną cyfrę i jeden znak specjalny";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
