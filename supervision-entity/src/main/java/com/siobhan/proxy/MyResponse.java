package com.siobhan.proxy;

/**
 * Created by siobhan.zheng on 2019/3/27
 * @author zheng
 * 自封装Response对象
 */
public class MyResponse<T> {
    // 逻辑状态码
   private String code;
   // 错误信息
    private String message;
    // dto、vo
    private T body;

    /**
     * 该构造方法默认code 为200
     */
    public MyResponse() {
        this("200", null);
    }
    public MyResponse(T body) {
        this("200", body);
    }
    public MyResponse(String code, T body) {
        this(code, null, body);
    }
    public MyResponse(String code, String message, T body) {
        this.code = code;
        this.body = body;
        this.message = message;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public T getBody() {
        return body;
    }

    public void setBody(T body) {
        this.body = body;
    }
}
