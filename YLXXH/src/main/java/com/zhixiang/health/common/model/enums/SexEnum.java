package com.zhixiang.health.common.model.enums;

/**
 * @Description: 性别枚举
 * @Auther: HeJiawang
 * @Date: 2020-04-20
 */
public enum SexEnum implements BaseEnum<Integer> {

    MAN(1, "男"),
    WOMAN(0, "女");

    private int value;
    private String desc;

    SexEnum(int value, String desc) {
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
