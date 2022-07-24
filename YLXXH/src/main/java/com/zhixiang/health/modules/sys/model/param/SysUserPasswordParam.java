package com.zhixiang.health.modules.sys.model.param;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * @Description: 修改用户密码参数
 * @Auther: HeJiawang
 * @Date: 2020-07-06
 */
@Data
@Accessors(chain = true)
public class SysUserPasswordParam {

    /**
     * 用户id
     */
    private Integer id;

    /**
     * 旧密码
     */
    private String oldPassword;

    /**
     * 新密码
     */
    private String newPassword;

    /**
     * 确认密码
     */
    private String checkPassword;
}
