package com.zhixiang.health.modules.sys.service;

import com.zhixiang.health.modules.sys.model.entity.SysDept;
import com.zhixiang.health.modules.sys.model.entity.SysUserDept;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * 用户部门表 服务类
 * </p>
 *
 * @author HeJiawang
 * @since 2020-05-08
 */
public interface ISysUserDeptService extends IService<SysUserDept> {

    /**
     * 重新设置用户归属的部门信息
     * @param userId 用户id
     * @param deptIdList 部门id集合
     * @return boolean
     */
    boolean merge(Integer userId, List<Integer> deptIdList);

    /**
     * 删除用户部门关联信息
     * @param userId 用户id
     * @return true
     */
    boolean removeByUserId(Integer userId);

    /**
     * 统计部门下的人员数量
     * @param deptId deptId
     * @return 人员数量
     */
    long countByDeptId(Integer deptId);

}
