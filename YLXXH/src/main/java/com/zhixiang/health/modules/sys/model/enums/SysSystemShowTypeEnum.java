package com.zhixiang.health.modules.sys.model.enums;

import com.zhixiang.health.common.model.enums.BaseEnum;

/**
 * @Description: 进入系统方式，0 _SELF，1 _BLANK
 * @Auther: HeJiawang
 * @Date: 2020-07-02
 */
public enum SysSystemShowTypeEnum implements BaseEnum<Integer> {

    SELF(0, "SELF"),
    BLANK(1, "BLANK");

    private int value;
    private String desc;

    SysSystemShowTypeEnum(int value, String desc) {
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
