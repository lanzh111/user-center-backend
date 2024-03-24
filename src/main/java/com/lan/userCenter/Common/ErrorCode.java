package com.lan.userCenter.Common;

/**
 * 错误状态码
 */
public enum ErrorCode {
    INTERNAL_SERVER_ERROR(500,"服务器出错",""),
    SUCCESS(200,"ok",""),
    NOT_NULL_ERROR(40001,"参数不能为空",""),
    PARAM_ERROR(40002,"参数错误",""),
    NOT_AUTH_ERROR(40005,"没有权限",""),
    NOT_LOGIN_ERROR(40006,"没有登录",""),
    NOT_ADMIN_ERROR(40007,"不是管理员","");
   private int code;
   private String messages;
   private String description;

    public int getCode() {
        return code;
    }

    public String getMessages() {
        return messages;
    }

    public String getDescription() {
        return description;
    }


    ErrorCode(int code, String messages, String description) {
        this.code = code;
        this.messages = messages;
        this.description = description;
    }

    ErrorCode(int code, String messages) {
       this(code,messages,"");
    }
    ErrorCode(int code) {
        this(code,"","");
    }
}
