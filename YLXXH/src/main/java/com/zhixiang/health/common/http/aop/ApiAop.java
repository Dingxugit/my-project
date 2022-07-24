package com.zhixiang.health.common.http.aop;

import cn.hutool.core.util.StrUtil;
import com.zhixiang.health.common.http.result.HttpResult;
import com.zhixiang.health.common.utils.RedisUtil;
import com.zhixiang.health.common.utils.UserUtil;
import com.zhixiang.health.modules.sys.model.enums.SysButtonMethodEnum;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

/**
 * aop
 *
 * @author HeJiawang
 * @since 2020-05-29
 */
@Slf4j
@Aspect
@Component
public class ApiAop {

    @Resource
    private RedisUtil redisUtil;

    /**
     * 拦截规则
     */
    @Pointcut("execution(public com.zhixiang.health.common.http.result.* *(..))")
    public void pointCutR() {}

    /**
     * 拦截器具体实现
     * @param pjp 切点 所有返回对象R
     * @return R  结果包装
     */
    @Around("pointCutR()")
    public Object methodRHandler(ProceedingJoinPoint pjp) {
        return methodHandler(pjp);
    }

    /**
     * 拦截方法
     *      1、设置 UserUtil 中的值
     *      2、捕获异常
     *
     * @param pjp pjp
     * @return Object
     */
    private Object methodHandler(ProceedingJoinPoint pjp) {
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();

        // 拦截重复提交，不校验GET请求
        String submitKey = request.getRequestURI(), token = UserUtil.getToken(request);
        if (StrUtil.isNotEmpty(token) && ! request.getMethod().equalsIgnoreCase(SysButtonMethodEnum.GET.getDesc())) {
            submitKey = submitKey + "_" + token;

            if (null != redisUtil.get(submitKey)) {
                return new HttpResult<>().repeat();
            } else {
                redisUtil.set(submitKey, "submit", 60); // 如果60秒内没有处理完接口业务，请勿重复提交
            }
        }

        UserUtil.setUserName(UserUtil.getUserName(request));
        UserUtil.setRoleCodeList(UserUtil.getRoleCodeList(request));

        Object result;

        try {

            result = pjp.proceed();
        } catch (Throwable e) {

            result = new HttpResult<>(e);
            log.error(this.getClass().getName() + "." + Thread.currentThread().getStackTrace()[1].getMethodName(), e);
        } finally {

            UserUtil.clearRoleCodeList();
            UserUtil.clearUserName();

            // 清除表单重复提交标志
            redisUtil.del(submitKey);
        }

        return result;
    }
}
