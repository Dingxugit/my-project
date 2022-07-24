package com.zhixiang.health.modules.sys.model.dto;

import com.zhixiang.health.modules.sys.model.entity.SysDept;
import com.zhixiang.health.modules.sys.model.entity.SysRole;
import com.zhixiang.health.modules.sys.model.entity.SysUser;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.util.List;

/**
 * @Description: 系统用户信息,以及用户的部门、角色信息
 * @Auther: HeJiawang
 * @Date: 2020-05-11
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
public class SysUserDto extends SysUser {

    /**
     * 用户归属部门信息集合
     */
    private List<SysDept> deptList;

    /**
     * 用户角色信息集合
     */
    private List<SysRole> roleList;
}
