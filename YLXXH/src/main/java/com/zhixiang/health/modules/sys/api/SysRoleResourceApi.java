package com.zhixiang.health.modules.sys.api;

import com.zhixiang.health.common.http.result.HttpResult;
import com.zhixiang.health.common.model.annotation.Log;
import com.zhixiang.health.modules.log.model.enums.LogEventTypeEnum;
import com.zhixiang.health.modules.log.model.enums.LogModuleTypeEnum;
import com.zhixiang.health.modules.sys.service.ISysRoleResourceService;
import org.springframework.web.bind.annotation.*;

import com.zhixiang.health.common.http.abs.BaseHttp;

import javax.annotation.Resource;
import java.util.List;

/**
 * <p>
 * 角色权限表 前端控制器
 * </p>
 *
 * @author HeJiawang
 * @since 2020-06-24
 */
@RestController
@RequestMapping("/sys/role/resource")
public class SysRoleResourceApi extends BaseHttp {

    @Resource
    private ISysRoleResourceService service;

    /**
     * 为角色授权
     * @param roleId 角色id
     * @param rIds 资源id集合
     * @return Boolean
     */
    @Log(value = "为角色授权", eventType = LogEventTypeEnum.UPDATE, moduleType = LogModuleTypeEnum.SYS)
    @GetMapping(value = "/auth")
    public HttpResult<Boolean> auth(Integer roleId,
                                    @RequestParam(value = "rIds", required=false ) List<Integer> rIds){
        return new HttpResult<>(service.auth(roleId, rIds));
    }

    /**
     * 获取角色以授权的资源id
     * @param roleId 角色id
     * @return 资源id
     */
    @GetMapping(value = "/findRidByRole/{roleId}")
    public HttpResult<List<Integer>> findRidByRole(@PathVariable Integer roleId ){
        return new HttpResult<>(service.findRidByRole(roleId));
    }

}

