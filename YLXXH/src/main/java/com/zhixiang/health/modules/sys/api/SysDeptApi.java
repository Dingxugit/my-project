package com.zhixiang.health.modules.sys.api;

import com.zhixiang.health.common.http.result.HttpResult;
import com.zhixiang.health.common.model.annotation.Log;
import com.zhixiang.health.common.model.param.UserParam;
import com.zhixiang.health.modules.log.model.enums.LogEventTypeEnum;
import com.zhixiang.health.modules.log.model.enums.LogModuleTypeEnum;
import com.zhixiang.health.modules.sys.model.dto.SysDeptTreeDto;
import com.zhixiang.health.modules.sys.model.entity.SysDept;
import com.zhixiang.health.modules.sys.service.ISysDeptService;
import com.zhixiang.health.modules.t.model.dto.TCarDto;
import org.springframework.web.bind.annotation.*;

import com.zhixiang.health.common.http.abs.BaseHttp;

import javax.annotation.Resource;
import java.util.List;

/**
 * <p>
 * 部门信息 前端控制器
 * </p>
 *
 * @author HeJiawang
 * @since 2020-04-28
 */
@RestController
@RequestMapping("/sys/dept")
public class SysDeptApi extends BaseHttp {

    @Resource
    private ISysDeptService service;

    /**
     * 获取所有机构部门信息
     * @return 机构部门信息集合
     */
    @GetMapping(value = "/list")
    public HttpResult<List<SysDept>> list() {
        return new HttpResult<>(service.list());
    }

    /**
     * 获取树形结构数据
     * @return 树形结构数据集合
     */
    @GetMapping(value = "/listTree")
    public HttpResult<List<SysDeptTreeDto>> listTree() {
        return new HttpResult<>(service.listTree());
    }

    /**
     * 保存实体信息
     * @param entity 实体信息
     * @return Boolean
     */
    @Log(value = "新增机构部门信息", eventType = LogEventTypeEnum.INSERT, moduleType = LogModuleTypeEnum.SYS)
    @PostMapping(value = "/save")
    public HttpResult<Boolean> save( @RequestBody SysDept entity ){
        return new HttpResult<>(service.save(entity));
    }

    /**
     * 修改实体信息
     * @param entity 实体信息
     * @return Boolean
     */
    @Log(value = "修改机构部门信息", eventType = LogEventTypeEnum.UPDATE, moduleType = LogModuleTypeEnum.SYS)
    @PutMapping(value = "/update")
    public HttpResult<Boolean> updateById( @RequestBody SysDept entity ){
        return new HttpResult<>(service.updateById(entity));
    }

    /**
    * 删除实体信息
    * @param id 实体ID
    * @return Boolean
    */
    @Log(value = "删除机构部门信息", eventType = LogEventTypeEnum.DELETE, moduleType = LogModuleTypeEnum.SYS)
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
    public HttpResult<SysDept> getById(@PathVariable Integer id ){
        return new HttpResult<>(service.getById(id));
    }

    /**
     * 校验部门编码是否可用
     * @param dept 部门信息
     * @return true 部门编码可用
     */
    @PostMapping(value = "/checkCode")
    public HttpResult<Boolean> checkCode( @RequestBody SysDept dept ) {
        return new HttpResult<>(service.checkCode(dept));
    }

    @GetMapping(value = "/getSysDeptList")
    public HttpResult<List<SysDept>> getSysDeptList(UserParam param){
        return new HttpResult<>(service.getSysDeptList(param));
    }
}

