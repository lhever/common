package com.lhever.sc.devops.logviewer.exception;

public class IllegalParamException extends BaseRuntimeException {

    public IllegalParamException(int errorCode, String msg) {
        super(msg);
        checkCode(errorCode);
    }

    public IllegalParamException(int errorCode, String msg, Throwable cause) {
        super(msg, cause);
        checkCode(errorCode);
    }

    public IllegalParamException(int errorCode, Throwable cause) {
        super(cause);
        checkCode(errorCode);
    }

    public IllegalParamException(String msg) {
        this(ResponseCode.ILLEGAL_PARAM_EXCEPTION.getCode(), msg);
    }

    public IllegalParamException(String msg, Throwable cause) {
        this(ResponseCode.ILLEGAL_PARAM_EXCEPTION.getCode(), msg, cause);
    }

    public IllegalParamException(Throwable cause) {
        this(ResponseCode.ILLEGAL_PARAM_EXCEPTION.getCode(), cause);
    }


    private void checkCode(Integer errorCode) {
        checkCode(errorCode, ResponseCode.ILLEGAL_PARAM_EXCEPTION);
    }
}
