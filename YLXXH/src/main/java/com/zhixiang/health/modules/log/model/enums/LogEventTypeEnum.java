package com.zhixiang.health.modules.log.model.enums;

import com.zhixiang.health.common.model.enums.BaseEnum;

/**
 * @Description: 日志操作事件类型
 * @Auther: HeJiawang
 * @Date: 2020-06-24
 */
public enum LogEventTypeEnum implements BaseEnum<Integer> {

    UNKNOWN(0, "综合操作"),
    SELECT(1, "查找操作"),
    DELETE(2, "删除操作"),
    UPDATE(3, "修改操作"),
    INSERT(4, "新增操作");

    private int value;
    private String desc;

    LogEventTypeEnum(int value, String desc) {
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
