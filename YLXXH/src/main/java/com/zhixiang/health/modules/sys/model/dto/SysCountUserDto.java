package com.zhixiang.health.modules.sys.model.dto;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * 用户数量统计
 * @author HeJiawang
 * @since 2020-07-09
 */
@Data
@Accessors(chain = true)
public class SysCountUserDto {

    /**
     * 用户总数
     */
    private int total;

    /**
     * 启用数量
     */
    private int yesState;

    /**
     * 禁用数量
     */
    private int noState;

    /**
     * 今日新增用户
     */
    private int today;
}
