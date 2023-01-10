package com.example.bilingual.validation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class PasswordValidator implements ConstraintValidator<PasswordValid, String> {

    @Override
    public boolean isValid(String password,
                           ConstraintValidatorContext constraintValidatorContext) {
        if(password.length() > 8) {
            return password.matches("^[a-zA-Z0-9]{8,16}$");
        } else {
            return false;
        }
    }
}
