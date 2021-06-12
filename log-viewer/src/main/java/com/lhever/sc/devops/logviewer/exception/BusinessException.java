package com.lhever.sc.devops.logviewer.exception;

public class BusinessException extends BaseRuntimeException {

    public BusinessException(Integer errorCode, String msg) {

        super(msg);
        checkCode(errorCode);
    }

    public BusinessException(Integer errorCode, String msg, Throwable cause) {
        super(msg, cause);
        checkCode(errorCode);
    }

    public BusinessException(Integer errorCode, Throwable cause) {
        super(cause);
        checkCode(errorCode);
    }


    public BusinessException(String msg) {
        this(ResponseCode.BUSINESS_EXCEPTION.getCode(), msg);
    }

    public BusinessException(String msg, Throwable cause) {
        this(ResponseCode.BUSINESS_EXCEPTION.getCode(), msg, cause);
    }

    public BusinessException(Throwable cause) {
        this(ResponseCode.BUSINESS_EXCEPTION.getCode(), cause);
    }

    private void checkCode(Integer errorCode) {
        checkCode(errorCode, ResponseCode.BUSINESS_EXCEPTION);
    }


}
