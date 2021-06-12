package com.lhever.sc.devops.core.validator;


import com.lhever.sc.devops.core.utils.StringUtils;

import javax.validation.Constraint;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import javax.validation.Payload;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;


/**
 * 参数验证注解： 是否为合法的中国大陆手机号
 *
 * @author lihong10 2015-5-10 下午7:16:54
 * @version v1.0
 */
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = PhoneNumber.Validator.class)
@SuppressWarnings("javadoc")
public @interface PhoneNumber {

    String message() default "invalid phone number";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    public class Validator implements ConstraintValidator<PhoneNumber, String> {

        /**
         * @see ConstraintValidator#initialize(java.lang.annotation.Annotation)
         * @since v1.0
         */
        @Override
        public void initialize(PhoneNumber arg0) {

        }

        /**
         * @see ConstraintValidator#isValid(Object, ConstraintValidatorContext)
         * @since v1.0
         */
        @Override
        public boolean isValid(String arg0, ConstraintValidatorContext arg1) {
            return StringUtils.isPhoneNumber(arg0);
        }
    }
}
