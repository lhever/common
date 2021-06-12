package com.lhever.sc.devops.core.validator;

import org.hibernate.validator.constraints.Range;

import javax.validation.Constraint;
import javax.validation.Payload;
import javax.validation.constraints.NotNull;
import java.lang.annotation.*;

/**
 * 校验分页参数pageSize有效性
 *
 * @author dengyishi
 * <p>
 * 2017年5月8日 下午6:59:33
 */
@Range(min = 1, max = 1000, message = "每页大小必须在1-1000之间!")
@NotNull(message = "page size不允许为空!")
@Target({ElementType.FIELD, ElementType.METHOD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Constraint(validatedBy = {})
public @interface PageSize {

    String message() default "无效的分页参数page size";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
