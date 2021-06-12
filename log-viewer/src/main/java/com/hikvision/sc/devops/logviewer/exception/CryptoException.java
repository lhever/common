package com.hikvision.sc.devops.logviewer.exception;

/**
 * {@code CryptoException}: 与加密解密相关的异常
 *
 * @author lihong10 2018/9/5 16:36:00
 * return
 */

public class CryptoException extends BaseRuntimeException {

    public CryptoException(Integer errorCode, String msg) {
        super(msg);
        checkCode(errorCode);
    }

    public CryptoException(Integer errorCode, String msg, Throwable cause) {
        super(msg, cause);
        checkCode(errorCode);
    }

    public CryptoException(Integer errorCode, Throwable cause) {
        super(cause);
        checkCode(errorCode);
    }


    public CryptoException(String msg) {
        this(ResponseCode.CRYPTO_EXCEPTION.getCode(), msg);
    }

    public CryptoException(String msg, Throwable cause) {
        this(ResponseCode.CRYPTO_EXCEPTION.getCode(), msg, cause);
    }

    public CryptoException(Throwable cause) {
        this(ResponseCode.CRYPTO_EXCEPTION.getCode(), cause);
    }

    private void checkCode(Integer errorCode) {
        checkCode(errorCode, ResponseCode.CRYPTO_EXCEPTION);

    }


}
