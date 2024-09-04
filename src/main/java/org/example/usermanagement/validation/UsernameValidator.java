package org.example.usermanagement.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class UsernameValidator implements ConstraintValidator<ValidUsername, String> {

    private static final String USERNAME_PATTERN = "^[a-zA-Z0-9]{4,20}$";

    @Override
    public void initialize(ValidUsername constraintAnnotation) {
        // Initialization logic if needed
    }

    @Override
    public boolean isValid(String username, ConstraintValidatorContext context) {
        if (username == null) {
            return false;
        }
        return username.matches(USERNAME_PATTERN);
    }
}
