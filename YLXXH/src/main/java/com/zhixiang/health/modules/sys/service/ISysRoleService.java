package com.zhixiang.health.modules.sys.service;

import com.zhixiang.health.modules.sys.model.entity.SysRole;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 系统角色信息表 服务类
 * </p>
 *
 * @author HeJiawang
 * @since 2020-04-21
 */
public interface ISysRoleService extends IService<SysRole> {

    /**
     * 校验角色编码是否可用
     * @param role 角色信息
     * @return true 编码可用
     */
    Boolean checkCode(SysRole role);
}
