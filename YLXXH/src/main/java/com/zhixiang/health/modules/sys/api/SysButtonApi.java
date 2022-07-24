package com.zhixiang.health.modules.sys.api;

import com.zhixiang.health.common.http.result.HttpResult;
import com.zhixiang.health.common.model.annotation.Log;
import com.zhixiang.health.modules.log.model.enums.LogEventTypeEnum;
import com.zhixiang.health.modules.log.model.enums.LogModuleTypeEnum;
import com.zhixiang.health.modules.sys.model.dto.SysButtonDto;
import com.zhixiang.health.modules.sys.model.entity.SysButton;
import com.zhixiang.health.modules.sys.model.enums.SysResourceTypeEnum;
import com.zhixiang.health.modules.sys.model.param.SysButtonSaveParam;
import com.zhixiang.health.modules.sys.service.ISysButtonService;
import com.zhixiang.health.modules.sys.service.ISysResourceService;
import org.springframework.web.bind.annotation.*;

import com.zhixiang.health.common.http.abs.BaseHttp;

import javax.annotation.Resource;
import java.util.List;

/**
 * <p>
 * 按钮及访问权限配置 前端控制器
 * </p>
 *
 * @author HeJiawang
 * @since 2020-06-24
 */
@RestController
@RequestMapping("/sys/button")
public class SysButtonApi extends BaseHttp {

    @Resource
    private ISysButtonService service;

    @Resource
    private ISysResourceService sysResourceService;

    /**
     * 根据上级资源id，获取按钮权限信息
     * @param parentId 上级资源id
     * @return 按钮权限信息
     */
    @Log(value = "查看按钮资源列表", eventType = LogEventTypeEnum.SELECT, moduleType = LogModuleTypeEnum.SYS)
    @GetMapping(value = "/listByParentId/{parentId}")
    public HttpResult<List<SysButtonDto>> listByParentId(@PathVariable Integer parentId) {
        return new HttpResult<>(service.listByParentId(parentId));
    }

    /**
     * 保存实体信息
     * @param saveParam 实体信息
     * @return Boolean
     */
    @Log(value = "新增按钮资源", eventType = LogEventTypeEnum.INSERT, moduleType = LogModuleTypeEnum.SYS)
    @PostMapping(value = "/save")
    public HttpResult<Boolean> save( @RequestBody SysButtonSaveParam saveParam ){
        return new HttpResult<>(service.save(saveParam));
    }

    /**
     * 修改实体信息
     * @param param 实体信息
     * @return Boolean
     */
    @Log(value = "修改按钮资源", eventType = LogEventTypeEnum.UPDATE, moduleType = LogModuleTypeEnum.SYS)
    @PutMapping(value = "/update")
    public HttpResult<Boolean> updateById( @RequestBody SysButtonSaveParam param ){
        return new HttpResult<>(service.updateById(param));
    }

    /**
    * 删除实体信息
    * @param id 实体ID
    * @return Boolean
    */
    @Log(value = "删除按钮资源", eventType = LogEventTypeEnum.DELETE, moduleType = LogModuleTypeEnum.SYS)
    @DeleteMapping(value = "/remove/{id}")
    public HttpResult<Boolean> removeById( @PathVariable Integer id ){
        return new HttpResult<>(sysResourceService.removeByResourceId(SysResourceTypeEnum.BUTTON, id));
    }

    /**
    * 查找实体信息
    * @param id 实体ID
    * @return HttpResult
    */
    @GetMapping(value = "/get/{id}")
    public HttpResult<SysButton> getById(@PathVariable Integer id ){
        return new HttpResult<>(service.getById(id));
    }
}

