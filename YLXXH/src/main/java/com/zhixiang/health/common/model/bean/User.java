package com.zhixiang.health.common.model.bean;

import com.zhixiang.health.common.model.enums.DeleteStateEnum;
import com.zhixiang.health.common.model.enums.SexEnum;
import com.zhixiang.health.common.model.enums.UsedStateEnum;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * @Description: 用户信息
 * @Auther: HeJiawang
 * @Date: 2020-04-23
 */
@Data
@Accessors(chain = true)
public class User implements Serializable {

    /**
     * 用户id
     */
    private Integer id;
    /**
     * 用户登陆名称
     */
    private String userName;
    /**
     * 用户登陆密码
     */
    private String password;
    /**
     * 用户真实姓名
     */
    private String realName;
    /**
     * 用户手机号码
     */
    private String phone;
    /**
     * 出生日期
     */
    private Date birthday;
    /**
     * 性别
     */
    private SexEnum sex;
    /**
     * 用户头像
     */
    private String photo;
    /**
     * 启用状态 1启用 0禁用
     */
    private UsedStateEnum usedState;
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
    protected String remark;
    /**
     * 删除状态 1已删除 0未删除
     */
    protected DeleteStateEnum deleteState;
    /**
     * 角色集合
     */
    private List<Role> roleList;
}
