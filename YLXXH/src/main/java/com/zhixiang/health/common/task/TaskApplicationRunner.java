package com.zhixiang.health.common.task;

import com.zhixiang.health.modules.sys.service.ISysTaskService;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * 项目启动时，启动定时任务配置
 * @author HeJiawang
 * @since 2019-01-24
 */
@Component
public class TaskApplicationRunner implements ApplicationRunner {

    @Resource
    private ISysTaskService service;

    /**
     * run
     * @param args args
     */
    @Override
    public void run(ApplicationArguments args) {
        service.startAllTask();
    }
}
