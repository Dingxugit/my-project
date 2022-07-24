package com.zhixiang.health.modules.sys.model.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.zhixiang.health.common.model.entity.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 部门信息
 * </p>
 *
 * @author HeJiawang
 * @since 2020-04-28
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
public class SysDept extends BaseEntity<SysDept> {

    private static final long serialVersionUID=1L;

    /**
     * 主键ID
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 上级部门ID, null为顶级部门
     */
    private Integer parentId;

    /**
     * 部门名称
     */
    private String name;

    /**
     * 机构部门简称
     */
    private String simpleName;

    /**
     * 部门编码
     */
    private String code;

    /**
     * 排序值
     */
    private Integer sort;

}
