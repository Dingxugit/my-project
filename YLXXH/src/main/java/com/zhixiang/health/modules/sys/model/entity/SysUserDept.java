package com.zhixiang.health.modules.sys.model.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.zhixiang.health.common.model.entity.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 用户部门表
 * </p>
 *
 * @author HeJiawang
 * @since 2020-05-08
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
public class SysUserDept extends BaseEntity<SysUserDept> {

    private static final long serialVersionUID=1L;

    /**
     * 用户ID
     */
    @TableId(type = IdType.INPUT)
    private Integer userId;

    /**
     * 部门ID
     */
    @TableId(type = IdType.INPUT)
    private Integer deptId;

}
