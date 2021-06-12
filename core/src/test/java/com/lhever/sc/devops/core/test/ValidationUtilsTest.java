package com.lhever.sc.devops.core.test;

import com.lhever.sc.devops.core.utils.DateFormatUtils;
import com.lhever.sc.devops.core.utils.JsonUtils;
import com.lhever.sc.devops.core.utils.StringUtils;
import com.lhever.sc.devops.core.utils.ValidationUtils;
import lombok.Data;
import org.junit.Test;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.util.Date;

/**
 * <p>
 * 类说明
 * </p>
 *
 * @author lihong10 2020/5/14 14:15
 * @version v1.0
 * @modificationHistory=========================逻辑或功能性重大变更记录
 * @modify by user: {修改人} 2020/5/14 14:15
 * @modify by reason:{方法名}:{原因}
 */
public class ValidationUtilsTest {


    @Data
    public static class SizeValidationDto {
        @Size(max = 32)
        private String name;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }


    @Data
    public static class PatternValidationDto {

        @Pattern(regexp="^(\\d{4}-\\d{2}-\\d{2}[T]{1}\\d{2}:\\d{2}:\\d{2}\\.\\d{3}[+-]{1}\\d{2}:\\d{2})$", message="日期格式错误")
        private String startTime;


        @Pattern(regexp="^(\\d{4}-\\d{2}-\\d{2}[T]{1}\\d{2}:\\d{2}:\\d{2}[+-]{1}\\d{2}:\\d{2})$", message="日期格式错误")
        private String endTime;

        public String getStartTime() {
            return startTime;
        }

        public void setStartTime(String startTime) {
            this.startTime = startTime;
        }

        public String getEndTime() {
            return endTime;
        }

        public void setEndTime(String endTime) {
            this.endTime = endTime;
        }
    }



    @Test
    public void testSize() {
        SizeValidationDto dto = new SizeValidationDto();
        dto.setName(StringUtils.getUuid());
        ValidationUtils.ValidResult validResult = ValidationUtils.validateBean(dto);
        System.out.println(JsonUtils.object2Json(validResult, true));


        //长度超过32
        dto.setName(StringUtils.getUuid() + "3333");
        validResult = ValidationUtils.validateBean(dto);
        System.out.println(JsonUtils.object2Json(validResult, true));
    }


    @Test
    public void TestPattern() {
        PatternValidationDto dto = new PatternValidationDto();
        String time = DateFormatUtils.toISO8601DateString(new Date());
        System.out.println(time);
        dto.setStartTime(time);

        ValidationUtils.ValidResult validResult = ValidationUtils.validateBean(dto);
        System.out.println(JsonUtils.object2Json(validResult, true));


        dto.setStartTime("2020-05-14T14:39:17+08:00");
        validResult = ValidationUtils.validateBean(dto);
        System.out.println(JsonUtils.object2Json(validResult, true));


        dto = new PatternValidationDto();
        dto.setEndTime("2020-05-14T14:39:17+08:00");
        validResult = ValidationUtils.validateBean(dto);
        System.out.println(JsonUtils.object2Json(validResult, true));

        dto.setEndTime("2020-05-14T14:39:17.000+08:00");
        validResult = ValidationUtils.validateBean(dto);
        System.out.println(JsonUtils.object2Json(validResult, true));


        dto = new PatternValidationDto();
        validResult = ValidationUtils.validateBean(dto);
        System.out.println(JsonUtils.object2Json(validResult, true));
    }


    @Data
    public static class MinMaxValidationDto {

        @Max(value = 100000, message = "页码不能大于100000")
        @Min(value= 1 ,message= "页码不能小于1" )
        private Integer pageNo;

        public Integer getPageNo() {
            return pageNo;
        }

        public void setPageNo(Integer pageNo) {
            this.pageNo = pageNo;
        }
    }

    @Test
    public void testMinMax() {
        MinMaxValidationDto dto = new MinMaxValidationDto();
        dto.setPageNo(null);
        ValidationUtils.ValidResult validResult = ValidationUtils.validateBean(dto);
        System.out.println(JsonUtils.object2Json(validResult, true));


        dto.setPageNo(-1);
        validResult = ValidationUtils.validateBean(dto);
        System.out.println(JsonUtils.object2Json(validResult, true));


        dto.setPageNo(1);
        validResult = ValidationUtils.validateBean(dto);
        System.out.println(JsonUtils.object2Json(validResult, true));


        dto.setPageNo(5);
        validResult = ValidationUtils.validateBean(dto);
        System.out.println(JsonUtils.object2Json(validResult, true));


        dto.setPageNo(1000000);
        validResult = ValidationUtils.validateBean(dto);
        System.out.println(JsonUtils.object2Json(validResult, true));



    }


}




























