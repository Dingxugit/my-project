package com.zhixiang.health.common.model.dto;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * 前端显示图表的数据结构
 *
 * @author HeJiawang
 * @since 2020-07-09
 */
@Data
@Accessors(chain = true)
public class ChartDto {

    private String name;

    private double value;
}
