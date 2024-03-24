package com.lan.userCenter.Common;


import com.lan.userCenter.Exception.BusinessException;
import com.lan.userCenter.Model.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;


/**
 * 全局异常处理
 */
@RestControllerAdvice
@Slf4j
public class GlobeException {
    /**
     * 捕获BusinessException异常
     * @param e
     * @return
     */
    @ExceptionHandler(BusinessException.class)
    public Result handleBusinessException(BusinessException e){
        log.error("BusinessException:"+e.getMessage(),e);
        return ResultUtils.failure(e.getCode(),e.getMessages(),e.getDescription());
    }

    /**
     * 捕获RuntimeException异常
     * @param e
     * @return
     */
    @ExceptionHandler(RuntimeException.class)
    public Result handleRuntimeException(RuntimeException e){
        log.error("RuntimeException:"+e.getMessage(),e);
        return ResultUtils.failure(ErrorCode.INTERNAL_SERVER_ERROR,e.getMessage());
    }
}
