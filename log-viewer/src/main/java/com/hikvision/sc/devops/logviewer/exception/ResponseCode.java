package com.hikvision.sc.devops.logviewer.exception;

public enum ResponseCode {


    SUCCESS(0, "成功"),

    //权限类异常： 比如用户不存在， 密码不正确
    BUSINESS_EXCEPTION(10, "业务类异常"),

    //权限类异常： 比如没登陆， 没有携带token
    AUTHRIZATION_EXCEPTION(20, "权限类异常"),

    //参数不合法异常: 比如参数为空/空指针， 除数为0
    ILLEGAL_PARAM_EXCEPTION(30, "参数不合法异常"),


    //redis不可用， pg连不上等情况都属于组件异常
    COMPONENTS_EXCEPTION(40, "组件异常"),

    CRYPTO_EXCEPTION(50, "加解密异常"),

    FREQUENCY_TOO_HIGH_EXCEPTION(60, "访问频次过高异常"),

    UNKNOWN_EXCEPTION(-1, "未知错误");

    private int code;
    private String msg;

    ResponseCode(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public int getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }

    public static boolean exists(Integer code) {
        if (code == null) {
            return false;
        }

        for (ResponseCode rspCode : ResponseCode.values()) {
            if (code == rspCode.getCode()) {
                return true;
            }
        }
        return false;
    }


}
