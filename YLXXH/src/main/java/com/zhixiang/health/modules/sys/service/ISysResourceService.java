package com.zhixiang.health.modules.sys.service;

import com.zhixiang.health.modules.sys.model.dto.SysLoginResourceDto;
import com.zhixiang.health.modules.sys.model.dto.SysResourceDto;
import com.zhixiang.health.modules.sys.model.dto.SysRouteDto;
import com.zhixiang.health.modules.sys.model.entity.SysResource;
import com.baomidou.mybatisplus.extension.service.IService;
import com.zhixiang.health.modules.sys.model.enums.SysResourceTypeEnum;

import java.util.List;

/**
 * <p>
 * 系统资源表 服务类
 * </p>
 *
 * @author HeJiawang
 * @since 2020-06-24
 */
public interface ISysResourceService extends IService<SysResource> {

    /**
     * 删除资源信息
     * 删除子集资源信息
     * 删除角色与资源关联信息
     * @param type 资源类型
     * @param resourceId 资源id
     * @return true
     */
    boolean removeByResourceId(SysResourceTypeEnum type, Integer resourceId);

    /**
     * 获取type值以上的资源信息
     * @param type type
     * @return list
     */
    List<SysResourceDto> list(SysResourceTypeEnum type);

    /**
     * 修改资源的上级资源id
     * @param resource 资源信息
     * @return true
     */
    boolean updateParentId(SysResource resource);

    /**
     * 获取前端路由信息
     * @return 路由信息
     */
    SysRouteDto routeList();

    /**
     * 获取当前登录人的资源信息
     * @param userId 当前登录人ID
     * @return 资源信息
     */
    SysLoginResourceDto loginResource(Integer userId);
}
