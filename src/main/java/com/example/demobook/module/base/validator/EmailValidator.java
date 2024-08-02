package com.example.demobook.module.base.validator;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import com.example.demobook.module.base.Constants;

public class EmailValidator implements ConstraintValidator<EmailConstraint, String> {

    @Override
    public void initialize(EmailConstraint email) {}

    @Override
    public boolean isValid(String email, ConstraintValidatorContext cxt) {
        return email == null || email.matches(Constants.EMAIL_REGEX);
    }
}
