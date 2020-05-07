package com.example.demo.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;

/**
 * @ClassName: IOUtil
 * @Description: I/O操作
 * @Author: 刘敬
 * @Date: 2019/6/13 15:50
 **/
public class IOUtil {
    public static void textToFile(final String strFilename, final String strBuffer){
        try {
            // 创建文件对象
            File fileText = new File(strFilename);
            // 向文件写入对象写入信息
            FileWriter fileWriter = new FileWriter(fileText);
            // 写文件
            fileWriter.write(strBuffer);
            // 关闭
            fileWriter.close();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static String readFile(final String path){
        File file = new File(path);
        if (file.isFile()) {
            // 以字节流方法读取文件
            FileInputStream fis = null;
            try {
                fis = new FileInputStream(file);
                // 设置一个，每次 装载信息的容器
                byte[] buf = new byte[1024];
                // 定义一个StringBuffer用来存放字符串
                StringBuffer sb = new StringBuffer();
                // 开始读取数据
                int len = 0;// 每次读取到的数据的长度
                while ((len = fis.read(buf)) != -1) {// len值为-1时，表示没有数据了
                    // append方法往sb对象里面添加数据
                    sb.append(new String(buf, 0, len, "utf-8"));
                }
                // 输出字符串
                return sb.toString();
            } catch (IOException e) {
                e.printStackTrace();
                return "error";
            }
        } else {
            return "error";
        }
    }
}
