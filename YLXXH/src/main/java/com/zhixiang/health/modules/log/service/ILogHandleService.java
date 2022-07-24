package com.zhixiang.health.modules.log.service;

import com.zhixiang.health.common.http.pagination.Page;
import com.zhixiang.health.modules.log.model.dto.LogHandleDto;
import com.zhixiang.health.modules.log.model.entity.LogHandle;
import com.zhixiang.health.modules.log.model.param.LogSearchParam;

/**
 * <p>
 * 操作日志信息 服务类
 * </p>
 *
 * @author HeJiawang
 * @since 2020-07-07
 */
public interface ILogHandleService {

    /**
     * 保存操作日志信息
     * @param logHandle 操作日志信息
     * @return boolean
     */
    Boolean save(LogHandle logHandle);

    /**
     * 获取分页信息
     * @param param param
     * @return 分页信息
     */
    Page<LogHandleDto> page(LogSearchParam param);
}
