package com.zhixiang.health.modules.log.model.dto;

import com.zhixiang.health.modules.log.model.entity.LogLogin;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 登录日志dto信息
 * </p>
 *
 * @author HeJiawang
 * @since 2020-07-08
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
public class LogLoginDto extends LogLogin {

    /**
     * 真实姓名
     */
    private String realName;
}
