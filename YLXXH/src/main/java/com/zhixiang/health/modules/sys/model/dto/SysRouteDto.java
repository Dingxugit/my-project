package com.zhixiang.health.modules.sys.model.dto;

import lombok.Data;
import lombok.experimental.Accessors;

import java.util.List;

/**
 * <p>
 * 系统路由信息
 * </p>
 *
 * @author HeJiawang
 * @since 2020-07-03
 */
@Data
@Accessors(chain = true)
public class SysRouteDto {

    /**
     * home页展示的菜单路由
     */
    private List<SysRouteTreeDto> homeRouteList;

    /**
     * 全屏页展示的菜单路由信息
     */
    private List<SysRouteTreeDto> screenRouteList;
}
