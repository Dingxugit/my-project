package com.zhixiang.health.modules.log.model.param;

import lombok.Data;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotNull;

/**
 * 检索日志信息参数
 *
 * @author HeJiawang
 * @since 2020-07-08
 */
@Data
@Accessors(chain = true)
public class LogSearchParam {

    /**
     * 年
     */
    @NotNull(message = "年份不能为空")
    private Integer year;

    /**
     * 月
     */
    @NotNull(message = "月份不能为空")
    private Integer month;

    /**
     * 登录名称
     */
    private String userName;

    /**
     * 真实姓名
     */
    private String realName;

    /**
     * 每页显示条数，默认 10
     */
    private long pageSize = 10;

    /**
     * 当前页
     */
    private long current = 1;

    /**
     * limitStart
     */
    private long limitStart;

    /**
     * renderLimitStart
     * @return this
     */
    public LogSearchParam renderLimitStart() {
        this.limitStart = pageSize * ( current - 1 );

        return this;
    }
}
