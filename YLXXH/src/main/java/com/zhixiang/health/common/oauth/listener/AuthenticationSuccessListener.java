package com.zhixiang.health.common.oauth.listener;

import com.zhixiang.health.common.oauth.bean.UserDetail;
import org.springframework.context.ApplicationListener;
import org.springframework.security.authentication.event.AuthenticationSuccessEvent;
import org.springframework.stereotype.Component;

/**
 * @Description: 认证成功监听事件
 * @Auther: HeJiawang
 * @Date: 2020-04-20
 */
@Component
public class AuthenticationSuccessListener implements ApplicationListener<AuthenticationSuccessEvent> {

    /**
     * 执行认证成功事件
     * @param event event
     */
    @Override
    public void onApplicationEvent(AuthenticationSuccessEvent event) {
        // 当请求接口权限正确时，也触发了

        if (event.getAuthentication().getPrincipal() instanceof UserDetail) {
            // System.out.println("认证成功监听事件");
        }
    }
}
