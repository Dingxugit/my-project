package com.zhixiang.health.common.model.enums;

/**
 * @Description: 启用状态 1启用 0禁用
 * @Auther: HeJiawang
 * @Date: 2020-04-20
 */
public enum UsedStateEnum implements BaseEnum<Integer> {

    YES(1, "启用"),
    NO(0, "停用");

    private int value;
    private String desc;

    UsedStateEnum(int value, String desc) {
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
