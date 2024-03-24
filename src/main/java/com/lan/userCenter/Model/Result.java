package com.lan.userCenter.Model;

import com.lan.userCenter.Model.constant.HttpStatus;
import lombok.Data;
import lombok.ToString;


/**
 *
 * 统一返回值
 * @param <T>
 */
@Data
@ToString
public class Result<T>  {

    private T data;
    private Integer code;
    private String messages;
    private String description;

    public Result(Integer code,T data, String messages, String description) {
        this.data = data;
        this.code = code;
        this.messages = messages;
        this.description = description;
    }

    public Result(Integer code, String messages, T data) {
        this(code,data,messages,"");
    }
}
