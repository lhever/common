package com.hikvision.sc.devops.logviewer.exception;

public class FrequencyTooHighException extends BaseRuntimeException {

    public FrequencyTooHighException(Integer errorCode, String msg) {
        super(msg);
        checkCode(errorCode);
    }

    public FrequencyTooHighException(Integer errorCode, String msg, Throwable cause) {
        super(msg, cause);
        checkCode(errorCode);
    }

    public FrequencyTooHighException(Integer errorCode, Throwable cause) {
        super(cause);
        checkCode(errorCode);
    }


    public FrequencyTooHighException(String msg) {
        this(ResponseCode.FREQUENCY_TOO_HIGH_EXCEPTION.getCode(), msg);
    }

    public FrequencyTooHighException(String msg, Throwable cause) {
        this(ResponseCode.FREQUENCY_TOO_HIGH_EXCEPTION.getCode(), msg, cause);
    }

    public FrequencyTooHighException(Throwable cause) {
        this(ResponseCode.FREQUENCY_TOO_HIGH_EXCEPTION.getCode(), cause);
    }

    private void checkCode(Integer errorCode) {
        checkCode(errorCode, ResponseCode.FREQUENCY_TOO_HIGH_EXCEPTION);

    }


}
