package com.zhixiang.health.modules.sys.model.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.zhixiang.health.common.model.entity.BaseEntity;
import com.zhixiang.health.common.model.enums.UsedStateEnum;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 定时任务配置表
 * </p>
 *
 * @author HeJiawang
 * @since 2020-04-26
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
public class SysTask extends BaseEntity<SysTask> {

    private static final long serialVersionUID=1L;

    /**
     * 主键ID
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 任务名
     */
    private String name;

    /**
     * 任务组,关联字典表,sys_task_group
     */
    @TableField("`group`")
    private String group;

    /**
     * 定时规则
     */
    private String rule;

    /**
     * 任务类
     */
    private String className;

    /**
     * 启用状态 1启用 0禁用
     */
    private UsedStateEnum usedState;

}
