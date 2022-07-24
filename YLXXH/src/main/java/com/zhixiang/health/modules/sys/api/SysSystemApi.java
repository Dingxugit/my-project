package com.zhixiang.health.modules.sys.api;

import com.zhixiang.health.common.http.result.HttpResult;
import com.zhixiang.health.common.model.annotation.Log;
import com.zhixiang.health.modules.log.model.enums.LogEventTypeEnum;
import com.zhixiang.health.modules.log.model.enums.LogModuleTypeEnum;
import com.zhixiang.health.modules.sys.model.dto.SysSystemDto;
import com.zhixiang.health.modules.sys.model.entity.SysSystem;
import com.zhixiang.health.modules.sys.model.enums.SysResourceTypeEnum;
import com.zhixiang.health.modules.sys.service.ISysResourceService;
import com.zhixiang.health.modules.sys.service.ISysSystemService;
import org.springframework.web.bind.annotation.*;

import com.zhixiang.health.common.http.abs.BaseHttp;

import javax.annotation.Resource;
import java.util.List;

/**
 * <p>
 * 系统配置 前端控制器
 * </p>
 *
 * @author HeJiawang
 * @since 2020-06-24
 */
@RestController
@RequestMapping("/sys/system")
public class SysSystemApi extends BaseHttp {

    @Resource
    private ISysSystemService service;

    @Resource
    private ISysResourceService sysResourceService;

    /**
     * 查询所有系统信息
     * @return list
     */
    @Log(value = "查看系统资源列表", eventType = LogEventTypeEnum.SELECT, moduleType = LogModuleTypeEnum.SYS)
    @GetMapping(value = "/listAll")
    public HttpResult<List<SysSystemDto>> listAll() {
        return new HttpResult<>(service.listAll());
    }

    /**
     * 保存实体信息
     * @param entity 实体信息
     * @return Boolean
     */
    @Log(value = "新增系统资源", eventType = LogEventTypeEnum.INSERT, moduleType = LogModuleTypeEnum.SYS)
    @PostMapping(value = "/save")
    public HttpResult<Boolean> save( @RequestBody SysSystem entity ){
        return new HttpResult<>(service.save(entity));
    }

    /**
     * 修改实体信息
     * @param entity 实体信息
     * @return Boolean
     */
    @Log(value = "修改系统资源", eventType = LogEventTypeEnum.UPDATE, moduleType = LogModuleTypeEnum.SYS)
    @PutMapping(value = "/update")
    public HttpResult<Boolean> updateById( @RequestBody SysSystem entity ){
        return new HttpResult<>(service.updateById(entity));
    }

    /**
    * 删除实体信息
    * @param id 实体ID
    * @return Boolean
    */
    @Log(value = "删除系统资源", eventType = LogEventTypeEnum.DELETE, moduleType = LogModuleTypeEnum.SYS)
    @DeleteMapping(value = "/remove/{id}")
    public HttpResult<Boolean> removeById( @PathVariable Integer id ){
        return new HttpResult<>(sysResourceService.removeByResourceId(SysResourceTypeEnum.SYSTEM, id));
    }

    /**
    * 查找实体信息
    * @param id 实体ID
    * @return HttpResult
    */
    @GetMapping(value = "/get/{id}")
    public HttpResult<SysSystem> getById(@PathVariable Integer id ){
        return new HttpResult<>(service.getById(id));
    }
}

