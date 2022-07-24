package com.zhixiang.health.modules.sys.mapper;

import com.zhixiang.health.modules.sys.model.entity.SysUserRole;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 * 用户角色表 Mapper 接口
 * </p>
 *
 * @author HeJiawang
 * @since 2020-05-08
 */
public interface SysUserRoleMapper extends BaseMapper<SysUserRole> {

    /**
     * 删除用户角色关联信息
     * @param userId 用户id
     * @return 删除个数
     */
    int deleteByUserId(@Param("userId") Integer userId);
}
