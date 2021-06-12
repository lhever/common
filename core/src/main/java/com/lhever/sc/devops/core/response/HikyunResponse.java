package com.lhever.sc.devops.core.response;

import lombok.Data;

import java.io.Serializable;

/**
 * <p>
 * 类说明
 * </p>
 *
 * @author lihong10 2020/2/19 10:24
 * @version v1.0
 * @modificationHistory=========================逻辑或功能性重大变更记录
 * @modify by user: {修改人} 2020/2/19 10:24
 * @modify by reason:{方法名}:{原因}
 */
@Data
public class HikyunResponse<T> implements Serializable {

    private static final long serialVersionUID = -9146805371831100892L;

    final public static HikyunResponse SUCCESS = new HikyunResponse("200", "操作成功");
    final public static HikyunResponse ERROR = new HikyunResponse("400", "操作失败");
    final public static HikyunResponse EXCEPTION = new HikyunResponse("500", "服务异常");

    private String code;

    private String msg;

    private T data;

    public HikyunResponse() {
    }

    public HikyunResponse(String code, String msg) {
        this(code, msg, null);
    }

    public HikyunResponse(String code, String msg, T data) {
        this.code = code;
        this.msg = msg;
        this.data = data;
    }

    public  <V> HikyunResponse<V> copyThis(V data) {
        return new HikyunResponse<>(code, msg, data);
    }

    public static <T> HikyunResponse<T> error(String msg) {
        return new HikyunResponse<>(ERROR.code, msg);
    }


    public static <T> HikyunResponse<T> success(String msg) {
        return new HikyunResponse<>(SUCCESS.code, msg);
    }

    public static <T> HikyunResponse<T> success(T object) {
        return SUCCESS.copyThis(object);
    }

}


