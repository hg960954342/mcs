package com.intplog.mcs.common;

import org.springframework.dao.DuplicateKeyException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.NoHandlerFoundException;

import javax.validation.ConstraintViolationException;
import javax.validation.ValidationException;

/**
 * @author suizhonghao
 * @version 1.0
 * @date 2020/6/30 11:05
 */
@RestControllerAdvice
public class GlobalExceptionHandler {

    private static int DUPLICATE_KEY_CODE = 500;
    private static int PARAM_FAIL_CODE = 500;
    private static int VALIDATION_CODE = 500;


    /**
     * 方法参数校验
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Object handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        return new JsonData(false, e.getBindingResult().getFieldError().getDefaultMessage(), "500");
    }

    /**
     * ValidationException
     */
    @ExceptionHandler(ValidationException.class)
    public Object handleValidationException(ValidationException e) {
        return new JsonData(false, e.getCause().getMessage(), "500");
    }

    /**
     * ConstraintViolationException
     */
    @ExceptionHandler(ConstraintViolationException.class)
    public Object handleConstraintViolationException(ConstraintViolationException e) {
        return new JsonData(false, e.getMessage(), "500");
    }

    @ExceptionHandler(NoHandlerFoundException.class)
    public Object handlerNoFoundException(Exception e) {
        return new JsonData(false, "路径不存在，请检查路径是否正确", "404");
    }

    @ExceptionHandler(DuplicateKeyException.class)
    public Object handleDuplicateKeyException(DuplicateKeyException e) {
        return new JsonData(false, "数据重复，请检查后提交", "500");
    }


    @ExceptionHandler(Exception.class)
    public Object handleException(Exception e) {
        return new JsonData(false, e.getCause().getMessage(), "500");
    }

}
