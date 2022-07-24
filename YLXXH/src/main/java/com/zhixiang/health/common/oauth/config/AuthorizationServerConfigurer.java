package com.zhixiang.health.common.oauth.config;

import com.zhixiang.health.common.oauth.bean.SecurityConstants;
import com.zhixiang.health.common.oauth.bean.OAuthConstant;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.DefaultTokenServices;
import org.springframework.security.oauth2.provider.token.TokenEnhancerChain;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.redis.RedisTokenStore;

import javax.annotation.Resource;
import java.util.Arrays;

/**
 * @Description: 授权服务器配置
 * @Auther: HeJiawang
 * @Date: 2020-04-22
 */
@Configuration
@EnableAuthorizationServer
public class AuthorizationServerConfigurer extends AuthorizationServerConfigurerAdapter {

    @Resource
    private RedisConnectionFactory redisConnectionFactory;

    @Resource
    private AuthenticationManager authenticationManager;

    @Resource
    private UserDetailsService userDetailsService;

    @Resource
    private OAuthConstant oAuthConstant;

    /**
     * 定义客户端详细信息服务
     * @param clients ClientDetailsServiceConfigurer
     * @throws Exception Exception
     */
    @Override
    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
        clients.inMemory()
                .withClient(SecurityConstants.CLIENT_ID)
                .secret(passwordEncoder().encode(SecurityConstants.CLIENT_SECRET))
                .authorizedGrantTypes(SecurityConstants.GRANT_TYPES_REFRESH_TOKEN, SecurityConstants.GRANT_TYPES_PASSWORD)
                .scopes(SecurityConstants.SCOPES)
                .autoApprove(true);
    }

    /**
     * 定义令牌端点上的安全约束
     * @param security AuthorizationServerSecurityConfigurer
     * @throws Exception Exception
     */
    @Override
    public void configure(AuthorizationServerSecurityConfigurer security) throws Exception {
        security.allowFormAuthenticationForClients();   // 启用客户端认证
    }

    /**
     * 定义授权和令牌端点以及令牌服务。
     * @param endpoints AuthorizationServerEndpointsConfigurer
     * @throws Exception Exception
     */
    @Override
    public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
        // 增强token信息
        TokenEnhancerChain tokenEnhancerChain = new TokenEnhancerChain();
        tokenEnhancerChain.setTokenEnhancers(
                Arrays.asList( jwtAccessTokenConverter() )
        );

        endpoints.tokenEnhancer(tokenEnhancerChain)
                .tokenStore(redisTokenStore())
                .authenticationManager(authenticationManager)
                .userDetailsService(userDetailsService);

        this.buildTokenServices(endpoints);
    }

    /**
     * 启用支持使用refresh token
     * @param endpoints endpoints
     */
    private void buildTokenServices(AuthorizationServerEndpointsConfigurer endpoints) {
        DefaultTokenServices tokenServices =
                (DefaultTokenServices) endpoints.getDefaultAuthorizationServerTokenServices();
        tokenServices.setTokenStore(endpoints.getTokenStore());
        tokenServices.setClientDetailsService(endpoints.getClientDetailsService());
        tokenServices.setTokenEnhancer(endpoints.getTokenEnhancer());
        // 支持使用refresh token刷新access token
        tokenServices.setSupportRefreshToken(true);
        // 允许重复使用refresh token
        tokenServices.setReuseRefreshToken(true);
        // access token有效期2个小时
        tokenServices.setAccessTokenValiditySeconds(oAuthConstant.getAccessTokenTimeout());
        // refresh token有效期30天
        tokenServices.setRefreshTokenValiditySeconds(oAuthConstant.getRefreshTokenTimeout());

        endpoints.tokenServices(tokenServices);
    }

    /**
     * JwtAccessTokenConverter Bean
     * @return JwtAccessTokenConverter
     */
    @Bean
    public JwtAccessTokenConverter jwtAccessTokenConverter() {
        JwtAccessTokenConverter jwtAccessTokenConverter = new JwtAccessTokenConverter();
        jwtAccessTokenConverter.setSigningKey(SecurityConstants.JWT_KEY);

        return jwtAccessTokenConverter;
    }

    /**
     * redis token store
     * @return TokenStore
     */
    @Bean
    public TokenStore redisTokenStore() {
        RedisTokenStore tokenStore = new RedisTokenStore(redisConnectionFactory);
        tokenStore.setPrefix(SecurityConstants.TOKEN_STORE_PRE);

        return tokenStore;
    }

    /**
     * 加密方式
     * @return PasswordEncoder
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
