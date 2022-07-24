package com.zhixiang.health.modules.sys.service;

import com.zhixiang.health.common.http.pagination.Page;
import com.zhixiang.health.modules.sys.model.dto.SysTaskDto;
import com.zhixiang.health.modules.sys.model.entity.SysTask;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 定时任务配置表 服务类
 * </p>
 *
 * @author HeJiawang
 * @since 2020-04-26
 */
public interface ISysTaskService extends IService<SysTask> {

    /**
     * 分页查询信息
     * @param page page
     * @param entity 实体信息
     * @return page
     */
    Page<SysTaskDto> page(Page<SysTaskDto> page, SysTask entity);

    /**
     * 删除实体信息
     * @param entity 实体信息
     * @return Boolean
     */
    Boolean remove(SysTask entity);

    /**
     * 校验任务类是否可用
     *  1、任务类存在
     *  2、任务类未被使用
     * @param sysTask sysTask
     * @return true 存在并未被使用，false不存在或已被使用
     */
    Boolean checkClassName(SysTask sysTask);

    /**
     * 校验任务名称是否可用
     *  1、与其他任务的名称不重复
     * @param sysTask sysTask
     * @return true 可用； false 不可用
     */
    Boolean checkName(SysTask sysTask);

    /**
     * 暂停任务
     * @param sysTask sysTask
     * @return Boolean
     */
    Boolean pause(SysTask sysTask);

    /**
     * 恢复任务
     * @param sysTask sysTask
     * @return Boolean
     */
    Boolean resume(SysTask sysTask);

    /**
     * 立即执行一次任务
     * @param sysTask sysTask
     * @return Boolean
     */
    Boolean startNow(SysTask sysTask);

    /**
     * 启动所有定时任务
     */
    void startAllTask();
}
