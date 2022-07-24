package com.zhixiang.health.common.http.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import javax.annotation.Resource;
import java.util.List;

/**
 * WebMvcConfigurer
 *
 * @author HeJiawang
 * @since 2020-05-29
 */
@Slf4j
@Configuration
public class WebConfig implements WebMvcConfigurer {

    /**
     * 将token转换为 UserParam将token转换为 UserParam
     */
    @Resource
    private TokenArgumentResolverConfig tokenArgumentResolverConfig;

    /**
     * 参数转换
     * @param argumentResolvers argumentResolvers
     */
    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> argumentResolvers) {
        argumentResolvers.add(tokenArgumentResolverConfig);
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/assets/lqbz/**").addResourceLocations("file:/D:/file/lqbz/");
        registry.addResourceHandler("/assets/ylshh/**").addResourceLocations("file:/D:/file/ylshh/");
    }
}
