package com.zhixiang.health.common.model.bean;

import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * @Description: 角色信息
 * @Auther: HeJiawang
 * @Date: 2020-04-23
 */
@Data
@Accessors(chain = true)
public class Role implements Serializable {

    /**
     * 角色id
     */
    private Integer id;

    /**
     * 角色名称
     */
    private String name;

    /**
     * 角色编码
     */
    private String code;
}
