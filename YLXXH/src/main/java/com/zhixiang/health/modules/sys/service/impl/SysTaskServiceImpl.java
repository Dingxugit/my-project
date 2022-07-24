package com.zhixiang.health.modules.sys.service.impl;

import com.zhixiang.health.common.http.pagination.Page;
import com.zhixiang.health.common.model.enums.UsedStateEnum;
import com.zhixiang.health.modules.sys.model.dto.SysTaskDto;
import com.zhixiang.health.modules.sys.model.entity.SysTask;
import com.zhixiang.health.modules.sys.mapper.SysTaskMapper;
import com.zhixiang.health.modules.sys.service.ISysTaskService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.quartz.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

/**
 * <p>
 * 定时任务配置表 服务实现类
 * </p>
 *
 * @author HeJiawang
 * @since 2020-04-26
 */
@Slf4j
@Service
public class SysTaskServiceImpl extends ServiceImpl<SysTaskMapper, SysTask> implements ISysTaskService {

    @Resource
    private SysTaskMapper mapper;

    @Resource
    private Scheduler scheduler;

    /**
     * 分页查询信息
     * @param page page
     * @param entity 实体信息
     * @return page
     */
    @Override
    public Page<SysTaskDto> page(Page<SysTaskDto> page, SysTask entity) {
        long total = mapper.pageTotal(entity);
        List<SysTaskDto> list = mapper.pageList(entity, page.getPageSize() * (page.getCurrent() - 1), page.getPageSize());

        return page.setTotal(total).setRecords(list);
    }

    /**
     * 保存实体信息
     * @param entity 实体信息
     * @return Boolean
     */
    @Override
    public boolean save(SysTask entity) {
        return this.addJob(entity) && super.save(entity);
    }

    /**
     * 修改实体信息
     *      删除后新增
     * @param entity 实体信息
     * @return Boolean
     */
    @Transactional
    @Override
    public boolean updateById(SysTask entity) {
        Boolean removeSuccess = this.remove(
                super.getById(entity.getId())
        );
        if (!removeSuccess) return false;

        entity.setId(null);
        return this.save(entity);
    }

    /**
     * 删除实体信息
     * @param entity 实体信息
     * @return Boolean
     */
    @Override
    public Boolean remove(SysTask entity) {
        try {

            JobKey key = new JobKey(entity.getName(), entity.getGroup());
            return scheduler.deleteJob(key) && super.removeById(entity.getId());
        } catch (SchedulerException e) {

            log.error(this.getClass().getName() + "." + Thread.currentThread().getStackTrace()[1].getMethodName(), e);
            return false;
        }
    }

    /**
     * 像任务管理器中添加任务
     * @param scheduleJob scheduleJob
     * @return true false
     */
    private boolean addJob(SysTask scheduleJob){
        Class job ;
        try {
            job = Class.forName(scheduleJob.getClassName());
        } catch (ClassNotFoundException e) {
            log.error(this.getClass().getName(), e.getMessage());

            return false;
        }

        JobDetail jobDetail = JobBuilder.newJob(job)
                .withIdentity(scheduleJob.getName(), scheduleJob.getGroup())
                .build();
        jobDetail.getJobDataMap()
                .put("scheduleJob", scheduleJob);

        // 表达式调度构建器（可判断创建SimpleScheduleBuilder）
        CronScheduleBuilder scheduleBuilder = CronScheduleBuilder.cronSchedule(scheduleJob.getRule());

        // 按新的cronExpression表达式构建一个新的trigger
        CronTrigger trigger = TriggerBuilder.newTrigger()
                .withIdentity(scheduleJob.getName(), scheduleJob.getGroup())
                .withSchedule(scheduleBuilder)
                .build();

        try {
            scheduler.scheduleJob(jobDetail, trigger);

            JobKey key = new JobKey(scheduleJob.getName(), scheduleJob.getGroup());
            if(scheduleJob.getUsedState() == UsedStateEnum.NO) scheduler.pauseJob(key);
            else scheduler.resumeJob(key);
        } catch (SchedulerException e) {
            log.error(this.getClass().getName() + "." + Thread.currentThread().getStackTrace()[1].getMethodName(), e);

            return false;
        }

        return true;
    }

    /**
     * 校验任务类是否可用
     *  1、任务类存在
     *  2、任务类未被使用
     * @param sysTask sysTask
     * @return true 存在并未被使用，false不存在或已被使用
     */
    @Override
    public Boolean checkClassName(SysTask sysTask) {
        try {
            Class.forName(sysTask.getClassName());
        } catch (ClassNotFoundException e) {
            return false;
        }

        return mapper.checkClassName(sysTask) <= 0;
    }

    /**
     * 校验任务名称是否可用
     *  1、与其他任务的名称不重复
     * @param sysTask sysTask
     * @return true 可用； false 不可用
     */
    @Override
    public Boolean checkName(SysTask sysTask) {
        return mapper.checkName(sysTask) <= 0;
    }

    /**
     * 暂停任务
     * @param sysTask sysTask
     * @return Boolean
     */
    @Override
    public Boolean pause(SysTask sysTask) {
        JobKey key = new JobKey(sysTask.getName(), sysTask.getGroup());

        try {
            scheduler.pauseJob(key);

            sysTask.setUsedState(UsedStateEnum.NO);
            return super.updateById(sysTask);
        } catch (SchedulerException e) {
            log.error(this.getClass().getName() + "." + Thread.currentThread().getStackTrace()[1].getMethodName(), e);
            return false;
        }
    }

    /**
     * 恢复任务
     * @param sysTask sysTask
     * @return Boolean
     */
    @Override
    public Boolean resume(SysTask sysTask) {
        JobKey key = new JobKey(sysTask.getName(), sysTask.getGroup());

        try {
            scheduler.pauseJob(key);

            sysTask.setUsedState(UsedStateEnum.YES);
            return super.updateById(sysTask);
        } catch (SchedulerException e) {
            log.error(this.getClass().getName() + "." + Thread.currentThread().getStackTrace()[1].getMethodName(), e);
            return false;
        }
    }

    /**
     * 立即执行一次任务
     * @param sysTask sysTask
     * @return Boolean
     */
    @Override
    public Boolean startNow(SysTask sysTask) {
        JobKey key = new JobKey(sysTask.getName(), sysTask.getGroup());

        try {
            scheduler.triggerJob(key);

            return true;
        } catch (SchedulerException e) {
            log.error(this.getClass().getName() + "." + Thread.currentThread().getStackTrace()[1].getMethodName(), e);
            return false;
        }
    }

    /**
     * 启动所有定时任务
     */
    @Override
    public void startAllTask() {
        super.list().forEach(task -> this.addJob(task));
    }
}
