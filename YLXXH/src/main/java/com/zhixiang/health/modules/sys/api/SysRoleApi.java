package com.zhixiang.health.modules.sys.api;

import com.zhixiang.health.common.http.result.HttpResult;
import com.zhixiang.health.common.model.annotation.Log;
import com.zhixiang.health.modules.log.model.enums.LogEventTypeEnum;
import com.zhixiang.health.modules.log.model.enums.LogModuleTypeEnum;
import com.zhixiang.health.modules.sys.model.entity.SysRole;
import com.zhixiang.health.modules.sys.service.ISysRoleService;
import org.springframework.web.bind.annotation.*;

import com.zhixiang.health.common.http.abs.BaseHttp;

import javax.annotation.Resource;
import java.util.List;

/**
 * <p>
 * 系统角色信息表 前端控制器
 * </p>
 *
 * @author HeJiawang
 * @since 2020-04-21
 */
@RestController
@RequestMapping("/sys/role")
public class SysRoleApi extends BaseHttp {

    @Resource
    private ISysRoleService service;

    /**
     * 获取所有角色信息
     * @return list
     */
    @Log(value = "查看角色列表", eventType = LogEventTypeEnum.SELECT, moduleType = LogModuleTypeEnum.SYS)
    @GetMapping(value = "/list")
    public HttpResult<List<SysRole>> list() {
        return new HttpResult<>(service.list());
    }

    /**
     * 保存实体信息
     * @param entity 实体信息
     * @return Boolean
     */
    @Log(value = "新增角色信息", eventType = LogEventTypeEnum.INSERT, moduleType = LogModuleTypeEnum.SYS)
    @PostMapping(value = "/save")
    public HttpResult<Boolean> save( @RequestBody SysRole entity ){
        return new HttpResult<>(service.save(entity));
    }

    /**
     * 修改实体信息
     * @param entity 实体信息
     * @return Boolean
     */
    @Log(value = "修改角色信息", eventType = LogEventTypeEnum.UPDATE, moduleType = LogModuleTypeEnum.SYS)
    @PutMapping(value = "/update")
    public HttpResult<Boolean> update( @RequestBody SysRole entity ){
        return new HttpResult<>(service.updateById(entity));
    }

    /**
    * 删除实体信息
    * @param id 实体ID
    * @return Boolean
    */
    @Log(value = "删除角色信息", eventType = LogEventTypeEnum.DELETE, moduleType = LogModuleTypeEnum.SYS)
    @DeleteMapping(value = "/remove/{id}")
    public HttpResult<Boolean> remove( @PathVariable Integer id ){
        return new HttpResult<>(service.removeById(id));
    }

    /**
    * 查找实体信息
    * @param id 实体ID
    * @return HttpResult
    */
    @GetMapping(value = "/get/{id}")
    public HttpResult<SysRole> getById(@PathVariable Integer id ){
        return new HttpResult<>(service.getById(id));
    }

    /**
     * 校验角色编码是否可用
     * @param role 角色信息
     * @return true 编码可用
     */
    @PostMapping(value = "/checkCode")
    public HttpResult<Boolean> checkCode(@RequestBody SysRole role) {
        return new HttpResult<>(service.checkCode(role));
    }
}

