package com.ny.common;

import lombok.Data;

/**
 * @author njx
 * @date 2024/11/3
 * @Description:通用返回结果类，在这里一共处理了三个参数
 */
import lombok.Data;

@Data
public class R<T> {
    private boolean success;
    private String message;
    private T data;

    public R() {}

    public R(boolean success, String message, T data) {
        this.success = success;
        this.message = message;
        this.data = data;
    }

    public static <T> R<T> success(String message, T data) {
        return new R<>(true, message, data);
    }
    public static <T> R<T> success(String message) {
        return new R<>(true, message, null);
    }
    public static <T> R<T> failure(String message, T data) {
        return new R<>(false, message, data);
    }
    public static <T> R<T> failure(String message) {
        return new R<>(false, message, null);
    }
}