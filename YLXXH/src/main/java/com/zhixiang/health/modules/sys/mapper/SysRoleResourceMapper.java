package com.zhixiang.health.modules.sys.mapper;

import com.zhixiang.health.modules.sys.model.entity.SysRoleResource;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 角色权限表 Mapper 接口
 * </p>
 *
 * @author HeJiawang
 * @since 2020-06-24
 */
public interface SysRoleResourceMapper extends BaseMapper<SysRoleResource> {

    /**
     * 根据资源id删除角色授权信息
     * @param resourceId 资源id
     * @return int
     */
    int deleteByResourceId(@Param("resourceId") Integer resourceId);

    /**
     * 根据角色id删除角色授权信息
     * @param roleId 角色id
     * @return int
     */
    int deleteByRoleId(@Param("roleId") Integer roleId);

    /**
     * 获取角色以授权的资源id
     * @param roleId 角色id
     * @return 资源id
     */
    List<Integer> findRidByRole(@Param("roleId") Integer roleId);
}
