package com.zhixiang.health.modules.log.api;

import com.zhixiang.health.common.http.abs.BaseHttp;
import com.zhixiang.health.common.http.result.HttpResult;
import com.zhixiang.health.modules.log.model.dto.LogCountHandleDto;
import com.zhixiang.health.modules.log.model.dto.LogCountLoginDto;
import com.zhixiang.health.modules.log.service.ILogCountService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * <p>
 * 统计分析
 * </p>
 *
 * @author HeJiawang
 * @since 2020-07-09
 */
@RestController
@RequestMapping("/log/count")
public class LogCountApi extends BaseHttp {

    @Resource
    private ILogCountService service;

    /**
     * 登录次数统计
     * @return 登录次数
     */
    @GetMapping(value = "/login")
    public HttpResult<LogCountLoginDto> login() {
        return new HttpResult<>(service.login());
    }

    /**
     * 操作日志统计
     * @return 统计数量
     */
    @GetMapping(value = "/handle/{year}")
    public HttpResult<LogCountHandleDto> handle(@PathVariable Integer year) {
        return new HttpResult<>(service.handle(year));
    }
}
