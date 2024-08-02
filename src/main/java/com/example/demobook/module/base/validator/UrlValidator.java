package com.example.demobook.module.base.validator;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import com.example.demobook.module.base.Constants;

public class UrlValidator implements ConstraintValidator<UrlConstraint, String> {

    @Override
    public void initialize(UrlConstraint url) {}

    @Override
    public boolean isValid(String url, ConstraintValidatorContext cxt) {
        return url == null || url.matches(Constants.URL_REGEX);
    }
}
