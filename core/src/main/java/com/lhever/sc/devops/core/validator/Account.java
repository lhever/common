package com.lhever.sc.devops.core.validator;


import com.lhever.sc.devops.core.utils.StringUtils;

import javax.validation.Constraint;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import javax.validation.Payload;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;


/**
 * 参数验证注解： 合法的账号——以字母开头， 只能包含字母数字和下划线
 *
 * @author lihong10 2015-5-10 下午7:16:54
 * @version v1.0
 */
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = Account.Validator.class)
@SuppressWarnings("javadoc")
public @interface Account {

    String message() default "invalid familyface account";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    public class Validator implements ConstraintValidator<Account, String> {

        /**
         * @see ConstraintValidator#initialize(java.lang.annotation.Annotation)
         * @since v1.0
         */
        @Override
        public void initialize(Account arg0) {

        }

        /**
         * @see ConstraintValidator#isValid(Object, ConstraintValidatorContext)
         * @since v1.0
         */
        @Override
        public boolean isValid(String arg0, ConstraintValidatorContext arg1) {
            return StringUtils.isAccount(arg0);
        }
    }
}
