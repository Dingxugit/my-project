package com.zhixiang.health.modules.log.model.enums;

import com.zhixiang.health.common.model.enums.BaseEnum;

/**
 * @Description: 登录日志
 * @Auther: HeJiawang
 * @Date: 2020-07-08
 */
public enum LogLoginResultEnum implements BaseEnum<Integer> {

    ERROR(0, "失败"),
    SUCCESS(1, "成功");

    private int value;
    private String desc;

    LogLoginResultEnum(int value, String desc) {
        this.value = value;
        this.desc = desc;
    }

    @Override
    public Integer getValue() {
        return this.value;
    }

    @Override
    public String getDesc() {
        return this.desc;
    }
}
