package com.zhixiang.health.common.model.result;

import com.zhixiang.health.common.http.result.HttpResult;
import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class ServiceResult<T> {

    private T result;
    private int code;
    private String message;
    private Boolean isSuccess = true;

    public ServiceResult() {}

    public ServiceResult(T t) {
        this.code = 200;
        this.isSuccess = true;
        this.result = t;
    }

    public ServiceResult(Throwable e) {
        this.code = 250;
        this.isSuccess = false;
        this.message = e.getMessage();
    }

    public ServiceResult<T> error (String message) {
        this.code = 404;
        this.isSuccess = false;
        this.message = message;
        return this;
    }

    public HttpResult<T> convert() {
        return new HttpResult<T>().setIsSuccess(this.isSuccess)
                .setCode(this.code)
                .setMessage(this.message)
                .setResult(this.result);
    }
}
