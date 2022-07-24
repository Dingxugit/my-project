package com.zhixiang.health.modules.log.model.dto;

import com.zhixiang.health.common.model.dto.ChartDto;
import lombok.Data;
import lombok.experimental.Accessors;

import java.util.List;

/**
 * 操作次数统计
 * @author HeJiawang
 * @since 2020-07-09
 */
@Data
@Accessors(chain = true)
public class LogCountHandleDto {

    /**
     * 月统计
     */
    private List<ChartDto> monthList;

    /**
     * 排名统计
     */
    private List<ChartDto> rankList;
}
