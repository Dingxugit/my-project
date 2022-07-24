package com.zhixiang.health.modules.sys.model.dto;

import com.zhixiang.health.modules.sys.model.entity.SysResource;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 系统资源表DTO信息
 * </p>
 *
 * @author HeJiawang
 * @since 2020-06-29
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
public class SysResourceDto extends SysResource {

    /**
     * 资源名称
     */
    private String name;

    /**
     * 系统资源类型名称
     */
    private String typeName;
}
