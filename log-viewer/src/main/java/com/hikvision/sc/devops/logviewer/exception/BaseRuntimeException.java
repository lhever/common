package com.hikvision.sc.devops.logviewer.exception;

public abstract class BaseRuntimeException extends RuntimeException {

    /*
     * 错误码
     */
    protected Integer errorCode;


    public BaseRuntimeException(String msg) {
        super(msg);
    }


    public BaseRuntimeException(String msg, Throwable cause) {
        super(msg, cause);
    }

    public BaseRuntimeException(Throwable cause) {
        super(cause);
    }


    public Integer getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(Integer errorCode) {
        this.errorCode = errorCode;
    }

    public String getMessage() {
        return super.getMessage();
    }

    public void checkCode(Integer errorCode, ResponseCode code) {
        if (errorCode == null) {
            this.errorCode = code.getCode();
            return;
        }
        this.errorCode = errorCode;

    }


}
