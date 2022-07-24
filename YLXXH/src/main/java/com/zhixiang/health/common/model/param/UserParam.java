package com.zhixiang.health.common.model.param;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.StrUtil;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.List;

/**
 * Web Config 将 token 转换为 UserParam
 *
 * 使用方法：
 *      将该类放置Api层接口方法中，
 *      Web Config 自动为该类赋值
 *
 * @author HeJiawang
 * @since 2020-05-29
 */
@Data
@Accessors(chain = true)
public class UserParam implements Serializable {

    /**
     * 用户id
     */
    private Integer id;
    /**
     * 用户登陆名称
     */
    private String userName;
    /**
     * 角色id集合
     */
    private List<String> roleIdList;
    /**
     * 角色编码集合
     */
    private List<String> roleCodeList;
    /**
     * 部门id集合
     */
    private List<String> deptIdList;
    /**
     * 部门编码集合
     */
    private List<String> deptCodeList;

    /**
     * 获取第一个角色id
     * @return String
     */
    public String getFirstRoleId() {
        return CollUtil.isEmpty(roleIdList) ? StrUtil.EMPTY : roleIdList.get(0);
    }

    /**
     * 获取第一个角色编码
     * @return String
     */
    public String getFirstRoleCode() {
        return CollUtil.isEmpty(roleCodeList) ? StrUtil.EMPTY : roleCodeList.get(0);
    }

    /**
     * 获取第一个机构id
     * @return String
     */
    public String getFirstDeptId() {
        return CollUtil.isEmpty(deptIdList) ? StrUtil.EMPTY : deptIdList.get(0);
    }

    /**
     * 获取第一个机构编码
     * @return String
     */
    public String getFirstDeptCode() {
        return CollUtil.isEmpty(deptCodeList) ? StrUtil.EMPTY : deptCodeList.get(0);
    }
}
