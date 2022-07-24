package com.zhixiang.health.modules.sys.service.impl;

import cn.hutool.core.collection.CollUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.zhixiang.health.modules.sys.model.entity.SysUserRole;
import com.zhixiang.health.modules.sys.mapper.SysUserRoleMapper;
import com.zhixiang.health.modules.sys.service.ISysUserRoleService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

/**
 * <p>
 * 用户角色表 服务实现类
 * </p>
 *
 * @author HeJiawang
 * @since 2020-05-08
 */
@Service
public class SysUserRoleServiceImpl extends ServiceImpl<SysUserRoleMapper, SysUserRole> implements ISysUserRoleService {

    @Resource
    private SysUserRoleMapper mapper;

    /**
     * 统计角色下的用户数量
     * @param roleId 角色id
     * @return 用户数量
     */
    @Override
    public long countByRoleId(int roleId) {
        QueryWrapper<SysUserRole> wrapper = new QueryWrapper<>(
                new SysUserRole().setRoleId(roleId)
        );

        return super.count(wrapper);
    }

    /**
     * 重新设置用户拥有的角色信息
     * @param userId 用户id
     * @param roleIdList 角色id集合
     * @return boolean
     */
    @Transactional
    @Override
    public boolean merge(Integer userId, List<Integer> roleIdList) {
        mapper.deleteByUserId(userId);

        if (CollUtil.isNotEmpty(roleIdList)) {
            roleIdList.forEach(roleId -> {
                SysUserRole sysUserRole = new SysUserRole()
                        .setRoleId(roleId).setUserId(userId);
                super.save(sysUserRole);
            });
        }

        return true;
    }

    /**
     * 删除用户与角色关联信息
     * @param userId 用户id
     * @return true
     */
    @Override
    public boolean removeByUserId(Integer userId) {
        return mapper.deleteByUserId(userId) >= 0;
    }
}
