package com.zhixiang.health.modules.sys.mapper;

import com.zhixiang.health.modules.sys.model.dto.SysTaskDto;
import com.zhixiang.health.modules.sys.model.entity.SysTask;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 定时任务配置表 Mapper 接口
 * </p>
 *
 * @author HeJiawang
 * @since 2020-04-26
 */
public interface SysTaskMapper extends BaseMapper<SysTask> {

    /**
     * 校验任务类是否已被使用
     * @param sysTask sysTask
     * @return <= 0 未被使用
     */
    int checkClassName(SysTask sysTask);

    /**
     * 校验任务名称是否可用
     *  1、与其他任务的名称不重复
     * @param sysTask sysTask
     * @return <= 0 可用
     */
    int checkName(SysTask sysTask);

    /**
     * 分页查询信息——条数
     * @param entity entity
     * @return 条数
     */
    long pageTotal(SysTask entity);

    /**
     * 分页查询信息
     * @param entity entity
     * @return lsit
     */
    List<SysTaskDto> pageList( @Param("task") SysTask entity,
                               @Param("limitStart") long limitStart, @Param("pageSize") long pageSize);
}
