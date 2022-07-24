package com.zhixiang.health.modules.log.api;

import com.zhixiang.health.common.http.result.HttpPageResult;
import com.zhixiang.health.modules.log.model.dto.LogLoginDto;
import com.zhixiang.health.modules.log.model.param.LogSearchParam;
import com.zhixiang.health.modules.log.service.ILogLoginService;
import org.springframework.web.bind.annotation.*;

import com.zhixiang.health.common.http.abs.BaseHttp;

import javax.annotation.Resource;
import javax.validation.Valid;

/**
 * <p>
 * 登录日志 前端控制器
 * </p>
 *
 * @author HeJiawang
 * @since 2020-07-08
 */
@RestController
@RequestMapping("/log/login")
public class LogLoginApi extends BaseHttp {

    @Resource
    private ILogLoginService service;

    /**
     * 获取分页信息
     * @param param param
     * @return 分页信息
     */
    @GetMapping(value = "/page")
    public HttpPageResult<LogLoginDto> page(@Valid LogSearchParam param) {
        return new HttpPageResult<>(service.page(param));
    }

}

