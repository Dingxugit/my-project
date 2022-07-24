package com.zhixiang.health.modules.sys.service.impl;

import com.zhixiang.health.modules.sys.mapper.SysRoleResourceMapper;
import com.zhixiang.health.modules.sys.model.entity.SysRole;
import com.zhixiang.health.modules.sys.mapper.SysRoleMapper;
import com.zhixiang.health.modules.sys.service.ISysRoleService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zhixiang.health.modules.sys.service.ISysUserRoleService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.Serializable;

/**
 * <p>
 * 系统角色信息表 服务实现类
 * </p>
 *
 * @author HeJiawang
 * @since 2020-04-21
 */
@Service
public class SysRoleServiceImpl extends ServiceImpl<SysRoleMapper, SysRole> implements ISysRoleService {

    @Resource
    private SysRoleMapper mapper;

    @Resource
    private ISysUserRoleService sysUserRoleService;

    @Resource
    private SysRoleResourceMapper sysRoleResourceMapper;

    /**
     * 删除角色信息
     *  1、判断是否有用户使用该角色，如果角色被使用，删除失败
     *  2、删除角色时，删除角色对应的资源
     * @param id 角色id
     * @return
     */
    @Override
    public boolean removeById(Serializable id) {
        int roleId = Integer.valueOf(id.toString());

        long userNum = sysUserRoleService.countByRoleId(roleId);
        if (userNum > 0) return false;

        return super.removeById(id)
                && sysRoleResourceMapper.deleteByRoleId(Integer.valueOf(id.toString())) >= 0;
    }

    /**
     * 校验角色编码是否可用
     * @param role 角色信息
     * @return true 编码可用
     */
    @Override
    public Boolean checkCode(SysRole role) {
        return mapper.checkCode(role) <= 0;
    }
}
