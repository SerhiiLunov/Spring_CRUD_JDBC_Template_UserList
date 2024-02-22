package com.sergiylunyov.spring.Validation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class StartsWithUppercaseValidator implements ConstraintValidator<StartsWithUppercase, String> {

    @Override
    public void initialize(StartsWithUppercase constraintAnnotation) {
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        return value != null && !value.isEmpty() && Character.isUpperCase(value.charAt(0));
    }
}