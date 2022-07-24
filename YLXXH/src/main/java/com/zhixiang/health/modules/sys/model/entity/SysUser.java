package com.zhixiang.health.modules.sys.model.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.zhixiang.health.common.model.entity.BaseEntity;
import com.zhixiang.health.common.model.enums.SexEnum;
import com.zhixiang.health.common.model.enums.UsedStateEnum;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.util.Date;

/**
 * @Description: 系统用户信息
 * @Auther: HeJiawang
 * @Date: 2020-04-20
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
public class SysUser extends BaseEntity<SysUser> {

    private static final long serialVersionUID=1L;

    /**
     * 主键ID
     */
    @TableId(value = "id", type = IdType.AUTO)
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
}
