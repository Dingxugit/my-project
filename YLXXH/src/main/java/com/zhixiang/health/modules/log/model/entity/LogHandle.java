package com.zhixiang.health.modules.log.model.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.zhixiang.health.common.model.entity.BaseEntity;
import com.zhixiang.health.modules.log.model.enums.LogEventTypeEnum;
import com.zhixiang.health.modules.log.model.enums.LogModuleTypeEnum;
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
public class LogHandle extends BaseEntity<LogHandle> {

    /**
     * 主键ID
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 年
     */
    private Integer year;

    /**
     * 日
     */
    private Integer day;

    /**
     * 操作人登录名称
     */
    private String userName;

    /**
     * 日志信息
     */
    private String detail;

    /**
     * 访问方法名
     */
    private String method;

    /**
     * 访问路径
     */
    private String url;

    /**
     * 访问方式
     */
    private String httpType;

    /**
     * 访问参数
     */
    private String args;

    /**
     * 日志操作事件类型
     */
    private LogEventTypeEnum eventType;

    /**
     * 日志记录模块类型
     */
    private LogModuleTypeEnum moduleType;
}
