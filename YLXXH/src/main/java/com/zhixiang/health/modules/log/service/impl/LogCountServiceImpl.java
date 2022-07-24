package com.zhixiang.health.modules.log.service.impl;

import com.zhixiang.health.modules.log.mapper.LogCountMapper;
import com.zhixiang.health.modules.log.model.dto.LogCountHandleDto;
import com.zhixiang.health.modules.log.model.dto.LogCountLoginDto;
import com.zhixiang.health.modules.log.service.ILogCountService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * <p>
 * 统计分析
 * </p>
 *
 * @author HeJiawang
 * @since 2020-07-09
 */
@Service
public class LogCountServiceImpl implements ILogCountService {

    @Resource
    private LogCountMapper mapper;

    /**
     * 登录次数统计
     * @return 登录次数
     */
    @Override
    public LogCountLoginDto login() {
        LogCountLoginDto result = mapper.login()
                .setList(mapper.loginList());

        return result;
    }

    /**
     * 操作日志统计
     * @return 统计数量
     */
    @Override
    public LogCountHandleDto handle(Integer year) {
        LogCountHandleDto result = new LogCountHandleDto()
                .setRankList(mapper.handleRank(year))
                .setMonthList(mapper.handleMonth(year));

        return result;
    }
}
