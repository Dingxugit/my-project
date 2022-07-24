package com.zhixiang.health.modules.sys.mapper;

import com.zhixiang.health.modules.sys.model.entity.SysDept;
import com.zhixiang.health.modules.sys.model.entity.SysUserDept;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 * 用户部门表 Mapper 接口
 * </p>
 *
 * @author HeJiawang
 * @since 2020-05-08
 */
public interface SysUserDeptMapper extends BaseMapper<SysUserDept> {

    /**
     * 根据用户id，删除用户部门关联信息
     * @param userId 用户id
     * @return 删除个数
     */
    int deleteByUserId(@Param("userId") Integer userId);

    SysDept getOauthUser(Integer userId);
}
