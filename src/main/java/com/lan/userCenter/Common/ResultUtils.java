package com.lan.userCenter.Common;

import com.lan.userCenter.Model.Result;
import com.lan.userCenter.Model.constant.HttpStatus;

/**
 * 统一返回值工具类
 */
public class ResultUtils {
    /**
     * 成功
     * @param data
     * @param <T>
     * @return
     */
    public static <T> Result<T> success(T data){
        return new Result(HttpStatus.SUCCESS,"SUCCESS",data);
    }

    /**
     * 成功
     * @param errorCode
     * @param <T>
     * @return
     */
    public static <T> Result<T>  success(ErrorCode errorCode) {
        return new Result<>(errorCode.getCode(),null,errorCode.getMessages(),errorCode.getDescription());
    }

    /**
     * 成功
     * @param errorCode
     * @param data
     * @param <T>
     * @return
     */
    public static <T> Result<T>  success(ErrorCode errorCode,T data) {
        return new Result<T>(errorCode.getCode(),data,errorCode.getMessages(),errorCode.getDescription());
    }

    /**
     * 成功
     * @param errorCode
     * @param messages
     * @param <T>
     * @return
     */
    public static <T> Result<T>  success(ErrorCode errorCode,String messages) {
        return new Result<>(errorCode.getCode(),null,messages,errorCode.getDescription());
    }


    /**
     * 失败
     * @param data
     * @return
     */
    public static <T> Result<T>   failure( Object data){
        return new Result(HttpStatus.FAILURE,"FAILURE",data);
    }

    /**
     * 失败
     * @param errorCode
     * @param <T>
     * @return
     */
    public static <T> Result<T>   failure(ErrorCode errorCode){
        return new Result(errorCode.getCode(),null,errorCode.getMessages(), errorCode.getDescription());
    }



    /**
     * 失败
     * @param errorCode
     * @param description
     * @param <T>
     * @return
     */
    public static <T> Result<T>   failure(ErrorCode errorCode,String description){
        return new Result(errorCode.getCode(),null,errorCode.getMessages(), description);
    }

    public static Result failure(int code, String messages, String description) {
        return new Result(code,null,messages, description);
    }
}
