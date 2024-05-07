package com.Odzywianie.Validators;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = NotPopularPasswordValidator.class)
@Target({ ElementType.METHOD, ElementType.FIELD })
@Retention(RetentionPolicy.RUNTIME)
public @interface NotPopularPassword {
    String message() default "Hasło jest jednym z popularnych haseł, użyj innego";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
