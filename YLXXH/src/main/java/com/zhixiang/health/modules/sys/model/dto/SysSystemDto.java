package com.zhixiang.health.modules.sys.model.dto;

import com.zhixiang.health.modules.sys.model.entity.SysSystem;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 系统配置 DTO信息
 * </p>
 *
 * @author HeJiawang
 * @since 2020-06-24
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
public class SysSystemDto extends SysSystem {

    /**
     * 系统类型名称
     */
    private String typeName;

    /**
     * 系统资源在sys_resource表中的资源id，即sys_resource.id
     */
    private Integer resourceId;

    /**
     * 首页路由path
     */
    private String indexPath;
}
