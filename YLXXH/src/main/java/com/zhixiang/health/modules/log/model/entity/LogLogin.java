package com.zhixiang.health.modules.log.model.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.zhixiang.health.common.model.entity.BaseEntity;
import com.zhixiang.health.modules.log.model.enums.LogLoginResultEnum;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 登录日志
 * </p>
 *
 * @author HeJiawang
 * @since 2020-07-08
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
public class LogLogin extends BaseEntity<LogLogin> {

    private static final long serialVersionUID=1L;

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
     * 月
     */
    private Integer month;

    /**
     * 日
     */
    private Integer day;

    /**
     * 用户登陆名称
     */
    private String userName;

    /**
     * 登录结果 0失败 1成功
     */
    private LogLoginResultEnum result;

    /**
     * 结果编码
     */
    private Integer code;

    /**
     * 结果信息
     */
    private String message;

}
