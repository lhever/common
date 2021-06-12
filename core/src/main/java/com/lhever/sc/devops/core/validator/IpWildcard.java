package com.lhever.sc.devops.core.validator;


import javax.validation.Constraint;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import javax.validation.Payload;
import javax.validation.constraints.NotEmpty;
import java.lang.annotation.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


/**
 * IP通配符校验
 *
 * @author dengyishi
 * <p>
 * 2017年5月8日 下午6:59:09
 */
@NotEmpty
@Target({ElementType.FIELD, ElementType.METHOD, ElementType.ANNOTATION_TYPE, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Constraint(validatedBy = {IpWildcard.IpWildcardValidator.class})
public @interface IpWildcard {
    String message() default "不被允许的值";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};


    public class IpWildcardValidator implements ConstraintValidator<IpWildcard, String> {
        static final String regEx = "^((1\\d{2}|2[0-4]\\d|25[0-5]|[1-9]\\d|[1-9])|(\\u002A))\\."
                + "((1\\d{2}|2[0-4]\\d|25[0-5]|[1-9]\\d|\\d)|(\\u002A))\\." + "((1\\d{2}|2[0-4]\\d|25[0-5]|[1-9]\\d|\\d)|(\\u002A))\\."
                + "((1\\d{2}|2[0-4]\\d|25[0-5]|[1-9]\\d|\\d)|(\\u002A))$";

        @Override
        public void initialize(IpWildcard constraintAnnotation) {
        }

        @Override
        public boolean isValid(String value, ConstraintValidatorContext context) {

            Pattern pattern = Pattern.compile(regEx);
            Matcher matcher = pattern.matcher(value);
            return matcher.matches();
        }

    }
}
