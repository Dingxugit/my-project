package com.zhixiang.health.modules.sys.model.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.zhixiang.health.common.model.entity.BaseEntity;
import com.zhixiang.health.modules.sys.model.enums.SysSystemGuideEnum;
import com.zhixiang.health.modules.sys.model.enums.SysSystemShowTypeEnum;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 系统配置
 * </p>
 *
 * @author HeJiawang
 * @since 2020-06-24
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
public class SysSystem extends BaseEntity<SysSystem> {

    private static final long serialVersionUID=1L;

    /**
     * 主键ID
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 系统类型，关联字典表，sys_system_type
     */
    private String type;

    /**
     * 系统名称
     */
    private String name;

    /**
     * 系统简介
     */
    @TableField("`describe`")
    private String describe;

    /**
     * 图标
     */
    private String icon;

    /**
     * 是否需要引导页，1是，0否
     */
    private SysSystemGuideEnum isGuide;

    /**
     * 进入系统方式，0 _SELF，1 _BLANK
     */
    private SysSystemShowTypeEnum showType;

    /**
     * 系统进入方式为blank时的路径
     */
    private String blankPath;

    /**
     * 排序值
     */
    private Integer sort;

}
