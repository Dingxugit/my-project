package com.zhixiang.health.modules.sys.model.dto;

import lombok.Data;
import lombok.experimental.Accessors;

import java.util.List;

/**
 * <p>
 * 登录后要获取的配置信息
 * </p>
 *
 * @author HeJiawang
 * @since 2020-06-24
 */
@Data
@Accessors(chain = true)
public class SysLoginResourceDto {

    /**
     * 当前有权限查看的系统信息
     */
    private List<SysSystemDto> systemList;

    /**
     * 有权限的标志信息
     */
    private List<String> authCode;
}
