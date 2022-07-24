package com.zhixiang.health.modules.sys.service;

import com.zhixiang.health.modules.sys.model.entity.SysUserRole;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * 用户角色表 服务类
 * </p>
 *
 * @author HeJiawang
 * @since 2020-05-08
 */
public interface ISysUserRoleService extends IService<SysUserRole> {

    /**
     * 统计角色下的用户数量
     * @param roleId 角色id
     * @return 用户数量
     */
    long countByRoleId(int roleId);

    /**
     * 重新设置用户拥有的角色信息
     * @param userId 用户id
     * @param roleIdList 角色id集合
     * @return boolean
     */
    boolean merge(Integer userId, List<Integer> roleIdList);

    /**
     * 删除用户与角色关联信息
     * @param userId 用户id
     * @return true
     */
    boolean removeByUserId(Integer userId);
}
