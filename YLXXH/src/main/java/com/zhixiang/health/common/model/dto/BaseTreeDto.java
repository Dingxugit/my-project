package com.zhixiang.health.common.model.dto;

import cn.hutool.core.clone.CloneSupport;
import cn.hutool.core.collection.CollUtil;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.zhixiang.health.common.model.enums.DeleteStateEnum;
import lombok.Data;
import lombok.experimental.Accessors;
import org.hibernate.validator.constraints.Length;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * 树形结构定义
 * @author HeJiawang
 * @since 2020-04-28
 */
@Data
@Accessors(chain = true)
public abstract class BaseTreeDto<T> extends CloneSupport<T> implements Serializable {

    /**
     * 自身id
     */
    private int id;

    /**
     * 父级id
     */
    private int parentId;

    /**
     * 子节点集合
     */
    private List<T> children;

    /**
     * 添加子节点
     * @param node 子节点信息
     */
    public void addChildren(T node) {
        if (CollUtil.isEmpty(children)) {
            children = CollUtil.newArrayList();
        }

        this.children.add(node);
    }

    /**
     * 创建日期
     */
    protected Date createDate;

    /**
     * 创建人
     */
    protected String createBy;

    /**
     * 最后修改日期
     */
    protected Date updateDate;

    /**
     * 最后修改人
     */
    protected String updateBy;

    /**
     * 备注
     */
    @Length(max = 100, message = "备注长度不能超过100")
    protected String remark;

    /**
     * 使用状态 1正在使用 0未使用
     */
    @TableLogic
    protected DeleteStateEnum deleteState;
}
