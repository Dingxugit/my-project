package com.zhixiang.health.common.model.enums;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.enums.IEnum;

import java.io.Serializable;

/**
 * @Description: 基础枚举
 * @Auther: HeJiawang
 * @Date: 2020-04-20
 */
public interface BaseEnum<T extends Serializable> extends IEnum<T> {

    /**
     * 返回描述信息
     * @return 描述信息
     */
    default String getDesc() {
        return StrUtil.EMPTY;
    }
}
