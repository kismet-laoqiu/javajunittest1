package com.example.demo.util;

import freemarker.template.Configuration;
import freemarker.template.Template;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.Map;

/**
 * @ClassName: GenerateSolidity
 * @Description: 生成自动理赔合约
 * @Author: 刘敬
 * @Date: 2019/8/15 19:30
 **/
public class GenerateSolidity {
    public static void Generate(String name, Map<String, String> map) {
        map.put("contractName", name);
        // 模板目录
        String templateDirectory = System.getProperty("user.dir") + "\\solidity";
        // 模板名称
        String templateFile = "InsuranceClaimTemplate.sol";
        // 模板生成后存放目录
        String targetPath = System.getProperty("user.dir") + "\\solidity\\InsuranceClaim";
        // 模板生成后新文件名
        String fileName = name + ".sol";
        // 创建文件夹
        File nFile = new File(targetPath + "\\" + fileName);
        if (nFile.exists()) {
            return;
        }
        // 生成目标文件
        Writer writer = null;
        try {
            writer = new FileWriter(nFile);
            Template template = getConfiguration(templateDirectory).getTemplate(templateFile, "UTF-8");
            template.process(map, writer);
            writer.close();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private static Configuration getConfiguration(String templateDirectory) {
        Configuration configuration = new Configuration(Configuration.VERSION_2_3_22);
        try {
            configuration.setTagSyntax(Configuration.AUTO_DETECT_TAG_SYNTAX);
            configuration.setDirectoryForTemplateLoading(new File(templateDirectory));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return configuration;
    }
}
