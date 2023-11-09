package com.ramelon.security.mpgenerate;

import com.baomidou.mybatisplus.generator.FastAutoGenerator;
import com.baomidou.mybatisplus.generator.config.OutputFile;
import com.baomidou.mybatisplus.generator.engine.FreemarkerTemplateEngine;

import java.util.Collections;

/**
 * @description:
 * @author: xzy
 * @time: 2023/11/8 16:39
 */
public class AutoGeneratorUtils {
    public static void main(String[] args) {
        FastAutoGenerator.create("jdbc:mysql://127.0.0.1:13306/spring_security", "root", "123456")
                .globalConfig(builder -> {
                    builder.author("xzy") // 设置作者
                            .fileOverride() // 覆盖已生成文件
                            .outputDir("G://"); // 指定输出目录
                })
                .packageConfig(builder -> {
                    builder.parent("com.ramelon") // 设置父包名
                            .moduleName("security") // 设置父包模块名
                            .pathInfo(Collections.singletonMap(OutputFile.xml, "G://")); // 设置mapperXml生成路径
                })
                .strategyConfig(builder -> {
                    builder.addInclude("user") // 设置需要生成的表名
                            .addTablePrefix("t_", "c_"); // 设置过滤表前缀
                })
                .templateEngine(new FreemarkerTemplateEngine()) // 使用Freemarker引擎模板，默认的是Velocity引擎模板
                .execute();
    }
}