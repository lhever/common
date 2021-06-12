package com.lhever.sc.devops.core.validator;

import javax.validation.Constraint;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import javax.validation.Payload;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * 参数验证注解： 是否为合法的性别
 *
 * @author lihong10 2015-5-10 下午7:16:54
 * @version v1.0
 */
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = Gender.Validator.class)
@SuppressWarnings("javadoc")
public @interface Gender {

    String message() default "invalid gender";

    /**
     * 是否允许为空
     *
     * @return
     * @author dengdan 2015-7-23 下午7:26:57
     * @since v1.0
     */
    boolean allowBlank() default false;

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    public class Validator implements ConstraintValidator<Gender, String> {
        boolean allowBlank;

        /**
         * @see ConstraintValidator#initialize(java.lang.annotation.Annotation)
         * @since v1.0
         */
        @Override
        public void initialize(Gender gender) {
            allowBlank = gender.allowBlank();
        }

        /**
         * @see ConstraintValidator#isValid(Object, ConstraintValidatorContext)
         * @since v1.0
         */
        @Override
        public boolean isValid(String arg0, ConstraintValidatorContext arg1) {
            if (arg0 == null) {
                return allowBlank;
            }
            return arg0.equalsIgnoreCase("M") || arg0.equalsIgnoreCase("F");
        }
    }
}
