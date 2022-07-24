package com.zhixiang.health.modules.sys.mapper;

import com.zhixiang.health.modules.sys.model.entity.SysRole;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

/**
 * <p>
 * 系统角色信息表 Mapper 接口
 * </p>
 *
 * @author HeJiawang
 * @since 2020-04-21
 */
public interface SysRoleMapper extends BaseMapper<SysRole> {

    /**
     * 校验角色编码是否可用
     * @param role 角色信息
     * @return true 编码可用
     */
    int checkCode(SysRole role);
}
