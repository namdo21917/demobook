package com.example.demobook.module.base.validator;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import com.example.demobook.module.base.Constants;

public class PhoneNumberValidator implements ConstraintValidator<PhoneNumberConstraint, String> {

    @Override
    public void initialize(PhoneNumberConstraint phoneNumber) {}

    @Override
    public boolean isValid(String phoneNumber, ConstraintValidatorContext cxt) {
        return phoneNumber == null || phoneNumber.matches(Constants.PHONE_NUMBER_REGEX);
    }
}
