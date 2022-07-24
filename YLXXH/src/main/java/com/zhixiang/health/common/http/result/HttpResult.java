package com.zhixiang.health.common.http.result;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * @Description: HTTP控制器返回结果对象
 * @Auther: HeJiawang
 * @Date: 2020-04-20
 */
@Data
@Accessors(chain = true)
public class HttpResult<T>  {

    private T result;
    private int code;
    private String message;
    private Boolean isSuccess = true;

    public HttpResult() {}

    public HttpResult(T t) {
        this.code = 200;
        this.isSuccess = true;
        this.result = t;
    }

    public HttpResult(Throwable e) {
        this.code = 250;
        this.isSuccess = false;
        this.message = e.getMessage();
    }

    public HttpResult<T> error () {
        this.code = 404;
        this.isSuccess = false;
        return this;
    }

    public HttpResult<T> repeat () {
        this.code = 420;
        this.isSuccess = false;
        this.message = "请勿重复提交";
        return this;
    }

}
