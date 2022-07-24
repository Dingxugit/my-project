package com.zhixiang.health.modules.sys.model.enums;

import com.zhixiang.health.common.model.enums.BaseEnum;

/**
 * @Description: 页面展示方式  0 HOME  1 SCREEN
 * @Auther: HeJiawang
 * @Date: 2020-07-02
 */
public enum SysMenuShowTypeEnum implements BaseEnum<Integer> {

    HOME(0, "HOME"),
    SCREEN(1, "SCREEN");

    private int value;
    private String desc;

    SysMenuShowTypeEnum(int value, String desc) {
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
