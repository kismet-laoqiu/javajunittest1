package com.example.demo.util.cloudStorage;

import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import com.example.demo.config.Config;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

/**
 * @ClassName: ALiYunOSS
 * @Description: 阿里云oss
 * @Author: 刘敬
 * @Date: 2019/8/7 15:03
 **/
public class ALiYunOSS {// 头像图片之类的信息放在阿里云上

    public static String uploadFile(File file, String fileName) {
        OSS ossClient = new OSSClientBuilder().build(Config.endpoint, Config.accessKeyId, Config.accessKeySecret);
        ossClient.putObject(Config.bucketName, Config.filedir + fileName, file);
        ossClient.shutdown();
        return "https://" + Config.bucketName + "." + Config.endpoint + "/" + Config.filedir + fileName;
    }

    public static String uploadMultipartFile(MultipartFile file, String fileName) {
        OSS ossClient = new OSSClientBuilder().build(Config.endpoint, Config.accessKeyId, Config.accessKeySecret);
        try {
            ossClient.putObject(Config.bucketName, Config.filedir + fileName, file.getInputStream());
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            ossClient.shutdown();
        }
        return "https://" + Config.bucketName + "." + Config.endpoint + "/" + Config.filedir + fileName;
    }
}
