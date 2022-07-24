package com.zhixiang.health.modules.sys.api;

import com.zhixiang.health.common.http.abs.BaseHttp;
import com.zhixiang.health.common.http.result.HttpResult;
import com.zhixiang.health.common.model.dto.ChartDto;
import com.zhixiang.health.modules.sys.model.dto.SysCountDepDto;
import com.zhixiang.health.modules.sys.model.dto.SysCountUserDto;
import com.zhixiang.health.modules.sys.service.ISysCountService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
@RestController
@RequestMapping("/sys/count")
public class SysCountApi extends BaseHttp {

    @Resource
    private ISysCountService service;

    /**
     * 统计用户数量
     * @return 用户数量信息
     */
    @GetMapping(value = "/user")
    public HttpResult<SysCountUserDto> user() {
        return new HttpResult<>(service.user());
    }

    /**
     * 每个角色中有多少用户
     * @return 数量
     */
    @GetMapping(value = "/roleUser")
    public HttpResult<List<ChartDto>> roleUser() {
        return new HttpResult<>(service.roleUser());
    }

    /**
     * 部门用户统计
     * @return 数量
     */
    @GetMapping(value = "/deptUser")
    public HttpResult<List<SysCountDepDto>> deptUser() {
        return new HttpResult<>(service.deptUser());
    }
}
