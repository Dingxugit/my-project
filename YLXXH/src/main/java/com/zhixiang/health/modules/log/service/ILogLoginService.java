package com.zhixiang.health.modules.log.service;

import com.zhixiang.health.common.http.pagination.Page;
import com.zhixiang.health.modules.log.model.dto.LogLoginDto;
import com.zhixiang.health.modules.log.model.entity.LogLogin;
import com.baomidou.mybatisplus.extension.service.IService;
import com.zhixiang.health.modules.log.model.param.LogSearchParam;

/**
 * <p>
 * 登录日志 服务类
 * </p>
 *
 * @author HeJiawang
 * @since 2020-07-08
 */
public interface ILogLoginService extends IService<LogLogin> {

    /**
     * 获取分页信息
     * @param param param
     * @return 分页信息
     */
    Page<LogLoginDto> page(LogSearchParam param);
}
