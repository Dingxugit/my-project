package com.zhixiang.health.common.oauth.listener;

import org.springframework.context.ApplicationListener;
import org.springframework.security.authentication.event.AuthenticationFailureBadCredentialsEvent;
import org.springframework.stereotype.Component;

/**
 * @Description: 认证失败监听事件
 * @Auther: HeJiawang
 * @Date: 2020-04-20
 */
@Component
public class AuthenticationFailureListener implements ApplicationListener<AuthenticationFailureBadCredentialsEvent> {

    /**
     * 执行认证失败事件
     * @param event event
     */
    @Override
    public void onApplicationEvent(AuthenticationFailureBadCredentialsEvent event) {
        // 当请求接口没权限时，也触发了

        if (event.getAuthentication().getPrincipal() instanceof String) {
            // System.out.println("认证失败监听事件");
        }
    }
}
