package com.Odzywianie.validators;

import com.Odzywianie.Validators.NotPopularPasswordValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

public class NotPopularPasswordValidatorTest {
    private NotPopularPasswordValidator notPopularPasswordValidator;
    @Mock
    private ConstraintValidatorContext context;

    @BeforeEach
    public void setUp(){
        MockitoAnnotations.openMocks(this);
        notPopularPasswordValidator = new NotPopularPasswordValidator();
    }

    @Test
    public void shouldFailWhenPopularPassword1(){
        Assertions.assertFalse(notPopularPasswordValidator.isValid("password", context));
    }

    @Test
    public void shouldFailWhenPopularPassword2(){
        Assertions.assertFalse(notPopularPasswordValidator.isValid("12345678", context));
    }

    @Test
    public void shouldFailWhenPopularPassword3(){
        Assertions.assertFalse(notPopularPasswordValidator.isValid("zaq12wsx", context));
    }

    @Test
    public void shouldFailWhenPopularPassword4(){
        Assertions.assertFalse(notPopularPasswordValidator.isValid("hasło123", context));
    }

    @Test
    public void shouldFailWhenPopularPassword5(){
        Assertions.assertFalse(notPopularPasswordValidator.isValid("123456789", context));
    }

    @Test
    public void shouldFailWhenPopularPassword6(){
        Assertions.assertFalse(notPopularPasswordValidator.isValid("password1", context));
    }

    @Test
    public void shouldFailWhenPopularPassword7(){
        Assertions.assertFalse(notPopularPasswordValidator.isValid("iloveyou", context));
    }

    @Test
    public void shouldFailWhenPopularPassword8(){
        Assertions.assertFalse(notPopularPasswordValidator.isValid("1q2w3e4r", context));
    }

    @Test
    public void shouldFailWhenPopularPassword9(){
        Assertions.assertFalse(notPopularPasswordValidator.isValid("qwerty123", context));
    }

    @Test
    public void shouldFailWhenPopularPassword10(){
        Assertions.assertFalse(notPopularPasswordValidator.isValid("1qaz2wsx", context));
    }

    @Test
    public void shouldFailWhenPopularPassword11(){
        Assertions.assertFalse(notPopularPasswordValidator.isValid("qwertyuiop", context));
    }

    @Test
    public void shouldFailWhenPopularPassword12(){
        Assertions.assertFalse(notPopularPasswordValidator.isValid("asdfghjkl", context));
    }

    @Test
    public void shouldSuccess(){
        Assertions.assertTrue(notPopularPasswordValidator.isValid("Test123!", context));
    }


}
