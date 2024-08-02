package com.example.demobook.module.base.validator;

import java.lang.annotation.*;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

@Documented
@Constraint(validatedBy = UrlValidator.class)
@Target({ElementType.METHOD, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface UrlConstraint {
    String message() default "Invalid url";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
