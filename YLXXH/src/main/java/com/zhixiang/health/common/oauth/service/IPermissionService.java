package com.zhixiang.health.common.oauth.service;

import org.springframework.security.core.Authentication;

import javax.servlet.http.HttpServletRequest;

/**
 * @Description: 权限验证 service
 * @Auther: HeJiawang
 * @Date: 2020-04-23
 */
public interface IPermissionService {

    /**
     * 权限验证
     * @param request request
     * @param authentication authentication
     * @return boolean
     */
    boolean hasPermission(HttpServletRequest request, Authentication authentication);
}
