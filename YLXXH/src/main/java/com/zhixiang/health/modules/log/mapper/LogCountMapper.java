package com.zhixiang.health.modules.log.mapper;

import com.zhixiang.health.common.model.dto.ChartDto;
import com.zhixiang.health.modules.log.model.dto.LogCountLoginDto;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 统计分析
 * </p>
 *
 * @author HeJiawang
 * @since 2020-07-09
 */
public interface LogCountMapper {

    /**
     * 登录次数统计
     * @return 登录次数
     */
    LogCountLoginDto login();

    /**
     * 每月登录次数
     * @return 登录次数
     */
    List<ChartDto> loginList();

    /**
     * 操作日志统计
     * @param year 年份
     * @return 月操作量
     */
    List<ChartDto> handleMonth(@Param("year") Integer year);

    /**
     * 操作排名统计
     * @param year 年份
     * @return 用户操作数
     */
    List<ChartDto> handleRank(@Param("year") Integer year);
}
