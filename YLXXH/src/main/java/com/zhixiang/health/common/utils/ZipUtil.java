package com.zhixiang.health.common.utils;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.io.*;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

@Slf4j
@Component
public class ZipUtil {

    public static byte[] streamZipStream(List<String> lstFile) { // lstFile  指已生成的.xls文档
        ZipOutputStream zos = null;
        BufferedInputStream bis = null;
        ByteArrayOutputStream fos = null;
        try {

            fos = new ByteArrayOutputStream();
            zos = new ZipOutputStream(fos);
            try {
                for (String fileName : lstFile) {
                    InputStream input = new FileInputStream(fileName); // 定义文件的输入流
                    ZipEntry zipEntry = new ZipEntry(fileName);
                    //通过ZipOutputStream将内容写入到zip格式的文件中。期间如要需要加入什么文件。通过ZipEntry来添加。
                    zos.putNextEntry(zipEntry);// 创建压缩的子目录
                    int temp = 0;
                    while ((temp = input.read()) != -1) { // 读取内容
                        zos.write(temp); // 压缩输出
                    }
                }
            } finally {
                zos.close(); // 不写的话会出Stream close 异常，提示未关闭文件流。 
            }
            return fos.toByteArray();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } finally {
            // 关闭流
            try {
                if (null != fos)
                    fos.close();
            } catch (IOException e) {
                e.printStackTrace();
                throw new RuntimeException(e);
            }
        }
        return null;
    }
}
