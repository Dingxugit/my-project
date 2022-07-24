package com.zhixiang.health.common.config.json;

import com.alibaba.fastjson.serializer.SerializerFeature;
import com.alibaba.fastjson.support.config.FastJsonConfig;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @Description: FastJsonConfig
 * @Auther: HeJiawang
 * @Date: 2020-04-20
 */
@Configuration
public class FastJson {

    /**
     * 设置MyBatisPlus通用枚举
     * 序列化枚举值为数据库存储值
     * 重写toString方法
     * @return FastJsonConfig
     */
    @Bean
    public FastJsonConfig config() {
        FastJsonConfig config = new FastJsonConfig();
        config.setSerializerFeatures(SerializerFeature.WriteEnumUsingToString);

        return config;
    }
}
