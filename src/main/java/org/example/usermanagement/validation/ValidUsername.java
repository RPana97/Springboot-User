package org.example.usermanagement.validation;

import java.lang.annotation.*;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

@Documented
@Constraint(validatedBy = UsernameValidator.class)
@Target({ ElementType.FIELD })
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidUsername {
    String message() default "Invalid username. Username must be alphanumeric and between 4 to 20 characters.";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
