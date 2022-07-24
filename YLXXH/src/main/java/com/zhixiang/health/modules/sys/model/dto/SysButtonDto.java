package com.zhixiang.health.modules.sys.model.dto;

import com.zhixiang.health.modules.sys.model.entity.SysButton;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 按钮及访问权限配置
 * </p>
 *
 * @author HeJiawang
 * @since 2020-06-24
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
public class SysButtonDto extends SysButton {

    /**
     * 上级资源id
     */
    private Integer parentId;
}
