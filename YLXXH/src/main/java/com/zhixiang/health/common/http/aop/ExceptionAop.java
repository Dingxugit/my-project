package com.zhixiang.health.common.http.aop;

import com.zhixiang.health.common.http.result.HttpResult;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 全局异常处理
 *
 * @author HeJiawang
 * @since 2020-07-08
 */
@ControllerAdvice
public class ExceptionAop {

    /**
     * 全局异常处理
     * @param e exception
     * @return HttpResult
     */
    @ExceptionHandler(Exception.class)
    @ResponseBody
    public HttpResult<Void> handler(Exception e) {
        HttpResult<Void> result = new HttpResult(e);

        if (e instanceof BindException) {   // 处理参数校验异常
            BindException ve = (BindException) e;
            BindingResult bindingResult = ve.getBindingResult();
            String message = bindingResult.getFieldError().getDefaultMessage();

            result.setMessage(message);
        }

        return result;
    }
}
