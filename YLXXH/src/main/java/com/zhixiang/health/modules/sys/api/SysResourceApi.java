package com.zhixiang.health.modules.sys.api;

import com.zhixiang.health.common.http.result.HttpResult;
import com.zhixiang.health.common.model.param.UserParam;
import com.zhixiang.health.modules.sys.model.dto.SysLoginResourceDto;
import com.zhixiang.health.modules.sys.model.dto.SysResourceDto;
import com.zhixiang.health.modules.sys.model.dto.SysRouteDto;
import com.zhixiang.health.modules.sys.model.enums.SysResourceTypeEnum;
import com.zhixiang.health.modules.sys.service.ISysResourceService;
import org.springframework.web.bind.annotation.*;

import com.zhixiang.health.common.http.abs.BaseHttp;

import javax.annotation.Resource;
import java.util.List;

/**
 * <p>
 * 系统资源表 前端控制器
 * </p>
 *
 * @author HeJiawang
 * @since 2020-06-24
 */
@RestController
@RequestMapping("/sys/resource")
public class SysResourceApi extends BaseHttp {

    @Resource
    private ISysResourceService service;

    /**
     * 获取type值以上的资源信息
     * @param type type
     * @return list
     */
    @GetMapping(value = "/list")
    public HttpResult<List<SysResourceDto>> list (SysResourceTypeEnum type) {
        return new HttpResult<>(service.list(type));
    }

    /**
     * 获取前端路由信息
     * @return 路由信息
     */
    @GetMapping(value = "/routeList")
    public HttpResult<SysRouteDto> routeList() {
        return new HttpResult<>(service.routeList());
    }

    /**
     * 获取当前登录人的资源信息
     * @param param param
     * @return 资源信息
     */
    @GetMapping(value = "/loginResource")
    public HttpResult<SysLoginResourceDto> loginResource(UserParam param) {
        return new HttpResult<>(service.loginResource(param.getId()));
    }
}

