package com.zhixiang.health.modules.sys.model.enums;

import com.zhixiang.health.common.model.enums.BaseEnum;

/**
 * @Description: 是否是主页，1是，0否
 * @Auther: HeJiawang
 * @Date: 2020-06-24
 */
public enum SysSystemGuideEnum implements BaseEnum<Integer> {

    YES(1, "是引导页"),
    NO(0, "非引导页");

    private int value;
    private String desc;

    SysSystemGuideEnum(int value, String desc) {
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
