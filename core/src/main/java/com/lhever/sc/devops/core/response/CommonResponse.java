package com.lhever.sc.devops.core.response;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * * author wangwei
 * * CREATE ON 2017/10/24 11:12
 * * DECRIPTION
 * * WWW.JOINTEM.COM
 **/
@Data
@ApiModel
public class CommonResponse<T> {

    @ApiModelProperty(value="消息内容")
    private String mesg;

    @ApiModelProperty(value= "实际数据")
    private T data;

    @ApiModelProperty(value = "消息码")
    private String code;

    public CommonResponse(){}

    public CommonResponse(T t){
        this.data = t;
    }

    public CommonResponse(String code, String mesg, T data) {
        this.code = code;
        this.mesg = mesg;
        this.data = data;
    }

    public CommonResponse(String code, String mesg) {
        this.code = code;
        this.mesg = mesg;
    }

    public String toString(){
        return JSON.toJSONString(this, SerializerFeature.WriteMapNullValue);
    }
}
