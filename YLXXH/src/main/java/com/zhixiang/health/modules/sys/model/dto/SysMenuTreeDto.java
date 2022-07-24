package com.zhixiang.health.modules.sys.model.dto;

import com.zhixiang.health.common.model.dto.BaseTreeDto;
import com.zhixiang.health.modules.sys.model.enums.SysMenuIndexEnum;
import com.zhixiang.health.modules.sys.model.enums.SysMenuShowTypeEnum;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * 菜单树
 * @author HeJiawang
 * @since 2020-06-28
 */
@Data
@Accessors(chain = true)
public class SysMenuTreeDto extends BaseTreeDto<SysMenuTreeDto> {

    /**
     * 菜单id
     */
    private Integer menuId;

    /**
     * 菜单名称
     */
    private String name;

    /**
     * 图标
     */
    private String icon;

    /**
     * router路径
     */
    private String path;

    /**
     * 组件路径
     */
    private String component;

    /**
     * 是否是主页，1是，0否
     */
    private SysMenuIndexEnum isIndex;

    /**
     * 页面展示方式  0 HOME  1 SCREEN
     */
    private SysMenuShowTypeEnum showType;

    /**
     * 排序值
     */
    private Integer sort;

    /**
     * 备注
     */
    private String remark;
}
