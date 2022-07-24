package com.zhixiang.health.common.utils.poi;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @Description: poi 导出Excel字段注解
 * @Auther: HeJiawang
 * @Date: 2019/3/17
 */
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface ExcelField {

    /**
     * 导出字段名
     */
    String value() default "";

    /**
     * 导出字段标题
     */
    String title();

    /**
     * 导出字段对齐方式（0：自动；1：靠左；2：居中；3：靠右）
     */
    int align() default 0;

    /**
     * 反射类型
     */
    Class<?> fieldType() default Class.class;
}
