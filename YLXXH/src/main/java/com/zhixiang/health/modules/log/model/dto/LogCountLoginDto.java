package com.zhixiang.health.modules.log.model.dto;

import com.zhixiang.health.common.model.dto.ChartDto;
import lombok.Data;
import lombok.experimental.Accessors;

import java.util.List;

/**
 * 登录次数统计
 * @author HeJiawang
 * @since 2020-07-09
 */
@Data
@Accessors(chain = true)
public class LogCountLoginDto {

    /**
     * 共登录次数
     */
    private int total;

    /**
     * 今日登录次数
     */
    private int today;

    /**
     * 登录成功次数
     */
    private int success;

    /**
     * 登录错误次数
     */
    private int error;

    /**
     * 每月登录次数
     */
    private List<ChartDto> list;
}
