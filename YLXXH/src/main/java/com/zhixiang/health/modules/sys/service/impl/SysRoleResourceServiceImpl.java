package com.zhixiang.health.modules.sys.service.impl;

import cn.hutool.core.collection.CollUtil;
import com.zhixiang.health.modules.sys.model.entity.SysRoleResource;
import com.zhixiang.health.modules.sys.mapper.SysRoleResourceMapper;
import com.zhixiang.health.modules.sys.service.ISysRoleResourceService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

/**
 * <p>
 * 角色权限表 服务实现类
 * </p>
 *
 * @author HeJiawang
 * @since 2020-06-24
 */
@Service
public class SysRoleResourceServiceImpl extends ServiceImpl<SysRoleResourceMapper, SysRoleResource> implements ISysRoleResourceService {

    @Resource
    private SysRoleResourceMapper mapper;

    /**
     * 为角色授权
     * @param roleId 角色id
     * @param rIds 资源id集合
     * @return Boolean
     */
    @CacheEvict(value = "JMonkey_Permission", allEntries = true)
    @Transactional
    @Override
    public Boolean auth(Integer roleId, List<Integer> rIds) {
        mapper.deleteByRoleId(roleId);

        if (CollUtil.isNotEmpty(rIds)) {
            rIds.forEach(resourceId -> {
                SysRoleResource entity = new SysRoleResource()
                        .setRoleId(roleId)
                        .setResourceId(resourceId);

                super.save(entity);
            });
        }

        return true;
    }

    /**
     * 获取角色以授权的资源id
     * @param roleId 角色id
     * @return 资源id
     */
    @Override
    public List<Integer> findRidByRole(Integer roleId) {
        return mapper.findRidByRole(roleId);
    }

}
