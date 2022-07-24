package com.zhixiang.health.modules.log.model.dto;

import cn.hutool.core.bean.BeanUtil;
import com.zhixiang.health.modules.log.model.entity.LogHandle;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 操作日志信息
 * </p>
 *
 * @author HeJiawang
 * @since 2020-07-07
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
public class LogHandleDto extends LogHandle {

    /**
     * 日志操作事件类型
     */
    private String eventTypeName;

    /**
     * 日志记录模块类型
     */
    private String moduleTypeName;

    /**
     * convertFromEntity
     * @param logHandle logHandle
     * @return LogHandleDto
     */
    public static LogHandleDto convertFromEntity(LogHandle logHandle) {
        LogHandleDto result = new LogHandleDto();
        BeanUtil.copyProperties(logHandle, result);

        result.setEventTypeName(logHandle.getEventType().getDesc())
                .setModuleTypeName(logHandle.getModuleType().getDesc());

        return result;
    }
}
