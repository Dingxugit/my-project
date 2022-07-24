package com.zhixiang.health.common.utils;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.toolkit.StringPool;
import com.baomidou.mybatisplus.generator.AutoGenerator;
import com.baomidou.mybatisplus.generator.InjectionConfig;
import com.baomidou.mybatisplus.generator.config.*;
import com.baomidou.mybatisplus.generator.config.po.TableInfo;
import com.baomidou.mybatisplus.generator.config.rules.NamingStrategy;
import lombok.Data;
import lombok.experimental.Accessors;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @Description: 基于Mybatis-plus的代码生成器工具
 * @Auther: HeJiawang
 * @Date: 2020-04-20
 */
@Data
@Accessors(chain = true)
public class CodeGeneratorUtil {

    /**
     * 代码作者
     */
    private String author = "Dingx";

    /** 数据库链接信息 start **/
    private String dbUrl = "jdbc:mysql://13.181.11.173:3306/ylxxh?serverTimezone=GMT%2B8&allowMultiQueries=true&useUnicode=true&characterEncoding=UTF-8&nullCatalogMeansCurrent=true";
    private String dbUsername = "root";
    private String dbPassword = "123456";
    /** 数据库链接信息 end **/

    /**
     * 模块名称
     */
    private String moduleName;

    /**
     * 要生成代码的表名
     */
    private String tableName;

    private CodeGeneratorUtil() {}

    public static CodeGeneratorUtil getInstance() {
        return new CodeGeneratorUtil();
    }

    /**
     * 构建代码生成器
     * @return 代码生成器
     */
    public AutoGenerator build() {
        if (StrUtil.isBlank(moduleName) && tableName.contains("_")) {
            moduleName = tableName.substring(0, tableName.indexOf("_"));
        }

        if (StrUtil.isBlank(moduleName)) throw new RuntimeException("模块名称不能为空");
        if (StrUtil.isBlank(tableName)) throw new RuntimeException("表名不能为空");

        // 构建代码生成器工具
        AutoGenerator mpg = new AutoGenerator();
        mpg.setGlobalConfig(globalConfig());
        mpg.setDataSource(dataSourceConfig());
        mpg.setPackageInfo(packageConfig());
        mpg.setCfg(injectionConfig());
        mpg.setTemplate(templateConfig());
        mpg.setStrategy(strategyConfig());

        return mpg;
    }

    /**
     * 全局配置
     * @return 全局配置
     */
    private GlobalConfig globalConfig() {
        GlobalConfig gc = new GlobalConfig();
        gc.setOutputDir(System.getProperty("user.dir") + "/src/main/java");
        gc.setAuthor(this.author);
        gc.setActiveRecord(true);
        gc.setBaseResultMap(true);
        gc.setOpen(false);
        gc.setControllerName("%sApi");

        return gc;
    }

    /**
     * 数据源配置
     * @return 数据源配置
     */
    private DataSourceConfig dataSourceConfig() {
        DataSourceConfig dsc = new DataSourceConfig();
        dsc.setUrl(this.dbUrl);
        dsc.setDriverName("com.mysql.cj.jdbc.Driver");
        dsc.setUsername(dbUsername);
        dsc.setPassword(this.dbPassword);

        return dsc;
    }

    /**
     * 包配置
     * @return 包配置
     */
    private PackageConfig packageConfig () {
        PackageConfig pc = new PackageConfig();
        pc.setModuleName(this.moduleName);
        pc.setParent("com.zhixiang.health.modules");
        pc.setController("api");
        pc.setEntity("model.entity");

        return pc;
    }

    /**
     * 自定义配置
     * @return 自定义配置
     */
    private InjectionConfig injectionConfig() {
        InjectionConfig cfg = new InjectionConfig() {
            @Override
            public void initMap() {
                String apiPath = "";
                if (tableName.contains("_")) {
                    for (String path : tableName.split("_")) {
                        apiPath = apiPath + "/" + path;
                    }
                }

                Map<String, Object> map = CollectionUtil.newHashMap();
                map.put("apiPath", apiPath);

                this.setMap(map);
            }
        };

        String templatePath = "/templates/mapper.xml.vm";

        // 自定义输出配置
        List<FileOutConfig> focList = new ArrayList<>();
        // 自定义配置会被优先输出
        focList.add(new FileOutConfig(templatePath) {
            @Override
            public String outputFile(TableInfo tableInfo) {
                // 自定义输出文件名 ， 如果你 Entity 设置了前后缀、此处注意 xml 的名称会跟着发生变化！！
                return System.getProperty("user.dir") + "/src/main/resources/mapper/" + moduleName
                        + "/" + tableInfo.getEntityName() + "Mapper" + StringPool.DOT_XML;
            }
        });
        cfg.setFileOutConfigList(focList);

        return cfg;
    }

    /**
     * 配置模板
     * @return 配置模板
     */
    private TemplateConfig templateConfig () {
        TemplateConfig templateConfig = new TemplateConfig();
        templateConfig.setXml(null);
        return templateConfig;
    }

    /**
     * 策略配置
     * @return 策略配置
     */
    private StrategyConfig strategyConfig() {
        StrategyConfig strategy = new StrategyConfig();
        strategy.setNaming(NamingStrategy.underline_to_camel);
        strategy.setColumnNaming(NamingStrategy.underline_to_camel);
        strategy.setSuperEntityClass("com.zhixiang.health.common.model.entity.BaseEntity");
        strategy.setEntityLombokModel(true);
        strategy.setRestControllerStyle(true);
        // 公共父类
        strategy.setSuperControllerClass("com.zhixiang.health.common.http.abs.BaseHttp");
        // 写于父类中的公共字段
        strategy.setSuperEntityColumns("create_date", "create_by", "update_date", "update_by", "delete_state", "remark");
        strategy.setInclude(this.tableName);

        return strategy;
    }
}
