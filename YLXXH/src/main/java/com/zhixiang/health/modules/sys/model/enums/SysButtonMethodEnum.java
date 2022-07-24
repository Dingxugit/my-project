package com.zhixiang.health.modules.sys.model.enums;

import com.zhixiang.health.common.model.enums.BaseEnum;

/**
 * @Description: 请求方法  1:Get，2:Post，3:Put，4:Delete
 * @Auther: HeJiawang
 * @Date: 2020-06-24
 */
public enum SysButtonMethodEnum implements BaseEnum<Integer> {

    GET(1, "GET"),
    POST(2, "POST"),
    PUT(3, "PUT"),
    DELETE(4, "DELETE");

    private int value;
    private String desc;

    SysButtonMethodEnum(int value, String desc) {
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
