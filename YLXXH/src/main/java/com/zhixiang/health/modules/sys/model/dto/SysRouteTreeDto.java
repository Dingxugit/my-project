package com.zhixiang.health.modules.sys.model.dto;

import cn.hutool.core.collection.CollUtil;
import com.zhixiang.health.modules.sys.model.enums.SysMenuIndexEnum;
import com.zhixiang.health.modules.sys.model.enums.SysMenuShowTypeEnum;
import lombok.Data;
import lombok.experimental.Accessors;

import java.util.List;

/**
 * <p>
 * 系统路由树信息
 * </p>
 *
 * @author HeJiawang
 * @since 2020-07-03
 */
@Data
@Accessors(chain = true)
public class SysRouteTreeDto {

    /**
     * 资源id sys_resource.id
     */
    private Integer id;

    /**
     * 父资源id sys_resource.parent_id
     */
    private Integer parentId;

    /**
     * 菜单名称
     */
    private String name;

    /**
     * 图标
     */
    private String icon;

    /**
     * router路径
     */
    private String path;

    /**
     * 组件路径
     */
    private String component;

    /**
     * 是否是主页，1是，0否
     */
    private SysMenuIndexEnum isIndex;

    /**
     * 页面展示方式  0 HOME  1 SCREEN
     */
    private SysMenuShowTypeEnum showType;

    /**
     * 权限信息
     */
    private List<String> authority;

    /**
     * 下级路由信息
     */
    private List<SysRouteTreeDto> routes;

    /**
     * 添加子节点路由信息
     * @param node 路由信息
     */
    public void addRoute(SysRouteTreeDto node) {
        if (CollUtil.isEmpty(routes)) {
            routes = CollUtil.newArrayList();
        }

        this.routes.add(node);
    }
}
