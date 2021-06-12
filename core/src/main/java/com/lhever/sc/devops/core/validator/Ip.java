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
 * IP 格式校验
 *
 * @author dengyishi
 * <p>
 * 2017年5月8日 下午6:58:52
 */
@NotEmpty
@Target({ElementType.FIELD, ElementType.METHOD, ElementType.ANNOTATION_TYPE, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Constraint(validatedBy = {Ip.IpStringValidator.class})
public @interface Ip {
    String message() default "不被允许的值";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};


    public class IpStringValidator implements ConstraintValidator<Ip, String> {
        static final String regex = "^(1\\d{2}|2[0-4]\\d|25[0-5]|[1-9]\\d|[1-9])\\." + "(00?\\d|1\\d{2}|2[0-4]\\d|25[0-5]|[1-9]\\d|\\d)\\."
                + "(00?\\d|1\\d{2}|2[0-4]\\d|25[0-5]|[1-9]\\d|\\d)\\." + "(00?\\d|1\\d{2}|2[0-4]\\d|25[0-5]|[1-9]\\d|\\d)$";

        @Override
        public void initialize(Ip constraintAnnotation) {
        }

        @Override
        public boolean isValid(String value, ConstraintValidatorContext context) {

            Pattern pattern = Pattern.compile(regex);
            Matcher matcher = pattern.matcher(value);
            return matcher.matches();
        }

    }

}
