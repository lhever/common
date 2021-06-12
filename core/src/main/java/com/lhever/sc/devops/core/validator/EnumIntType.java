package com.lhever.sc.devops.core.validator;

import javax.validation.Constraint;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import javax.validation.Payload;
import javax.validation.constraints.NotNull;
import java.lang.annotation.*;


@NotNull
@Target({ElementType.FIELD, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Constraint(validatedBy = {EnumIntType.EnumIntValidator.class})
public @interface EnumIntType {
    public int[] allowValues() default {};

    String message() default "请求参数不合法";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};


    public class EnumIntValidator implements ConstraintValidator<EnumIntType, Integer> {
        int[] allowValues;

        @Override
        public void initialize(EnumIntType constraintAnnotation) {
            allowValues = constraintAnnotation.allowValues();
        }

        @Override
        public boolean isValid(Integer value, ConstraintValidatorContext context) {
            for (int i = 0; i < allowValues.length; i++) {
                if (allowValues[i] == value) {
                    return true;
                }
            }
            return false;
        }

    }
}
