package com.zhixiang.health.common;

import com.zhixiang.health.common.utils.CodeGeneratorUtil;
import org.junit.jupiter.api.Test;

/**
 * @Description: 代码生成器
 * @Date: 2020-04-21
 */
public class CodeGenerator {

    @Test
    void run() {
        CodeGeneratorUtil.getInstance()
                // .setDbUrl("jdbc:mysql://192.168.1.182:3306/jmonkey3?useUnicode=true&useSSL=false&characterEncoding=utf8")
//                .setAuthor("Dingx")
                .setTableName("t_swy_dcyh")
                .build()
                .execute();
    }
}
