package com.lhever.sc.devops.core.validator;


import com.lhever.sc.devops.core.utils.StringUtils;

import javax.validation.Constraint;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import javax.validation.Payload;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;


/**
 * 校验字符串是否符合yyyyMMdd日期格式的注解
 *
 * @author lihong10 2016年4月5日 下午3:42:00
 * @version v2.0
 */
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = DateStr.Validator.class)
@SuppressWarnings("javadoc")
public @interface DateStr {

    String message() default "date string did not match the format of yyyyMMdd";

    boolean allowBlank() default false;

    Class<?>[] groups() default
            {};

    Class<? extends Payload>[] payload() default
            {};

    public class Validator implements ConstraintValidator<DateStr, String> {

        boolean allowBlank;

        /**
         * @see ConstraintValidator#initialize(java.lang.annotation.Annotation)
         * @since v1.0
         */
        @Override
        public void initialize(DateStr arg0) {
            allowBlank = arg0.allowBlank();

        }

        /**
         * @see ConstraintValidator#isValid(Object,
         * ConstraintValidatorContext)
         * @since v1.0
         */
        @Override
        public boolean isValid(String arg0, ConstraintValidatorContext arg1) {
            if (arg0 == null) {
                return allowBlank;
            }
            return StringUtils.isDateString(arg0);
        }
    }
}






