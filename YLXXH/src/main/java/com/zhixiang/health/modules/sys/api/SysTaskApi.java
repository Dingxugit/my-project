package com.zhixiang.health.modules.sys.api;

import cn.hutool.core.util.StrUtil;
import com.zhixiang.health.common.http.pagination.Page;
import com.zhixiang.health.common.http.result.HttpPageResult;
import com.zhixiang.health.common.http.result.HttpResult;
import com.zhixiang.health.common.model.annotation.Log;
import com.zhixiang.health.modules.log.model.enums.LogEventTypeEnum;
import com.zhixiang.health.modules.log.model.enums.LogModuleTypeEnum;
import com.zhixiang.health.modules.sys.model.dto.SysTaskDto;
import com.zhixiang.health.modules.sys.model.entity.SysTask;
import com.zhixiang.health.modules.sys.service.ISysTaskService;
import org.quartz.CronExpression;
import org.springframework.web.bind.annotation.*;

import com.zhixiang.health.common.http.abs.BaseHttp;

import javax.annotation.Resource;

/**
 * <p>
 * 定时任务配置表 前端控制器
 * </p>
 *
 * @author HeJiawang
 * @since 2020-04-26
 */
@RestController
@RequestMapping("/sys/task")
public class SysTaskApi extends BaseHttp {

    @Resource
    private ISysTaskService service;

    /**
     * 分页查询信息
     * @param page page
     * @param entity 实体信息
     * @return page
     */
    @Log(value = "查看定时任务列表", eventType = LogEventTypeEnum.SELECT, moduleType = LogModuleTypeEnum.SYS)
    @GetMapping(value = "/page")
    public HttpPageResult<SysTaskDto> page(Page<SysTaskDto> page, SysTask entity) {
        return new HttpPageResult<>(service.page(page, entity));
    }

    /**
     * 保存实体信息
     * @param entity 实体信息
     * @return Boolean
     */
    @Log(value = "新增定时任务", eventType = LogEventTypeEnum.INSERT, moduleType = LogModuleTypeEnum.SYS)
    @PostMapping(value = "/save")
    public HttpResult<Boolean> save( @RequestBody SysTask entity ){
        return new HttpResult<>(service.save(entity));
    }

    /**
     * 修改实体信息
     * @param entity 实体信息
     * @return Boolean
     */
    @Log(value = "修改定时任务", eventType = LogEventTypeEnum.UPDATE, moduleType = LogModuleTypeEnum.SYS)
    @PutMapping(value = "/update")
    public HttpResult<Boolean> updateById( @RequestBody SysTask entity ){
        return new HttpResult<>(service.updateById(entity));
    }

    /**
    * 删除实体信息
    * @param entity 实体信息
    * @return Boolean
    */
    @Log(value = "删除定时任务", eventType = LogEventTypeEnum.DELETE, moduleType = LogModuleTypeEnum.SYS)
    @PostMapping(value = "/remove")
    public HttpResult<Boolean> remove( @RequestBody SysTask entity ){
        return new HttpResult<>(service.remove(entity));
    }

    /**
    * 查找实体信息
    * @param id 实体ID
    * @return HttpResult
    */
    @GetMapping(value = "/get/{id}")
    public HttpResult<SysTask> getById(@PathVariable Integer id ){
        return new HttpResult<>(service.getById(id));
    }

    /**
     * 校验任务类是否可用
     *  1、任务类存在
     *  2、任务类未被使用
     * @param sysTask sysTask
     * @return true 存在并未被使用，false不存在或已被使用
     */
    @PostMapping(value = "/checkClassName")
    public HttpResult<Boolean> checkClassName(@RequestBody SysTask sysTask) {
        return new HttpResult<>(service.checkClassName(sysTask));
    }

    /**
     * 验证定时任务规则表达式
     * @param rule rule
     * @return Boolean true 通过； false不通过
     */
    @GetMapping(value = "/checkRule")
    public HttpResult<Boolean> checkRule(String rule){
        return new HttpResult<>(
                StrUtil.isNotEmpty(rule) && CronExpression.isValidExpression(rule)
        );
    }

    /**
     * 校验任务名称是否可用
     *  1、与其他任务的名称不重复
     * @param sysTask sysTask
     * @return true 可用； false 不可用
     */
    @PostMapping(value = "/checkName")
    public HttpResult<Boolean> checkName(@RequestBody SysTask sysTask) {
        return new HttpResult<>(service.checkName(sysTask));
    }

    /**
     * 暂停任务
     * @param sysTask sysTask
     * @return Boolean
     */
    @Log(value = "暂停定时任务", eventType = LogEventTypeEnum.UPDATE, moduleType = LogModuleTypeEnum.SYS)
    @PostMapping(value = "/pause")
    public HttpResult<Boolean> pause(@RequestBody SysTask sysTask){
        return new HttpResult<>(service.pause(sysTask));
    }

    /**
     * 恢复任务
     * @param sysTask sysTask
     * @return Boolean
     */
    @Log(value = "恢复定时任务", eventType = LogEventTypeEnum.UPDATE, moduleType = LogModuleTypeEnum.SYS)
    @PostMapping(value = "/resume")
    public HttpResult<Boolean> resume(@RequestBody SysTask sysTask){
        return new HttpResult<>(service.resume(sysTask));
    }

    /**
     * 立即执行一次任务
     * @param sysTask sysTask
     * @return Boolean
     */
    @Log(value = "立即执行定时任务", eventType = LogEventTypeEnum.UNKNOWN, moduleType = LogModuleTypeEnum.SYS)
    @PostMapping(value = "/startNow")
    public HttpResult<Boolean> startNow(@RequestBody SysTask sysTask){
        return new HttpResult<>(service.startNow(sysTask));
    }
}

