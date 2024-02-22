package com.sergiylunyov.spring.Validation;
import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.FIELD, ElementType.METHOD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = StartsWithUppercaseValidator.class)
public @interface StartsWithUppercase {

    String message() default "Parameter must start with an uppercase letter";

    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}