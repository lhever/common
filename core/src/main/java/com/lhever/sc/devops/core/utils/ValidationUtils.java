package com.lhever.sc.devops.core.utils;

import lombok.Data;
import org.hibernate.validator.HibernateValidator;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class ValidationUtils {

    private static Validator validator = Validation.byProvider( HibernateValidator.class )
            .configure()
            .addProperty( "hibernate.validator.fail_fast", "true" )
            .buildValidatorFactory().getValidator();

    public static <T> ValidResult validateBean(T t) {
        ValidResult result = new ValidationUtils.ValidResult();
        Set<ConstraintViolation<T>> violationSet = validator.validate(t);
        boolean hasError = violationSet != null && violationSet.size() > 0;
        result.setHasErrors(hasError);
        if (hasError) {
            for (ConstraintViolation<T> violation : violationSet) {
                result.addError(violation.getPropertyPath().toString(), violation.getMessage());
            }
        }
        return result;
    }

    public static <T> ValidResult validateProperty(T obj, String propertyName) {
        ValidResult result = new ValidationUtils.ValidResult();
        Set<ConstraintViolation<T>> violationSet = validator.validateProperty(obj, propertyName);
        boolean hasError = violationSet != null && violationSet.size() > 0;
        result.setHasErrors(hasError);
        if (hasError) {
            for (ConstraintViolation<T> violation : violationSet) {
                result.addError(propertyName, violation.getMessage());
            }
        }
        return result;
    }

    @Data
    public static  class ValidResult {

        /**
         * 是否有错误
         */
        private boolean hasErrors;

        /**
         * 错误信息
         */
        private List<ErrorMessage> errors;

        public ValidResult() {
            this.errors = new ArrayList<>(1);
        }
        public boolean hasErrors() {
            return hasErrors;
        }

        public void setHasErrors(boolean hasErrors) {
            this.hasErrors = hasErrors;
        }

        /**
         * 获取所有验证信息
         * @return 集合形式
         */
        public List<ErrorMessage> getAllErrors() {
            return errors;
        }
        /**
         * 获取所有验证信息
         * @return 字符串形式
         */
        public String getErrors(){
            StringBuilder sb = new StringBuilder();
            for (ErrorMessage error : errors) {
                sb.append(";").append(error.getPropertyPath()).append(":").append(error.getMessage());
            }
            if (sb.length() > 0 && sb.charAt(0) == ';') {
                return sb.substring(1);
            }
            return sb.toString();
        }

        public void addError(String propertyName, String message) {
            this.errors.add(new ErrorMessage(propertyName, message));
        }
    }

    @Data
    public static  class ErrorMessage {

        private String propertyPath;

        private String message;

        public ErrorMessage() {
        }

        public ErrorMessage(String propertyPath, String message) {
            this.propertyPath = propertyPath;
            this.message = message;
        }
    }

}
