package com.example.demo.util.cloudStorage;

import org.springframework.web.multipart.MultipartFile;

import java.io.File;

/**
 * @ClassName: CloudStorage
 * @Description:
 * @Author: 刘敬
 * @Date: 2019/8/7 15:01
 **/
public class CloudStorage {
    public static String uploadFile(File file, String fileName) {
        return ALiYunOSS.uploadFile(file, fileName);
    }

    public static String uploadMultipartFile(MultipartFile file, String fileName) {
        return ALiYunOSS.uploadMultipartFile(file, fileName);
    }

}
