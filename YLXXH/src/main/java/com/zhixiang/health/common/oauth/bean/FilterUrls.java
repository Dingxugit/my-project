package com.zhixiang.health.common.oauth.bean;

import cn.hutool.core.collection.CollectionUtil;
import lombok.Data;
import lombok.experimental.Accessors;
import org.springframework.boot.autoconfigure.condition.ConditionalOnExpression;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.List;

/**
 * @Description: FilterUrls
 * @Auther: HeJiawang
 * @Date: 2020-04-24
 */
@Data
@Accessors(chain = true)
@Configuration
@ConditionalOnExpression("!'${security.oauth2.urls}'.isEmpty()")
@ConfigurationProperties(prefix = "security.oauth2.urls")
public class FilterUrls {

    /**
     * 过滤不需要权限的请求
     */
    private List<String> permits = CollectionUtil.newArrayList();
}
