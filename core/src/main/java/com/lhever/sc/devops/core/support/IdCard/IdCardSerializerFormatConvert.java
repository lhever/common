package com.lhever.sc.devops.core.support.IdCard;

import com.fasterxml.jackson.annotation.JacksonAnnotationsInside;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author liuyabin6 2019/11/22 21:59
 * @version V1.0
 */


@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
@JacksonAnnotationsInside
@JsonSerialize(using = IdCardSerializerConvert.class)
public @interface IdCardSerializerFormatConvert {
}
