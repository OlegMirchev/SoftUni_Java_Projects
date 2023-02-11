package com.example.BattleShips.Validations;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.PropertyAccessorFactory;

public class PasswordMatchValidator implements ConstraintValidator<PasswordMatch, Object> {

    private String first;

    private String second;

    private String message;

    @Override
    public void initialize(PasswordMatch constraintAnnotation) {
        this.first = constraintAnnotation.firstPass();
        this.second = constraintAnnotation.secondPass();
        this.message = constraintAnnotation.message();
    }

    @Override
    public boolean isValid(Object value, ConstraintValidatorContext context) {
        BeanWrapper beanWrapper = PropertyAccessorFactory.forBeanPropertyAccess(value);

        Object firstValue = beanWrapper.getPropertyValue(this.first);
        Object secondValue = beanWrapper.getPropertyValue(this.second);

        boolean valid = false;

        if (firstValue == null) {
            valid = secondValue == null;
        }else {
            valid = firstValue.equals(secondValue);
        }

        if (!valid) {
            context.buildConstraintViolationWithTemplate(message).addPropertyNode(second).addConstraintViolation()
                    .disableDefaultConstraintViolation();
        }

        return valid;
    }
}
