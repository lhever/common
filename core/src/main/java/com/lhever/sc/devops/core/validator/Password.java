package com.lhever.sc.devops.core.validator;

import com.lhever.sc.devops.core.utils.StringUtils;

import javax.validation.Constraint;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import javax.validation.Payload;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * 密码参数验证注解： 是否为合法的密码
 *
 * @author lihong10 2015-5-10 下午7:16:54
 * @version v1.0
 */
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = Password.Validator.class)
@SuppressWarnings("javadoc")
public @interface Password {

    String message() default "invalid password";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    public class Validator implements ConstraintValidator<Password, String> {

        /**
         * @see ConstraintValidator#initialize(java.lang.annotation.Annotation)
         * @since v1.0
         */
        @Override
        public void initialize(Password arg0) {

        }

        /**
         * @see ConstraintValidator#isValid(Object, ConstraintValidatorContext)
         * @since v1.0
         */
        @Override
        public boolean isValid(String arg0, ConstraintValidatorContext arg1) {
            return StringUtils.isPasssword(arg0);
        }
    }
}
