package com.zhixiang.health.modules.sys.model.param;

import cn.hutool.core.bean.BeanUtil;
import com.zhixiang.health.modules.sys.model.entity.SysButton;
import com.zhixiang.health.modules.sys.model.entity.SysResource;
import com.zhixiang.health.modules.sys.model.enums.SysResourceTypeEnum;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 按钮及访问权限配置保存参数
 * </p>
 *
 * @author HeJiawang
 * @since 2020-06-25
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
public class SysButtonSaveParam extends SysButton {

    /**
     * 父资源ID，指向sys_resource.id
     */
    private Integer parentId;

    /**
     * convertToEntity
     * @return entity
     */
    public SysButton convertToEntity() {
        SysButton entity = new SysButton();
        BeanUtil.copyProperties(this, entity);

        return entity;
    }

    /**
     * convertToResource
     * @return SysResource
     */
    public SysResource convertToResource() {
        SysResource resource = new SysResource()
                .setType(SysResourceTypeEnum.BUTTON)
                .setParentId(this.parentId)
                .setResourceId(this.getId());

        return resource;
    }
}
