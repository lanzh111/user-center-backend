package com.lan.userCenter.Exception;

import com.lan.userCenter.Common.ErrorCode;

/**
 * 自定义业务异常类
 */
public class BusinessException extends RuntimeException{


    private final int code;
    private final String messages;
    private final String description;


    public BusinessException(String message, int code, String messages, String description) {
        super(message);
        this.code = code;
        this.messages = messages;
        this.description = description;
    }
    public BusinessException(ErrorCode errorCode,String description){
        super(errorCode.getMessages());
        this.code = errorCode.getCode();
        this.messages = errorCode.getMessages();
        this.description = description;
    }

    public BusinessException(ErrorCode errorCode){
        super(errorCode.getMessages());
        this.code = errorCode.getCode();
        this.messages = errorCode.getMessages();
        this.description = errorCode.getDescription();
    }

    public int getCode() {
        return code;
    }

    public String getMessages() {
        return messages;
    }

    public String getDescription() {
        return description;
    }
}
