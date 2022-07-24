package com.zhixiang.health.modules.sys.api;

import com.zhixiang.health.common.http.pagination.Page;
import com.zhixiang.health.common.http.result.HttpPageResult;
import com.zhixiang.health.common.http.result.HttpResult;
import com.zhixiang.health.common.model.annotation.Log;
import com.zhixiang.health.modules.log.model.enums.LogEventTypeEnum;
import com.zhixiang.health.modules.log.model.enums.LogModuleTypeEnum;
import com.zhixiang.health.modules.sys.model.dto.SysDictTreeDto;
import com.zhixiang.health.modules.sys.model.entity.SysDict;
import com.zhixiang.health.modules.sys.service.ISysDictService;
import org.springframework.web.bind.annotation.*;

import com.zhixiang.health.common.http.abs.BaseHttp;

import javax.annotation.Resource;
import java.util.List;

/**
 * <p>
 * 字典表 前端控制器
 * </p>
 *
 * @author HeJiawang
 * @since 2020-04-30
 */
@RestController
@RequestMapping("/sys/dict")
public class SysDictApi extends BaseHttp {

    @Resource
    private ISysDictService service;

    /**
     * tree page
     * @param page page
     * @return SysDictTreeDto Page
     */
    @GetMapping(value = "/tree")
    public HttpPageResult<SysDictTreeDto> tree(Page<SysDictTreeDto> page) {
        return new HttpPageResult<>(service.tree(page));
    }

    /**
     * 获取所有字典信息
     * @return 字典list
     */
    @GetMapping(value = "/list")
    public HttpResult<List<SysDict>> list() {
        return new HttpResult<>(service.list());
    }

    /**
     * 保存实体信息
     * @param entity 实体信息
     * @return Boolean
     */
    @Log(value = "新增字典信息", eventType = LogEventTypeEnum.INSERT, moduleType = LogModuleTypeEnum.SYS)
    @PostMapping(value = "/save")
    public HttpResult<Boolean> save( @RequestBody SysDict entity ){
        return new HttpResult<>(service.save(entity));
    }

    /**
     * 修改实体信息
     * @param entity 实体信息
     * @return Boolean
     */
    @Log(value = "修改字典信息", eventType = LogEventTypeEnum.UPDATE, moduleType = LogModuleTypeEnum.SYS)
    @PutMapping(value = "/update")
    public HttpResult<Boolean> updateById( @RequestBody SysDict entity ){
        return new HttpResult<>(service.updateById(entity));
    }

    /**
    * 删除实体信息
    * @param id 实体ID
    * @return Boolean
    */
    @Log(value = "删除字典信息", eventType = LogEventTypeEnum.DELETE, moduleType = LogModuleTypeEnum.SYS)
    @DeleteMapping(value = "/remove/{id}")
    public HttpResult<Boolean> removeById( @PathVariable Integer id ){
        return new HttpResult<>(service.removeById(id));
    }

    /**
    * 查找实体信息
    * @param id 实体ID
    * @return HttpResult
    */
    @GetMapping(value = "/get/{id}")
    public HttpResult<SysDict> getById(@PathVariable Integer id ){
        return new HttpResult<>(service.getById(id));
    }

    /**
     * 校验字典值是否可用
     * @param dict 字典信息
     * @return true 可用
     */
    @PostMapping(value = "/checkValue")
    public HttpResult<Boolean> checkCode( @RequestBody SysDict dict ) {
        return new HttpResult<>(service.checkValue(dict));
    }

    /**
     * 根据父节点信息，获取节点下的所有层级的字典信息
     * @param parentValue 父节点value
     * @return 字典树信息
     */
    @GetMapping(value = "/treeByParentValue/{parentValue}")
    public HttpResult<List<SysDictTreeDto>> treeByParentValue(@PathVariable String parentValue) {
        return new HttpResult<>(service.treeByParentValue(parentValue));
    }
}

