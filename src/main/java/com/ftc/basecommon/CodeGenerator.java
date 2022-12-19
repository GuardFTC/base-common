package com.ftc.basecommon;

import com.baomidou.mybatisplus.generator.FastAutoGenerator;
import com.baomidou.mybatisplus.generator.config.TemplateType;

/**
 * @author: 冯铁城 [17615007230@163.com]
 * @date: 2022-08-25 17:35:44
 * @describe: 新版本代码自动生成器
 */
public class CodeGenerator {

    /**
     * 文件创建者名称
     */
    private static final String AUTHOR = "冯铁城 [fengtiecheng@cyou-inc.com]";

    /**
     * 数据库配置
     */
    private static final String URL = "jdbc:mysql://localhost:3306/common_test?useUnicode=true&useSSL=false&characterEncoding=utf8";
    private static final String USERNAME = "root";
    private static final String PASSWORD = "root";
    private static final String TABLE_NAME = "test_table";

    public static void main(String[] args) {

        //1.获取项目路径
        String projectPath = System.getProperty("user.dir");

        System.out.println(projectPath);
        System.out.println("D:\\Code\\Projects\\Java\\base-common\\src\\main\\java\\test");

        //2.生成代码自动生成器
        FastAutoGenerator generator = FastAutoGenerator.create(URL, USERNAME, PASSWORD)
                .globalConfig(builder -> builder
                        .author(AUTHOR)
                        .outputDir(projectPath + "/src/main/java")
                        .enableSwagger())
                .packageConfig(builder -> builder
                        .parent("test")
                        .moduleName("local")
                        .entity("entity")
                        .mapper("mapper")
                        .service("service")
                        .serviceImpl("service.impl")
                        .controller("controller"))
                .strategyConfig(builder -> builder
                        .addInclude(TABLE_NAME)
                        .addTablePrefix("test_")
                        .entityBuilder()
                        .formatFileName("%sEntity")
                        .enableChainModel()
                        .enableLombok()
                        .serviceBuilder()
                        .formatServiceFileName("%sService")
                        .formatServiceImplFileName("%sServiceImpl")
                        .controllerBuilder()
                        .enableRestStyle()
                        .mapperBuilder()
                        .enableMapperAnnotation())
                .templateConfig(builder -> builder
                        .disable(TemplateType.XML));

        //3.执行
        generator.execute();
    }
}
