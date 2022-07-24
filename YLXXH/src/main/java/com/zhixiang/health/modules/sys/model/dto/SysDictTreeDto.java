package com.zhixiang.health.modules.sys.model.dto;

import com.zhixiang.health.common.model.dto.BaseTreeDto;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * 字典树
 * @author HeJiawang
 * @since 2020-05-03
 */
@Data
@Accessors(chain = true)
public class SysDictTreeDto extends BaseTreeDto<SysDictTreeDto> {

    /**
     * 字典标签
     */
    private String label;

    /**
     * 字典键值
     */
    private String value;

    /**
     * 排序值
     */
    private Integer sort;
}
