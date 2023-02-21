package com.example.LikeBook.Validations;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.PropertyAccessorFactory;

public class PasswordMatchValidator implements ConstraintValidator<PasswordMatch, Object> {

    private String firstPass;

    private String secondPass;

    private String message;

    @Override
    public void initialize(PasswordMatch constraintAnnotation) {
        this.firstPass = constraintAnnotation.firstPass();
        this.secondPass = constraintAnnotation.secondPass();
        this.message = constraintAnnotation.message();
    }

    @Override
    public boolean isValid(Object value, ConstraintValidatorContext context) {
        BeanWrapper beanWrapper = PropertyAccessorFactory.forBeanPropertyAccess(value);

        Object firstValue = beanWrapper.getPropertyValue(this.firstPass);
        Object secondValue = beanWrapper.getPropertyValue(this.secondPass);

        boolean valid = false;

        if (firstValue == null) {
            valid = secondValue == null;
        }else {
            valid = firstValue.equals(secondValue);
        }

        if (!valid) {
            context.buildConstraintViolationWithTemplate(message).addPropertyNode(secondPass).addConstraintViolation()
                    .disableDefaultConstraintViolation();
        }

        return valid;
    }
}
