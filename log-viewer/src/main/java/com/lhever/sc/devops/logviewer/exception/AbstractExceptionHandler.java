package com.lhever.sc.devops.logviewer.exception;


import com.lhever.sc.devops.core.constant.CommonConstants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataAccessResourceFailureException;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingRequestHeaderException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.multipart.support.MissingServletRequestPartException;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.util.List;
import java.util.Set;

public abstract class AbstractExceptionHandler {
    Logger log = LoggerFactory.getLogger(AbstractExceptionHandler.class);

    /**
     * 后台组件异常的通用提示信息
     */
//	private static final String COMPONENTS_EXCEPTION_MSG = "server components error, please check immediately";
    private static final String COMPONENTS_EXCEPTION_MSG = "后台组件未完全启动，导致请求失败";

    /**
     * 拦截权限类异常
     *
     * @param e return
     * @author lihong10 2018/8/15 10:15:00
     */
    @ExceptionHandler({AuthrizationException.class})
    public ApiResult handleAuthrizationException(AuthrizationException e) {
        ApiResult apiResult = ApiResult.clone(e.getErrorCode(), e.getMessage(), false);
        log.error("权限异常： " + e.getMessage(), e);

        return apiResult;
    }

    /**
     * 拦截业务类异常
     *
     * @param e return
     * @author lihong10 2018/8/15 10:15:00
     */
    @ExceptionHandler({BusinessException.class})
    public ApiResult handleBusinessException(BusinessException e) {
        ApiResult apiResult = ApiResult.clone(e.getErrorCode(), e.getMessage(), false);
        log.error("业务异常： " + e.getMessage(), e);
        return apiResult;
    }

    /**
     * 拦截组件、中间件相关的异常
     *
     * @param e return
     * @author lihong10 2018/8/15 10:15:00
     */
    @ExceptionHandler({ComponentsException.class})
    public ApiResult handleComponentsException(ComponentsException e) {
        ApiResult apiResult = ApiResult.clone(e.getErrorCode(), e.getMessage(), false);
        log.error("业务异常： " + e.getMessage(), e);
        return apiResult;
    }

    /**
     * 拦截参数类的异常
     *
     * @param e return
     * @author lihong10 2018/8/15 10:15:00
     */
    @ExceptionHandler(IllegalParamException.class)
    public ApiResult handleIllegalParamException(IllegalParamException e) {
        ApiResult apiResult = ApiResult.clone(e.getErrorCode(), e.getMessage(), false);
        log.error("参数异常：" + e.getMessage(), e);
        return apiResult;
    }


    /**
     * 拦截hibernate validation参数校验失败抛出的异常
     *
     * @param e return
     * @author lihong10 2018/8/15 10:15:00
     */
    @ExceptionHandler(value = {ConstraintViolationException.class})
    @ResponseStatus(value = HttpStatus.OK)
    public ApiResult handleResourceNotFoundException(ConstraintViolationException e) {
        Set<ConstraintViolation<?>> violations = e.getConstraintViolations();
        StringBuilder strBuilder = new StringBuilder().append("参数错误： ");
        String msg = null;
        if (violations != null) {
            for (ConstraintViolation<?> violation : violations) {
                if ((msg = violation.getMessage()) == null) {
                    continue;
                }

                strBuilder.append(msg);
            }
        }
        log.error(e.getMessage(), e);
        ApiResult apiResult = ApiResult.clone(ResponseCode.ILLEGAL_PARAM_EXCEPTION.getCode(), strBuilder.toString(), false);
        return apiResult;
    }


    /**
     * 拦截hibernate validation参数校验失败抛出的异常
     *
     * @param e return
     * @author lihong10 2018/8/15 10:15:00
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ApiResult handleException(MethodArgumentNotValidException e) {
        String errorMessage = CommonConstants.EMPTY;
        BindingResult bindingResult = e.getBindingResult();
        List<FieldError> fieldErrors = null;

        if (bindingResult != null && (fieldErrors = bindingResult.getFieldErrors()) != null) {
            int size = fieldErrors.size();
            if (size == 0) {
                //do nothing
            } else if (size == 1) {
                errorMessage = fieldErrors.get(0).getDefaultMessage();
            } else {
                StringBuilder builder = new StringBuilder();
                for (FieldError fieldError : fieldErrors) {
                    if (fieldError.getDefaultMessage() != null) {
                        builder.append(fieldError.getDefaultMessage()).append(CommonConstants.SPACE);
                    }
                }
                errorMessage = builder.toString();
            }
        }
        log.error(errorMessage, e);
        ApiResult apiResult = ApiResult.clone(ResponseCode.ILLEGAL_PARAM_EXCEPTION.getCode(), errorMessage, false);
        return apiResult;

    }

    /**
     * 拦截spring data抛出的异常
     *
     * @param e return
     * @author lihong10 2018/8/15 10:15:00
     */
    @ExceptionHandler(DataAccessResourceFailureException.class)
    public ApiResult handleDataAccessResourceFailureException(DataAccessResourceFailureException e) {
        ApiResult apiResult = ApiResult.clone(ResponseCode.COMPONENTS_EXCEPTION.getCode(), COMPONENTS_EXCEPTION_MSG, false);
        log.error("组件异常， 数据访问失败", e);
        return apiResult;
    }

    @ExceptionHandler(MissingServletRequestParameterException.class)
    public ApiResult handleMissingServletRequestParameterException(MissingServletRequestParameterException e) {
        ApiResult apiResult = ApiResult.clone(ResponseCode.ILLEGAL_PARAM_EXCEPTION.getCode(), "参数 " + e.getParameterName() + " 不存在", false);
        log.error("必填参数" + e.getParameterName() + "不存在", e);
        return apiResult;

    }

    @ExceptionHandler(MissingRequestHeaderException.class)
    public ApiResult handleMissingRequestHeaderException(MissingRequestHeaderException e) {
        String msg = "参数 " + e.getHeaderName() + " 不存在";
        ApiResult apiResult = ApiResult.clone(ResponseCode.ILLEGAL_PARAM_EXCEPTION.getCode(), msg, false);
        log.error(msg, e);
        return apiResult;
    }

    @ExceptionHandler(MissingServletRequestPartException.class)
    public ApiResult handleMissingServletRequestPartException(MissingServletRequestPartException e) {
        ApiResult apiResult = ApiResult.clone(ResponseCode.ILLEGAL_PARAM_EXCEPTION.getCode(), "未指定上传文件", false);
        log.error("异常", e);
        return apiResult;
    }

    @ExceptionHandler(CryptoException.class)
    public ApiResult handleCryptoException(CryptoException e) {
        ApiResult apiResult = ApiResult.clone(ResponseCode.ILLEGAL_PARAM_EXCEPTION.getCode(), "加解密错误", false);
        log.error("异常", e);
        return apiResult;
    }

    /**
     * 拦截所有其他异常
     *
     * @param e return
     * @author lihong10 2018/8/15 10:15:00
     */
    @ExceptionHandler(Exception.class)
    public ApiResult handleException(Exception e) {
        ApiResult apiResult = ApiResult.clone(ResponseCode.UNKNOWN_EXCEPTION.getCode(), "未知错误", false);
        log.error("异常", e);
        return apiResult;
    }

}
