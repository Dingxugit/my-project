package com.zhixiang.health.common.oauth.bean;

import com.zhixiang.health.common.model.bean.User;
import com.zhixiang.health.modules.sys.model.entity.SysDept;
import lombok.Data;
import lombok.experimental.Accessors;

import java.util.List;

/**
 * @Description: 个性化返回token的信息
 * @Auther: HeJiawang
 * @Date: 2020-04-23
 */
@Data
@Accessors(chain = true)
public class OAuthInfo {

    /**
     * accessToken
     */
    private String accessToken;

    /**
     * refreshToken
     */
    private String refreshToken;

    /**
     * expiresIn
     */
    private int expiresIn;

    /**
     * 角色编码list
     */
    private List<String> roleCodeList;

    /**
     * 登录用户信息
     */
    private User user;

    /**
     * 部门信息
     */
    private SysDept dept ;
}
