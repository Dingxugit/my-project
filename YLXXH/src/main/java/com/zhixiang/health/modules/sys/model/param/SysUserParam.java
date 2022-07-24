package com.zhixiang.health.modules.sys.model.param;

import cn.hutool.core.bean.BeanUtil;
import com.zhixiang.health.modules.sys.model.entity.SysUser;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.util.List;

/**
 * @Description: 保存、修改系统用户信息时的参数
 * @Auther: HeJiawang
 * @Date: 2020-04-20
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
public class SysUserParam extends SysUser {

    /**
     * 角色id集合
     */
    private List<Integer> roleIdList;

    /**
     * 部门id集合
     */
    private List<Integer> deptIdList;

    /**
     * 转换为实体对象
     * @return SysUser
     */
    public SysUser convertToEntity() {
        SysUser user = new SysUser();
        BeanUtil.copyProperties(this, user);

        return user;
    }
}
