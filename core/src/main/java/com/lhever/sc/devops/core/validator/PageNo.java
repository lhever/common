package com.lhever.sc.devops.core.validator;

import javax.validation.Constraint;
import javax.validation.Payload;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.lang.annotation.*;

/**
 * 校验分页参数page有效性
 *
 * @author dengyishi
 * <p>
 * 2017年5月8日 下午6:59:33
 */
@Min(value = 1, message = "pageNo必须大于0!")
@NotNull(message = "pageNo不允许为空!")
@Target({ElementType.FIELD, ElementType.METHOD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Constraint(validatedBy = {})
public @interface PageNo {
    String message() default "无效的分页参数pageNo";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
