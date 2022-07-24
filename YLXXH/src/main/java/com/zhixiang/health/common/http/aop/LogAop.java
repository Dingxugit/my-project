package com.zhixiang.health.common.http.aop;

import com.zhixiang.health.common.model.annotation.Log;
import com.zhixiang.health.common.utils.UserUtil;
import com.zhixiang.health.modules.log.model.entity.LogHandle;
import com.zhixiang.health.modules.log.service.ILogHandleService;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * log aop
 *
 * @author HeJiawang
 * @since 2020-07-07
 */
@Slf4j
@Aspect
@Component
public class LogAop {

    /**
     * 线程池
     */
    private static ExecutorService executorService =
            Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());

    @Resource
    private ILogHandleService logHandleService;

    /**
     * 拦截规则
     */
    @Pointcut("@annotation(com.zhixiang.health.common.model.annotation.Log)")
    public void pointCutR() {}

    /**
     * 拦截器具体实现
     * @param jp 切点
     */
    @Before("pointCutR()")
    public void methodRHandler(JoinPoint jp) {
        MethodSignature signature = (MethodSignature) jp.getSignature();
        Method method = signature.getMethod();
        Log logAnnotation = method.getAnnotation(Log.class);

        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();

        String userName = UserUtil.getUserName();

        executorService.execute( () -> {
            try {
                LogHandle logHandle = new LogHandle()
                        .setUserName(userName)
                        .setDetail(logAnnotation.value())
                        .setMethod(jp.getSignature().getDeclaringTypeName() + "." + jp.getSignature().getName())
                        .setUrl(request.getRequestURL().toString())
                        .setHttpType(request.getMethod())
                        .setArgs(Arrays.toString(jp.getArgs()))
                        .setEventType(logAnnotation.eventType())
                        .setModuleType(logAnnotation.moduleType());

                logHandleService.save(logHandle);
            } catch (Exception e) {

                log.error(this.getClass().getName() + "." + Thread.currentThread().getStackTrace()[1].getMethodName(), e);
            }
        });
    }

}
