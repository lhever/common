package com.lhever.sc.devops.core.validator;


import javax.validation.Constraint;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import javax.validation.Payload;
import javax.validation.constraints.NotEmpty;
import java.lang.annotation.*;


@NotEmpty
@Target({ElementType.FIELD, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Constraint(validatedBy = {EnumStringType.EnumStringValidator.class})
public @interface EnumStringType {
    public String[] allowValues() default {};

    String message() default "不被允许的值";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};


    public class EnumStringValidator implements ConstraintValidator<EnumStringType, String> {
        String[] allowValues;

        @Override
        public void initialize(EnumStringType constraintAnnotation) {
            allowValues = constraintAnnotation.allowValues();
        }

        @Override
        public boolean isValid(String value, ConstraintValidatorContext context) {
            for (int i = 0; i < allowValues.length; i++) {
                if (allowValues[i].equals(value)) {
                    return true;
                }
            }
            return false;
        }

    }

}
