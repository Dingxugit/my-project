package com.zhixiang.health.modules.sys.service.impl;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zhixiang.health.modules.sys.mapper.*;
import com.zhixiang.health.modules.sys.model.dto.*;
import com.zhixiang.health.modules.sys.model.entity.SysResource;
import com.zhixiang.health.modules.sys.model.enums.SysMenuShowTypeEnum;
import com.zhixiang.health.modules.sys.model.enums.SysResourceTypeEnum;
import com.zhixiang.health.modules.sys.service.ISysResourceService;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * <p>
 * 系统资源表 服务实现类
 * </p>
 *
 * @author HeJiawang
 * @since 2020-06-24
 */
@Service
public class SysResourceServiceImpl extends ServiceImpl<SysResourceMapper, SysResource> implements ISysResourceService {

    @Resource
    private SysResourceMapper mapper;

    @Resource
    private SysRoleResourceMapper sysRoleResourceMapper;

    @Resource
    private SysSystemMapper sysSystemMapper;

    @Resource
    private SysButtonMapper sysButtonMapper;

    @Resource
    private SysMenuMapper sysMenuMapper;

    private static Map<SysResourceTypeEnum, String> RESOURCE_MAP = new HashMap<>();
    static {
        RESOURCE_MAP.put(SysResourceTypeEnum.SYSTEM, "sys_system");
        RESOURCE_MAP.put(SysResourceTypeEnum.MENU, "sys_menu");
        RESOURCE_MAP.put(SysResourceTypeEnum.BUTTON, "sys_button");
    };

    /**
     * 删除资源信息
     * 删除子集资源信息
     * 删除角色与资源关联信息
     * @param type 资源类型
     * @param resourceId 资源id
     * @return true
     */
    @CacheEvict(value = "JMonkey_Permission", allEntries = true)
    @Transactional
    @Override
    public boolean removeByResourceId(SysResourceTypeEnum type, Integer resourceId) {
        // 获取资源信息
        QueryWrapper<SysResource> wrapper = new QueryWrapper<>(
                new SysResource().setResourceId(resourceId).setType(type)
        );
        SysResource resource = super.getOne(wrapper);

        // 删除资源信息
        super.removeById(resource.getId());

        // 删除资源内容信息, 逻辑删除
        mapper.deleteResource(RESOURCE_MAP.get(type), resourceId);

        // 删除角色资源关联信息
        sysRoleResourceMapper.deleteByResourceId(resource.getId());

        // 删除下级资源
        wrapper = new QueryWrapper<>(
                new SysResource().setParentId(resource.getId())
        );
        List<SysResource> resourceList = this.list(wrapper);
        if (CollUtil.isNotEmpty(resourceList)) {
            resourceList.forEach(r -> {
                this.removeByResourceId(r.getType(), r.getResourceId());
            });
        }

        return true;
    }

    /**
     * 获取type值以上的资源信息
     * @param type type
     * @return list
     */
    @Override
    public List<SysResourceDto> list(SysResourceTypeEnum type) {
        return mapper.list(type);
    }

    /**
     * 修改资源的上级资源id
     * @param resource 资源信息
     * @return true
     */
    @Override
    public boolean updateParentId(SysResource resource) {
        return mapper.updateParentId(resource) >= 0;
    }

    /**
     * 获取前端路由信息
     * @return 路由信息
     */
    @Override
    public SysRouteDto routeList() {
        /*
         * 获取所有系统、菜单信息
         * 带着系统信息是因为要拼接树结构信息，之后把第一节点去掉，也就是去掉了所有系统信息，这样就保留了所有菜单树
         * 另一个原因是拼接菜单权限信息，拼接规则为： '系统资源id' + '_' + '角色code'
         * 这样在前端点击具体某一个系统时，刷新前端角色集合为该规则信息，就能实现进入不同系统展现不同菜单了
         */
        List<SysRouteTreeDto> list = mapper.routeList();
        // 构造树形结构信息
        list = this.buildTree(list);

        // 去除了系统信息，并将系统信息拼接到路由权限中
        List<SysRouteTreeDto> routeList = CollUtil.newArrayList();
        list.forEach(system -> {
            List<SysRouteTreeDto> routes = system.getRoutes();

            if (CollUtil.isNotEmpty(routes)) {
                renderAuthority(system.getId().toString(), routes);
                routeList.addAll(routes);
            }
        });

        List<SysRouteTreeDto> screenRouteList = CollUtil.newArrayList();
        this.groupRoute(routeList, screenRouteList);    // 将全屏路由从routeList中删除，并添加到screenRouteList中

        return new SysRouteDto()
                .setHomeRouteList(routeList)
                .setScreenRouteList(screenRouteList);
    }

    /**
     * 将全屏路由从routeList中删除，并添加到screenRouteList中
     * @param routeList routeList
     * @param screenRouteList screenRouteList
     */
    private void groupRoute(List<SysRouteTreeDto> routeList, List<SysRouteTreeDto> screenRouteList) {

        Iterator<SysRouteTreeDto> iterator = routeList.iterator();
        while(iterator.hasNext()){
            SysRouteTreeDto s = iterator.next();

            if(s.getShowType() == SysMenuShowTypeEnum.SCREEN) {
                screenRouteList.add(s);
                iterator.remove();
            } else {
                if (CollUtil.isNotEmpty(s.getRoutes())) {
                    this.groupRoute(s.getRoutes(), screenRouteList);
                }
            }
        }
    }

    /**
     * 将系统信息拼接到路由权限中
     * @param authority 系统资源id
     * @param routes 陆游信息
     */
    private void renderAuthority(String authority, List<SysRouteTreeDto> routes) {
        routes.forEach(route -> {
            List<String> authList = CollUtil.newArrayList();
            route.getAuthority().forEach(auth -> authList.add(authority + "_" + auth) );

            route.setAuthority(authList);

            if (CollUtil.isNotEmpty(route.getRoutes())) {
                this.renderAuthority(authority, route.getRoutes());
            }
        });
    }

    /**
     * 构造路由树信息
     * @param list 路由信息
     * @return 路由树信息
     */
    private List<SysRouteTreeDto> buildTree(List<SysRouteTreeDto> list) {
        List<SysRouteTreeDto> treeList = CollUtil.newArrayList();
        list.forEach(node -> {
            if (0 == node.getParentId()) treeList.add(node);

            list.forEach(nodeChildren -> {
                if (nodeChildren.getParentId() == node.getId()) {
                    node.addRoute(nodeChildren);
                }
            });
        });

        return treeList;
    }

    /**
     * 获取当前登录人的资源信息
     * @param userId 当前登录人ID
     * @return 资源信息
     */
    @Override
    public SysLoginResourceDto loginResource(Integer userId) {
        List<SysMenuDto> menuIndexList = sysMenuMapper.listIndexByUserId(userId);
        Map<Integer, List<SysMenuDto>> menuIndexMap = menuIndexList.stream().collect(Collectors.groupingBy(SysMenuDto::getResourceParentId));

        List<SysSystemDto> systemList = sysSystemMapper.listByUseId(userId);
        systemList.forEach(system -> {
            system.setIndexPath(findIndex(system.getResourceId(), menuIndexMap));
        });

        return new SysLoginResourceDto()
                .setSystemList(systemList)
                .setAuthCode(sysButtonMapper.listPermissionByUserId(userId));
    }

    private String findIndex(Integer parentId, Map<Integer, List<SysMenuDto>> map) {
        List<SysMenuDto> list = map.get(parentId);
        if (CollUtil.isNotEmpty(list)) {

            for (SysMenuDto menu : list) {

                if (CollUtil.isEmpty(map.get(menu.getResourceId()))) {
                    return menu.getPath();
                } else {
                    return findIndex(menu.getResourceId(), map);
                }
            }
        }

        return StrUtil.EMPTY;
    }
}
