package com.zhixiang.health.modules.sys.model.param;

import com.zhixiang.health.modules.sys.model.entity.SysUser;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
public class SysUserSearchParam extends SysUser {

    /**
     * 角色id
     */
    private String roleId;

    /**
     * 部门id
     */
    private String deptId;

    /**
     * 每页显示条数，默认 10
     */
    private long pageSize = 10;

    /**
     * 当前页
     */
    private long current = 1;

    /**
     * limitStart
     */
    private long limitStart;

    /**
     * renderLimitStart
     * @return this
     */
    public SysUserSearchParam renderLimitStart() {
        this.limitStart = pageSize * ( current - 1 );

        return this;
    }
}
