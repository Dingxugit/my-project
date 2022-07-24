package com.zhixiang.health.modules.sys.model.enums;

import com.zhixiang.health.common.model.enums.BaseEnum;

/**
 * @Description: 资源类型 1:System 2:Menu 3:Button
 * @Auther: HeJiawang
 * @Date: 2020-06-24
 */
public enum SysResourceTypeEnum implements BaseEnum<Integer> {

    SYSTEM(1, "SYSTEM"),
    MENU(2, "MENU"),
    BUTTON(3, "BUTTON");

    private int value;
    private String desc;

    SysResourceTypeEnum(int value, String desc) {
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
