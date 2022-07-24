package com.zhixiang.health.modules.log.mapper;

import com.zhixiang.health.modules.log.model.entity.LogHandle;
import com.zhixiang.health.modules.log.model.param.LogSearchParam;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 操作日志信息 Mapper 接口
 * </p>
 *
 * @author HeJiawang
 * @since 2020-07-07
 */
public interface LogHandleMapper {

    /**
     * 保存操作日志信息
     * @param tableName 日志表
     * @param logHandle 操作日志信息
     * @return int
     */
    int insert(@Param("tableName") String tableName, @Param("log") LogHandle logHandle);

    /**
     * 获取分页信息
     * @param tableName tableName
     * @param param param
     * @return 分页信息
     */
    List<LogHandle> pageList(@Param("tableName") String tableName, @Param("param") LogSearchParam param);

    /**
     * 获取分页信息
     * @param tableName tableName
     * @param param param
     * @return 分页信息
     */
    long pageTotal(@Param("tableName") String tableName, @Param("param") LogSearchParam param);
}
