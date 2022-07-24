package com.zhixiang.health.modules.sys.model.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.zhixiang.health.common.model.entity.BaseEntity;
import com.zhixiang.health.modules.sys.model.enums.SysMenuIndexEnum;
import com.zhixiang.health.modules.sys.model.enums.SysMenuShowTypeEnum;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 菜单配置
 * </p>
 *
 * @author HeJiawang
 * @since 2020-06-24
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
public class SysMenu extends BaseEntity<SysMenu> {

    private static final long serialVersionUID=1L;

    /**
     * 主键ID
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

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
}
