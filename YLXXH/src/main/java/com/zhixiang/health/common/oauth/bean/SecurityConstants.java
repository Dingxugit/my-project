package com.zhixiang.health.common.oauth.bean;

/**
 * @Description: OAuth2 权限认证 常量定义
 * @Auther: HeJiawang
 * @Date: 2020-04-22
 */
public interface SecurityConstants {

    /* oauth2 认证信息 start */
    String CLIENT_ID = "jmonkey_client_id";
    String CLIENT_SECRET = "jmonkey_client_secret";
    String GRANT_TYPES_PASSWORD = "password";
    String GRANT_TYPES_REFRESH_TOKEN = "refresh_token";
    String SCOPES = "all";
    /* oauth2 认证信息 end */

    // JwtAccessToken key JMONKEY_JWT_KEY
    String JWT_KEY = "JMONKEY_JWT_KEY";

    // redisTokenStore pre
    String TOKEN_STORE_PRE = "JMonkey_OAuth_";

    // token请求头名称
    String REQ_HEADER = "Authorization";

    // token分割符
    String TOKEN_SPLIT = "Bearer ";

    /**
     * 基础角色
     */
    String BASE_ROLE = "ROLE_BASE";
}
