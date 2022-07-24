package com.zhixiang.health.modules.sys.mapper;

import com.zhixiang.health.common.model.dto.ChartDto;
import com.zhixiang.health.modules.sys.model.dto.SysCountDepDto;
import com.zhixiang.health.modules.sys.model.dto.SysCountUserDto;

import java.util.List;

/**
 * <p>
 * 统计分析
 * </p>
 *
 * @author HeJiawang
 * @since 2020-07-09
 */
public interface SysCountMapper {

    /**
     * 统计用户数量
     * @return 用户数量信息
     */
    SysCountUserDto user();

    /**
     * 每个角色中有多少用户
     * @return 数量
     */
    List<ChartDto> roleUser();

    /**
     * 部门用户统计
     * @return 数量
     */
    List<SysCountDepDto> deptUser();
}
