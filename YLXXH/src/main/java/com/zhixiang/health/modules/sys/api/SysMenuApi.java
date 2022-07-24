package com.zhixiang.health.modules.sys.api;

import com.zhixiang.health.common.http.result.HttpResult;
import com.zhixiang.health.common.model.annotation.Log;
import com.zhixiang.health.modules.log.model.enums.LogEventTypeEnum;
import com.zhixiang.health.modules.log.model.enums.LogModuleTypeEnum;
import com.zhixiang.health.modules.sys.model.dto.SysMenuTreeDto;
import com.zhixiang.health.modules.sys.model.entity.SysMenu;
import com.zhixiang.health.modules.sys.model.enums.SysResourceTypeEnum;
import com.zhixiang.health.modules.sys.model.param.SysMenuSaveParam;
import com.zhixiang.health.modules.sys.service.ISysMenuService;
import com.zhixiang.health.modules.sys.service.ISysResourceService;
import org.springframework.web.bind.annotation.*;

import com.zhixiang.health.common.http.abs.BaseHttp;

import javax.annotation.Resource;
import java.util.List;

/**
 * <p>
 * 菜单配置 前端控制器
 * </p>
 *
 * @author HeJiawang
 * @since 2020-06-24
 */
@RestController
@RequestMapping("/sys/menu")
public class SysMenuApi extends BaseHttp {

    @Resource
    private ISysMenuService service;

    @Resource
    private ISysResourceService sysResourceService;

    /**
     * 获取菜单树
     * @param parentId 上级资源id（sys_resource.id）
     * @return 菜单树
     */
    @Log(value = "查看菜单资源列表", eventType = LogEventTypeEnum.SELECT, moduleType = LogModuleTypeEnum.SYS)
    @GetMapping(value = "/tree/{parentId}")
    public HttpResult<List<SysMenuTreeDto>> tree(@PathVariable Integer parentId){
        return new HttpResult<>(service.tree(parentId));
    }

    /**
     * 保存实体信息
     * @param saveParam 实体信息
     * @return Boolean
     */
    @Log(value = "新增菜单资源", eventType = LogEventTypeEnum.INSERT, moduleType = LogModuleTypeEnum.SYS)
    @PostMapping(value = "/save")
    public HttpResult<Boolean> save( @RequestBody SysMenuSaveParam saveParam ){
        return new HttpResult<>(service.save(saveParam));
    }

    /**
     * 修改实体信息
     * @param param 实体信息
     * @return Boolean
     */
    @Log(value = "修改菜单资源", eventType = LogEventTypeEnum.UPDATE, moduleType = LogModuleTypeEnum.SYS)
    @PutMapping(value = "/update")
    public HttpResult<Boolean> updateById( @RequestBody SysMenuSaveParam param ){
        return new HttpResult<>(service.updateById(param));
    }

    /**
    * 删除实体信息
    * @param id 实体ID
    * @return Boolean
    */
    @Log(value = "删除菜单资源", eventType = LogEventTypeEnum.DELETE, moduleType = LogModuleTypeEnum.SYS)
    @DeleteMapping(value = "/remove/{id}")
    public HttpResult<Boolean> removeById( @PathVariable Integer id ){
        return new HttpResult<>(sysResourceService.removeByResourceId(SysResourceTypeEnum.MENU, id));
    }

    /**
    * 查找实体信息
    * @param id 实体ID
    * @return HttpResult
    */
    @GetMapping(value = "/get/{id}")
    public HttpResult<SysMenu> getById(@PathVariable Integer id ){
        return new HttpResult<>(service.getById(id));
    }
}

