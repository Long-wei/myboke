package com.boke.index.utils;


/**
 * 响应结果类
 * 2024-3-31
 */
public record Result (int code, Object data, String message){
    public static Result success(Object data){
        return new Result(200, data, null);
    }
    public static Result error(String message){
        return new Result(200, message, null);
    }
}
