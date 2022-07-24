package com.zhixiang.health.modules.sys.model.dto;

import com.zhixiang.health.common.model.dto.BaseTreeDto;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * 机构树
 * @author HeJiawang
 * @since 2020-04-28
 */
@Data
@Accessors(chain = true)
public class SysDeptTreeDto extends BaseTreeDto<SysDeptTreeDto> {

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
