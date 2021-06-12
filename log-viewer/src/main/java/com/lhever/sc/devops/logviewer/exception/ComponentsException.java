package com.lhever.sc.devops.logviewer.exception;

/**
 * 与系统内各种组件，中间件相关的异常，比如：redis连接补上，pg端口被占用， 云服务不在线等
 *
 * @author lihong10 2018/8/14 10:58:00
 */

public class ComponentsException extends BaseRuntimeException {

    public ComponentsException(String msg) {
        this(ResponseCode.COMPONENTS_EXCEPTION.getCode(), msg);
    }

    public ComponentsException(String msg, Throwable cause) {
        this(ResponseCode.COMPONENTS_EXCEPTION.getCode(), msg, cause);
    }

    public ComponentsException(Throwable cause) {
        this(ResponseCode.COMPONENTS_EXCEPTION.getCode(), cause);
    }

    public ComponentsException(Integer errorCode, String msg) {

        super(msg);
        checkCode(errorCode);
    }

    public ComponentsException(Integer errorCode, String msg, Throwable cause) {
        super(msg, cause);
        checkCode(errorCode);
    }

    public ComponentsException(Integer errorCode, Throwable cause) {
        super(cause);
        checkCode(errorCode);
    }


    @Override
    public Integer getErrorCode() {
        return super.getErrorCode();
    }

    @Override
    public void setErrorCode(Integer errorCode) {
        super.setErrorCode(errorCode);
    }

    @Override
    public String getMessage() {
        return super.getMessage();
    }

    private void checkCode(Integer errorCode) {
        checkCode(errorCode, ResponseCode.COMPONENTS_EXCEPTION);
    }
}
