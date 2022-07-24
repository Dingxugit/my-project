package com.zhixiang.health.modules.sys.model.param;

import cn.hutool.core.bean.BeanUtil;
import com.zhixiang.health.modules.sys.model.entity.SysMenu;
import com.zhixiang.health.modules.sys.model.entity.SysResource;
import com.zhixiang.health.modules.sys.model.enums.SysResourceTypeEnum;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 保存菜单配置参数
 * </p>
 *
 * @author HeJiawang
 * @since 2020-06-25
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
public class SysMenuSaveParam extends SysMenu {

    /**
     * 父资源ID，指向sys_resource.id
     */
    private Integer parentId;

    /**
     * convertToEntity
     * @return entity
     */
    public SysMenu convertToEntity() {
        SysMenu entity = new SysMenu();
        BeanUtil.copyProperties(this, entity);

        return entity;
    }

    /**
     * convertToResource
     * @return SysResource
     */
    public SysResource convertToResource() {
        SysResource resource = new SysResource()
                .setType(SysResourceTypeEnum.MENU)
                .setParentId(this.parentId)
                .setResourceId(this.getId());

        return resource;
    }
}
