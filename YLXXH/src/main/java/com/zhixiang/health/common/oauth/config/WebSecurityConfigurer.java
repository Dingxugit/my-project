package com.zhixiang.health.common.oauth.config;

import com.zhixiang.health.common.oauth.bean.UserDetail;
import com.zhixiang.health.modules.sys.service.ISysUserService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.BeanIds;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;

import javax.annotation.Resource;


/**
 * @Description: 启用spring security配置
 * @Auther: HeJiawang
 * @Date: 2020-04-22
 */
@Configuration
@EnableWebSecurity
public class WebSecurityConfigurer extends WebSecurityConfigurerAdapter {

    @Resource
    private ISysUserService userService;

    /**
     * 配置HttpSecurity相关信息
     * @param http HttpSecurity
     * @throws Exception Exception
     */
    @Override
    public void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests().anyRequest().authenticated()
                .and().csrf().disable();
    }

    /**
     * AuthenticationManager
     * @return AuthenticationManager
     * @throws Exception Exception
     */
    @Bean(name = BeanIds.AUTHENTICATION_MANAGER)
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    /**
     * UserDetailsService
     * @return UserDetailsService
     * @throws Exception Exception
     */
    @Bean(name = BeanIds.USER_DETAILS_SERVICE)
    @Override
    public UserDetailsService userDetailsServiceBean() throws Exception {
        return (username) -> new UserDetail(userService.getOauthUser(username));
    }
}
