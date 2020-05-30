package com.product.util;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;

import com.product.costant.Constants;

@Documented
@Inherited
@Target({ ElementType.FIELD, ElementType.METHOD })
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = { RetentionPeriodValidator.class })
public @interface RetentionPeriodValidation {

    /**
     * Error Message
     * 
     * @return
     */
    String message() default Constants.INVALID_RETENTION_PERIOD_VALUE;

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
