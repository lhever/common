package com.hikvision.sc.devops.logviewer.exception;

public class AuthrizationException extends BaseRuntimeException {

    public AuthrizationException(Integer errorCode, String msg) {
        super(msg);
        checkCode(errorCode);
    }

    public AuthrizationException(Integer errorCode, String msg, Throwable cause) {
        super(msg, cause);
        checkCode(errorCode);
    }

    public AuthrizationException(Integer errorCode, Throwable cause) {
        super(cause);
        checkCode(errorCode);
    }


    public AuthrizationException(String msg) {
        this(ResponseCode.AUTHRIZATION_EXCEPTION.getCode(), msg);
    }

    public AuthrizationException(String msg, Throwable cause) {
        this(ResponseCode.AUTHRIZATION_EXCEPTION.getCode(), msg, cause);
    }

    public AuthrizationException(Throwable cause) {
        this(ResponseCode.AUTHRIZATION_EXCEPTION.getCode(), cause);
    }

    private void checkCode(Integer errorCode) {
        checkCode(errorCode, ResponseCode.AUTHRIZATION_EXCEPTION);
    }


}
