package com.zhixiang.health.modules.sys.service.impl;

import com.zhixiang.health.common.model.dto.ChartDto;
import com.zhixiang.health.modules.sys.mapper.SysCountMapper;
import com.zhixiang.health.modules.sys.model.dto.SysCountDepDto;
import com.zhixiang.health.modules.sys.model.dto.SysCountUserDto;
import com.zhixiang.health.modules.sys.service.ISysCountService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * <p>
 * 统计分析
 * </p>
 *
 * @author HeJiawang
 * @since 2020-07-09
 */
@Service
public class SysCountServiceImpl implements ISysCountService {

    @Resource
    private SysCountMapper mapper;

    /**
     * 统计用户数量
     * @return 用户数量信息
     */
    @Override
    public SysCountUserDto user() {
        return mapper.user();
    }

    /**
     * 每个角色中有多少用户
     * @return 数量
     */
    @Override
    public List<ChartDto> roleUser() {
        return mapper.roleUser();
    }

    /**
     * 部门用户统计
     * @return 数量
     */
    @Override
    public List<SysCountDepDto> deptUser() {
        return mapper.deptUser();
    }
}
