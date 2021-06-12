package com.lhever.sc.devops.core.validator;

import javax.validation.Constraint;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import javax.validation.Payload;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;


/**
 * 参数验证注解： 是否为合法的布尔值
 *
 * @author lihong10 2015-5-10 下午7:16:54
 * @version v1.0
 */
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = Bool.Validator.class)
@SuppressWarnings("javadoc")
public @interface Bool {

    String message() default "invalid bool value";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    public class Validator implements ConstraintValidator<Bool, String> {

        /**
         * @see ConstraintValidator#initialize(java.lang.annotation.Annotation)
         * @since v1.0
         */
        @Override
        public void initialize(Bool arg0) {

        }

        /**
         * @see ConstraintValidator#isValid(Object, ConstraintValidatorContext)
         * @since v1.0
         */
        @Override
        public boolean isValid(String arg0, ConstraintValidatorContext arg1) {
            if (arg0 == null) {
                return false;
            }
            return arg0.equalsIgnoreCase("false") || arg0.equalsIgnoreCase("true");
        }
    }
}
