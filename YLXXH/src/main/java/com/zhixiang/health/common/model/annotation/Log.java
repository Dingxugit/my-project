package com.zhixiang.health.common.model.annotation;

import com.zhixiang.health.modules.log.model.enums.LogEventTypeEnum;
import com.zhixiang.health.modules.log.model.enums.LogModuleTypeEnum;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 系统操作日志
 *
 * @author HeJiawang
 * @since 2020-07-07
 */
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface Log {

    /**
     * 日志信息
     * @return String
     */
    String value();

    /**
     * 操作类型 —— 增删改查
     * @return LogEventTypeEnum
     */
    LogEventTypeEnum eventType() default LogEventTypeEnum.UNKNOWN;

    /**
     * 业务模块
     * @return LogModuleTypeEnum
     */
    LogModuleTypeEnum moduleType() default LogModuleTypeEnum.UNKNOWN;
}
