package com.zhixiang.health.common.oauth.service.impl;

import com.zhixiang.health.common.oauth.service.IPermissionService;
import com.zhixiang.health.modules.sys.model.entity.SysButton;
import com.zhixiang.health.modules.sys.service.ISysButtonService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.util.AntPathMatcher;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * @Description: 权限验证 service impl
 * @Auther: HeJiawang
 * @Date: 2020-04-23
 */
@Service("permissionService")
public class PermissionServiceImpl implements IPermissionService {

    /**
     * 路径比对工具类
     */
    private AntPathMatcher antPathMatcher = new AntPathMatcher();

    @Resource
    private ISysButtonService buttonService;

    /**
     * 权限验证
     * @param request request
     * @param authentication authentication
     * @return boolean
     */
    @Override
    public boolean hasPermission(HttpServletRequest request, Authentication authentication) {
        boolean permission = false;

        List<SysButton> urls = buttonService.listByUserName(authentication.getName());
        for( SysButton button : urls ){
            if (StringUtils.isNotEmpty(button.getUrl())
                    && antPathMatcher.match(button.getUrl(), request.getRequestURI())
                    && request.getMethod().equalsIgnoreCase(button.getMethod().getDesc())) {

                permission = true;
                break;
            }
        }

        return permission;
    }
}
