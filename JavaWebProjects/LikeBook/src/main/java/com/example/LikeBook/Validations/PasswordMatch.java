package com.example.LikeBook.Validations;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Constraint(validatedBy = PasswordMatchValidator.class)
public @interface PasswordMatch {

    String firstPass();

    String secondPass();

    String message() default "Invalid passwords";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
