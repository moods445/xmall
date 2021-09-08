package com.modds.xmall.common.bean;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class Result<T> implements Serializable {

    private static final long serialVersionUID = -3266931205943696705L;
    /**
     * 返回数据
     */
    private T data;
    /**
     * 错误码
     */
    private int code = 0;


    private String message = "success";

    public static <T> Result<T> success() {
        return new Result<T>();
    }


    public static <T> Result<T> success(T data) {
        Result<T> result = new Result<T>();
        result.data = data;
        return result;
    }


    public static <T> Result<T> error(int code, String message) {
        Result<T> result = new Result<T>();
        result.setMessage(message);
        result.setCode(code);
        return result;
    }

    public static <T> Result<T> error(int code, String message, T data) {
        Result<T> result = new Result<T>();
        result.setMessage(message);
        result.setCode(code);
        result.setData(data);
        return result;
    }

    public enum ErrorCode {
        ;

        private int code;
        private String msg;

        public int getCode() {
            return code;
        }

        public String getMsg() {
            return msg;
        }
    }
}
