package com.zhixiang.health.modules.sys.model.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.zhixiang.health.common.model.entity.BaseEntity;
import com.zhixiang.health.modules.sys.model.enums.SysResourceTypeEnum;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 系统资源表
 * </p>
 *
 * @author HeJiawang
 * @since 2020-06-24
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
public class SysResource extends BaseEntity<SysResource> {

    private static final long serialVersionUID=1L;

    /**
     * 主键ID
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 父资源ID，指向sys_resource.id
     */
    private Integer parentId;

    /**
     * 资源类型 1:System 2:Menu 3:Button
     */
    private SysResourceTypeEnum type;

    /**
     * 资源id 例 type==System r_id==sys_system.id
     */
    private Integer resourceId;

}
