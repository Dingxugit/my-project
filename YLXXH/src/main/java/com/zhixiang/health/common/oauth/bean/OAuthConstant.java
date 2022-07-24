package com.zhixiang.health.common.oauth.bean;

import lombok.Data;
import lombok.experimental.Accessors;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * @Description: 配置OAuth信息
 * @Auther: HeJiawang
 * @Date: 2020-04-24
 */
@Data
@Accessors(chain = true)
@Configuration
@ConfigurationProperties(prefix = "security.oauth2")
public class OAuthConstant {

    /**
     * access_token过期时间(秒)
     * 默认43200秒 = 12小时
     */
    @Value("43200")
    private int accessTokenTimeout;

    /**
     * refresh_token过期时间(秒)
     * 默认2592000秒 = 30天
     */
    @Value("2592000")
    private int refreshTokenTimeout;
}
