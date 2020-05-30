package com.product.util;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class RetentionPeriodValidator implements ConstraintValidator<RetentionPeriodValidation, Integer> {

    @Override
    public boolean isValid(Integer value, ConstraintValidatorContext constraintValidatorContext) {
        return value != null;
    }

}