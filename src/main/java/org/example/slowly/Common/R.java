package org.example.slowly.Common;

/**
 * @author njx
 * @date 2024/11/8
 * @Description 通用返回类
 */
public class R<T> {
    private Boolean success;
    private T data;
    private String msg;

    // 构造函数
    public R(Boolean success, T data, String msg) {
        this.success = success;
        this.data = data;
        this.msg = msg;
    }

    // 默认成功的静态方法，无数据返回
    public static <T> R<T> succeed() {
        return new R<>(true, null, "操作成功");
    }

    // 成功的静态方法，返回数据
    public static <T> R<T> succeed(T object) {
        return new R<>(true, object, "操作成功");
    }

    // 成功的静态方法，带自定义消息
    public static <T> R<T> succeed(T object, String msg) {
        return new R<>(true, object, msg);
    }

    // 失败的静态方法
    public static <T> R<T> failed(String msg) {
        return new R<>(false, null, msg);
    }

    public Boolean getSuccess() {
        return success;
    }

    public void setSuccess(Boolean success) {
        this.success = success;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    @Override
    public String toString() {
        return "R{" +
                "success=" + success +
                ", data=" + data +
                ", msg='" + msg + '\'' +
                '}';
    }
}
