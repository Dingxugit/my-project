package com.zhixiang.health.modules.log.mapper;

import com.zhixiang.health.modules.log.model.dto.LogLoginDto;
import com.zhixiang.health.modules.log.model.entity.LogLogin;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zhixiang.health.modules.log.model.param.LogSearchParam;

import java.util.List;

/**
 * <p>
 * 登录日志 Mapper 接口
 * </p>
 *
 * @author HeJiawang
 * @since 2020-07-08
 */
public interface LogLoginMapper extends BaseMapper<LogLogin> {

    /**
     * 获取分页信息
     * @param param param
     * @return 分页信息
     */
    List<LogLoginDto> pageList(LogSearchParam param);

    /**
     * 获取分页信息
     * @param param param
     * @return 分页信息
     */
    long pageTotal(LogSearchParam param);
}
