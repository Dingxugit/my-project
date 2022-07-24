package com.zhixiang.health.common.oauth.config;

import com.zhixiang.health.common.oauth.bean.FilterUrls;
import com.zhixiang.health.common.oauth.handler.AccessDeniedHandler;
import com.zhixiang.health.common.oauth.handler.AuthExceptionHandler;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.ExpressionUrlAuthorizationConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.expression.OAuth2WebSecurityExpressionHandler;

import javax.annotation.Resource;

/**
 * @Description: 资源服务器配置
 * @Auther: HeJiawang
 * @Date: 2020-04-22
 */
@Configuration
@EnableResourceServer
public class ResourceServerConfigurer extends ResourceServerConfigurerAdapter {

    @Resource
    private OAuth2WebSecurityExpressionHandler expressionHandler;

    @Resource
    private AccessDeniedHandler accessDeniedHandler;

    @Resource
    private FilterUrls filterUrls;

    /**
     * 配置资源服务安全机制 HttpSecurity
     * @param http http
     * @throws Exception Exception
     */
    @Override
    public void configure(HttpSecurity http) throws Exception {
        ExpressionUrlAuthorizationConfigurer<HttpSecurity>.ExpressionInterceptUrlRegistry registry
                = http.authorizeRequests();

        filterUrls.getPermits().forEach(url -> registry.antMatchers(url).permitAll());
        registry.anyRequest().access("@permissionService.hasPermission(request, authentication)");
    }

    /**
     * ResourceServerSecurityConfigurer
     * @param resources resources
     */
    @Override
    public void configure(ResourceServerSecurityConfigurer resources) {
        // 处理 no bean resolver registered的问题
        resources.expressionHandler(expressionHandler);

        // 个性化token异常、过期的提示
        resources.authenticationEntryPoint(new AuthExceptionHandler());

        // 授权失败
        resources.accessDeniedHandler(accessDeniedHandler);
    }

    /**
     * 处理 no bean resolver registered的问题
     *      registry.anyRequest().access("@permissionService.hasPermission(request, authentication)");
     *
     * @param applicationContext applicationContext
     * @return OAuth2WebSecurityExpressionHandler
     */
    @Bean
    public OAuth2WebSecurityExpressionHandler oAuth2WebSecurityExpressionHandler(ApplicationContext applicationContext) {
        OAuth2WebSecurityExpressionHandler expressionHandler = new OAuth2WebSecurityExpressionHandler();
        expressionHandler.setApplicationContext(applicationContext);
        return expressionHandler;
    }
}
