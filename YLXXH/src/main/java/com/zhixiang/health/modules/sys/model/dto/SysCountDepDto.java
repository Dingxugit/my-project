package com.zhixiang.health.modules.sys.model.dto;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * 部门用户统计
 * @author HeJiawang
 * @since 2020-07-09
 */
@Data
@Accessors(chain = true)
public class SysCountDepDto {

    private Integer id;

    /**
     * 部门名称
     */
    private String deptName;

    /**
     * 用户数量
     */
    private int userNum;

    /**
     * 登录次数
     */
    private int loginNum;

}
