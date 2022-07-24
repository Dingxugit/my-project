package com.zhixiang.health.modules.log.service.impl;

import com.zhixiang.health.common.http.pagination.Page;
import com.zhixiang.health.modules.log.model.dto.LogLoginDto;
import com.zhixiang.health.modules.log.model.entity.LogLogin;
import com.zhixiang.health.modules.log.mapper.LogLoginMapper;
import com.zhixiang.health.modules.log.model.param.LogSearchParam;
import com.zhixiang.health.modules.log.service.ILogLoginService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * <p>
 * 登录日志 服务实现类
 * </p>
 *
 * @author HeJiawang
 * @since 2020-07-08
 */
@Service
public class LogLoginServiceImpl extends ServiceImpl<LogLoginMapper, LogLogin> implements ILogLoginService {

    @Resource
    private LogLoginMapper mapper;

    /**
     * 获取分页信息
     * @param param param
     * @return 分页信息
     */
    @Override
    public Page<LogLoginDto> page(LogSearchParam param) {
        param.renderLimitStart();

        Page<LogLoginDto> pageResult = new Page<>();
        pageResult.setRecords(mapper.pageList(param))
                .setTotal(mapper.pageTotal(param));

        return pageResult;
    }
}
