package com.zhixiang.health.modules.sys.model.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.zhixiang.health.common.model.entity.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 角色权限表
 * </p>
 *
 * @author HeJiawang
 * @since 2020-06-24
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
public class SysRoleResource extends BaseEntity<SysRoleResource> {

    private static final long serialVersionUID=1L;

    /**
     * 角色ID
     */
    @TableId(type = IdType.INPUT)
    private Integer roleId;

    /**
     * 资源ID
     */
    @TableId(type = IdType.INPUT)
    private Integer resourceId;

}
