package com.zhixiang.health.modules.sys.service.impl;

import cn.hutool.core.collection.CollUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.zhixiang.health.modules.sys.model.entity.SysUserDept;
import com.zhixiang.health.modules.sys.mapper.SysUserDeptMapper;
import com.zhixiang.health.modules.sys.service.ISysUserDeptService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

/**
 * <p>
 * 用户部门表 服务实现类
 * </p>
 *
 * @author HeJiawang
 * @since 2020-05-08
 */
@Service
public class SysUserDeptServiceImpl extends ServiceImpl<SysUserDeptMapper, SysUserDept> implements ISysUserDeptService {

    @Resource
    private SysUserDeptMapper mapper;

    /**
     * 重新设置用户归属的部门信息
     * @param userId 用户id
     * @param deptIdList 部门id集合
     * @return boolean
     */
    @Transactional
    @Override
    public boolean merge(Integer userId, List<Integer> deptIdList) {
        mapper.deleteByUserId(userId);

        if (CollUtil.isNotEmpty(deptIdList)) {
            deptIdList.forEach(deptId -> {
                SysUserDept sysUserDept = new SysUserDept()
                        .setDeptId(deptId).setUserId(userId);
                super.save(sysUserDept);
            });
        }

        return true;
    }

    /**
     * 删除用户部门关联信息
     * @param userId 用户id
     * @return true
     */
    @Override
    public boolean removeByUserId(Integer userId) {
        return mapper.deleteByUserId(userId) >= 0;
    }

    /**
     * 统计部门下的人员数量
     * @param deptId deptId
     * @return 人员数量
     */
    @Override
    public long countByDeptId(Integer deptId) {
        QueryWrapper<SysUserDept> wrapper = new QueryWrapper<>(
                new SysUserDept().setDeptId(deptId)
        );

        return super.count(wrapper);
    }
}
