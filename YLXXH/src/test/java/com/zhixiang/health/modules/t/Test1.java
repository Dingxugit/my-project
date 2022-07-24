package com.zhixiang.health.modules.t;

import com.zhixiang.health.common.utils.ZipMultiFileUtil;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

@SpringBootTest
public class Test1 {

    @Test
    void run(){
        //创建父级目录 用于存放分区（分区中存放多个分类）
        String fatherPath = "D:\\file\\carPic";
        File fatherFile = new File(fatherPath);
        try {
            if (!fatherFile.exists()) {
                fatherFile.mkdir();
            } else {
                fatherFile.delete();
                fatherFile.mkdir();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        //创建list 存放图片
        List<File> fileList = new ArrayList<>();
        fileList.add(new File(fatherPath+"/59eae252-10b4-4b9c-94dd-8498e76f881b.jpg"));
        fileList.add(new File(fatherPath+"/719a824d-7c54-4694-91dc-622a1049c98a.jpg"));

        //遍历存储图片地址
        String url = fatherPath + "/车辆图片.zip";
        File zipFile = new File(url);
        // 调用压缩方法
        ZipMultiFileUtil.zipFiles(fileList.stream().toArray(File[]::new), zipFile);

        //将项目名称的文件夹 压缩为zip
        String fileDir ="";
        ZipMultiFileUtil.fileToZip(fatherPath, fileDir, fatherPath + ".zip");
    }

}
