package com.zhixiang.health.common.model.enums;

/**
 * @Description: 使用状态枚举
 * @Auther: HeJiawang
 * @Date: 2020-04-20
 */
public enum DeleteStateEnum implements BaseEnum<Integer> {

    YES(1, "已删除"),
    NO(0, "未删除");

    private int value;
    private String desc;

    DeleteStateEnum(int value, String desc) {
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
