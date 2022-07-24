package com.zhixiang.health.modules.sys.service;

import com.zhixiang.health.modules.sys.model.entity.SysRoleResource;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * 角色权限表 服务类
 * </p>
 *
 * @author HeJiawang
 * @since 2020-06-24
 */
public interface ISysRoleResourceService extends IService<SysRoleResource> {

    /**
     * 为角色授权
     * @param roleId 角色id
     * @param rIds 资源id集合
     * @return Boolean
     */
    Boolean auth(Integer roleId, List<Integer> rIds);

    /**
     * 获取角色以授权的资源id
     * @param roleId 角色id
     * @return 资源id
     */
    List<Integer> findRidByRole(Integer roleId);
}
