package com.zhixiang.health.modules.log.api;

import com.zhixiang.health.common.http.abs.BaseHttp;
import com.zhixiang.health.common.http.result.HttpPageResult;
import com.zhixiang.health.modules.log.model.dto.LogHandleDto;
import com.zhixiang.health.modules.log.model.param.LogSearchParam;
import com.zhixiang.health.modules.log.service.ILogHandleService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * <p>
 * 操作日志信息
 * </p>
 *
 * @author HeJiawang
 * @since 2020-07-07
 */
@RestController
@RequestMapping("/log/handle")
public class LogHandleApi extends BaseHttp {

    @Resource
    private ILogHandleService service;

    /**
     * 获取分页信息
     * @param param param
     * @return 分页信息
     */
    @GetMapping(value = "/page")
    public HttpPageResult<LogHandleDto> page(LogSearchParam param) {
        return new HttpPageResult<>(service.page(param));
    }
}
