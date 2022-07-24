package com.zhixiang.health.common.utils;

import cn.hutool.core.lang.UUID;
import cn.hutool.core.util.StrUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

/**
 * @Description: 文件工具类
 * @Auther: HeJiawang
 * @Date: 2020-04-20
 */
@Slf4j
@Component
public class FileUtil {

    @Component
    public static class FileBuilder {

        // 文件保存基础路径
        public static String staticFilePath;

        @Value("${ylxxh.common.static-file-path}")
        public void setStaticFilePath( String staticFilePath ){
            this.staticFilePath = staticFilePath;
        }
    }

    /**
     * 上传文件
     * @param uploadFile 文件
     * @param filePath 文件路径
     * @return 文件访问路径
     */
    public static String uploadFile(MultipartFile uploadFile, String filePath) {
        String newFilePath = StrUtil.EMPTY;
        if (StrUtil.isEmpty(filePath)) return newFilePath;

        try {

            String fileType = uploadFile.getOriginalFilename()
                    .substring(uploadFile.getOriginalFilename().lastIndexOf("."));
            newFilePath = filePath + UUID.randomUUID() + fileType;
            String targetFilePath = FileBuilder.staticFilePath + newFilePath;

            File targetFile = new File(targetFilePath);
            cn.hutool.core.io.FileUtil.mkParentDirs(targetFile);
            uploadFile.transferTo(targetFile);
        } catch (IOException e) {

            newFilePath = StrUtil.EMPTY;
            log.error("FileUtil." + Thread.currentThread().getStackTrace()[1].getMethodName(), e);
        }

        return newFilePath;
    }
}
