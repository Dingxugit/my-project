package com.zhixiang.health.modules.sys.model.enums;

import com.zhixiang.health.common.model.enums.BaseEnum;

/**
 * @Description: 是否是主页，1是，0否
 * @Auther: HeJiawang
 * @Date: 2020-06-24
 */
public enum SysMenuIndexEnum implements BaseEnum<Integer> {

    YES(1, "是主页"),
    NO(0, "非主页");

    private int value;
    private String desc;

    SysMenuIndexEnum(int value, String desc) {
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
