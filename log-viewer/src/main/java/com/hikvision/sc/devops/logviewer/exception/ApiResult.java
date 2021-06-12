package com.hikvision.sc.devops.logviewer.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ApiResult<T> implements Cloneable {
    Logger log = LoggerFactory.getLogger(ApiResult.class);

    public static final ApiResult EMPTY = new ApiResult();
    private boolean success = true;
    private int code = ResponseCode.SUCCESS.getCode();
    private String msg = ResponseCode.SUCCESS.getMsg();
    private T data;

    public ApiResult(ResponseCode responseCode) {
        this(responseCode.getCode(), responseCode.getMsg());
    }

    /**
     * @param code
     * @param msg
     */
    public ApiResult(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public ApiResult() {
    }

    public boolean getSuccess() {
        return success;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    @Override
    protected ApiResult clone() {

        ApiResult clone = null;
        try {
            clone = (ApiResult) super.clone();
        } catch (CloneNotSupportedException e) {
            log.error("clone ApiResult对象失败", e);
            clone = new ApiResult();
        } catch (Throwable e) {
            log.error("clone ApiResult对象异常", e);
            clone = new ApiResult();
        }


        return clone;
    }

    public static ApiResult clone(Integer code, String msg, boolean success) {

        ApiResult clone = EMPTY.clone();
        clone.setCode(code);
        clone.setMsg(msg);
        clone.setSuccess(success);

        return clone;
    }


    public static <T> ApiResult clone(Integer code, String msg, boolean success, T data) {

        ApiResult clone = EMPTY.clone();
        clone.setCode(code);
        clone.setMsg(msg);
        clone.setSuccess(success);
        clone.setData(data);

        return clone;
    }

    public static <T> ApiResult clone(T data) {

        ApiResult clone = EMPTY.clone();
        clone.setData(data);


        return clone;
    }


}
