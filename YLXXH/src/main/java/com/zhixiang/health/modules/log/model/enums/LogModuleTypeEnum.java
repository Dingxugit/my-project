package com.zhixiang.health.modules.log.model.enums;

import com.zhixiang.health.common.model.enums.BaseEnum;

/**
 * @Description: 日志记录模块类型
 * @Auther: HeJiawang
 * @Date: 2020-06-24
 */
public enum LogModuleTypeEnum implements BaseEnum<Integer> {

    UNKNOWN(0, "UNKNOWN"),
    SYS(1, "系统配置模块"),
    LOG(2, "日志调阅模块");

    private int value;
    private String desc;

    LogModuleTypeEnum(int value, String desc) {
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
