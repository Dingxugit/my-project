package com.zhixiang.health.common.task;

import cn.hutool.core.date.DatePattern;
import cn.hutool.core.date.DateUtil;
import lombok.extern.slf4j.Slf4j;
import org.quartz.DisallowConcurrentExecution;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import java.util.Date;

/**
 * @Description: 定时任务父类
 * @Auther: HeJiawang
 * @Date: 2019-01-24
 */
@Slf4j
@DisallowConcurrentExecution    // 不能并发执行同一个Job
public abstract class BaseTask implements Job {

    /**
     * 执行定时任务
     * 在执行具体任务时，能够在run()前后做其他业务，比如发送消息通知管理员定时任务已经执行
     * @param context context
     * @throws JobExecutionException
     */
    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {
        log.info(this.getClass().getName() + " " + DateUtil.format(new Date(), DatePattern.NORM_DATETIME_PATTERN) + " 运行开始");

        run();

        log.info(this.getClass().getName() + " " + DateUtil.format(new Date(), DatePattern.NORM_DATETIME_PATTERN) + " 运行结束");
    }

    /**
     * 模板方法，在子类中重写该方法
     */
    public abstract void run();
}
