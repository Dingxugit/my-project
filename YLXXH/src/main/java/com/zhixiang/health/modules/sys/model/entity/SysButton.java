package com.zhixiang.health.modules.sys.model.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.zhixiang.health.common.model.entity.BaseEntity;
import com.zhixiang.health.modules.sys.model.enums.SysButtonMethodEnum;
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
public class SysButton extends BaseEntity<SysButton> {

    private static final long serialVersionUID=1L;

    /**
     * 主键ID
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 按钮名称
     */
    private String name;

    /**
     * 权限标识
     */
    private String permission;

    /**
     * 请求链接
     */
    private String url;

    /**
     * 请求方法  1:Get，2:Post，3:Put，4:Delete
     */
    private SysButtonMethodEnum method;

}
