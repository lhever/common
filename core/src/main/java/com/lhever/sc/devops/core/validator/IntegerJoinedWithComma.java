package com.lhever.sc.devops.core.validator;


import com.lhever.sc.devops.core.utils.StringUtils;

import javax.validation.Constraint;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import javax.validation.Payload;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;


/**
 * 参数验证注解： 将一整数数组的数字用逗号连接得到的字符串， 只包含数字和英文逗号
 *
 * @author lihong10 2015-5-10 下午7:16:54
 * @version v1.0
 */
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = IntegerJoinedWithComma.Validator.class)
@SuppressWarnings("javadoc")
public @interface IntegerJoinedWithComma {

    String message() default "invalid integer values";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    public class Validator implements ConstraintValidator<IntegerJoinedWithComma, String> {

        /**
         * @see ConstraintValidator#initialize(java.lang.annotation.Annotation)
         * @since v1.0
         */
        @Override
        public void initialize(IntegerJoinedWithComma arg0) {

        }

        /**
         * @see ConstraintValidator#isValid(Object, ConstraintValidatorContext)
         * @since v1.0
         */
        @Override
        public boolean isValid(String arg0, ConstraintValidatorContext arg1) {
            if (arg0 == null) {
                return true;
            }
            return StringUtils.match("^(\\d|\\,)*$", arg0);
        }
    }
}
