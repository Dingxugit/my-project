package com.zhixiang.health.common.utils;

import org.apache.poi.ss.formula.functions.T;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

public class ZipMultiFileUtil {

    public static void zip(String path,String zipName,List<File> fileList){

        //创建list 存放图片
//        List<File> fileList = new ArrayList<>();
//        for (File f:files){
//            fileList.add(f);
//        }

        //遍历存储图片地址
        String url = path + zipName;
        File zipFile = new File(url);
        // 调用压缩方法
        ZipMultiFileUtil.zipFiles(fileList.stream().toArray(File[]::new), zipFile);

        //将项目名称的文件夹 压缩为zip
        ZipMultiFileUtil.fileToZip(path, "", zipName);
    }


    public static void zipFiles(File[] srcFiles, File zipFile) {
        try {
            if (srcFiles.length != 0) {
                // 判断压缩后的文件存在不，不存在则创建
                if (!zipFile.exists()) {
                    zipFile.createNewFile();
                } else {
                    zipFile.delete();
                    zipFile.createNewFile();
                }

                // 创建 FileInputStream 对象
                FileInputStream fileInputStream = null;
                // 实例化 FileOutputStream 对象
                FileOutputStream fileOutputStream = new FileOutputStream(zipFile);
                // 实例化 ZipOutputStream 对象
                ZipOutputStream zipOutputStream = new ZipOutputStream(fileOutputStream);
                // 创建 ZipEntry 对象
                ZipEntry zipEntry = null;
                // 遍历源文件数组
                for (int i = 0; i < srcFiles.length; i++) {
                    // 将源文件数组中的当前文件读入 FileInputStream 流中
                    fileInputStream = new FileInputStream(srcFiles[i]);
                    // 实例化 ZipEntry 对象，源文件数组中的当前文件
                    zipEntry = new ZipEntry(srcFiles[i].getName());
                    zipOutputStream.putNextEntry(zipEntry);
                    // 该变量记录每次真正读的字节个数
                    int len;
                    // 定义每次读取的字节数组
                    byte[] buffer = new byte[1024];
                    while ((len = fileInputStream.read(buffer)) > 0) {
                        zipOutputStream.write(buffer, 0, len);
                    }
                }
                zipOutputStream.closeEntry();
                zipOutputStream.close();
                fileInputStream.close();
                fileOutputStream.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 将存放在sourceFilePath目录下的源文件，打包成fileName名称的zip文件，并存放到zipFilePath路径下
     *
     * @param sourceFilePath :待压缩的文件路径
     * @param zipFilePath    :压缩后存放路径
     * @param fileName       :压缩后文件的名称
     * @return
     */
    public static boolean fileToZip(String sourceFilePath, String zipFilePath, String fileName) {
        boolean flag = false;
        File sourceFile = new File(sourceFilePath);
        FileInputStream fis = null;
        BufferedInputStream bis = null;
        FileOutputStream fos = null;
        ZipOutputStream zos = null;

        if (sourceFile.exists() == false) {
            System.out.println("待压缩的文件目录：" + sourceFilePath + "不存在");
            sourceFile.mkdir(); // 新建目录
        }
        try {
            File zipFile = new File(zipFilePath + "/" + fileName);
            if (zipFile.exists()) {
                System.out.println(zipFilePath + "目录下存在名字为:" + fileName + ".zip" + "打包文件.");
            } else {
                File[] sourceFiles = sourceFile.listFiles();
                if (null == sourceFiles || sourceFiles.length < 1) {
                    System.out.println("待压缩的文件目录：" + sourceFilePath + "里面不存在文件，无需压缩.");
                } else {
                    fos = new FileOutputStream(zipFile);
                    zos = new ZipOutputStream(new BufferedOutputStream(fos));
                    byte[] bufs = new byte[1024 * 10];
                    for (int i = 0; i < sourceFiles.length; i++) {
                        //创建ZIP实体，并添加进压缩包
                        ZipEntry zipEntry = new ZipEntry(sourceFiles[i].getName());
                        zos.putNextEntry(zipEntry);
                        //读取待压缩的文件并写进压缩包里
                        fis = new FileInputStream(sourceFiles[i]);
                        bis = new BufferedInputStream(fis, 1024 * 10);
                        int read = 0;
                        while ((read = bis.read(bufs, 0, 1024 * 10)) != -1) {
                            zos.write(bufs, 0, read);
                        }
                    }
                    flag = true;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        } finally {
            //关闭流
            try {
                if (null != bis) bis.close();
                if (null != zos) zos.close();
            } catch (IOException e) {
                e.printStackTrace();
                throw new RuntimeException(e);
            }
        }
        return flag;
    }
    public static void downLoad(HttpServletResponse response, HttpServletRequest request, String path){
        try {
            // path是指欲下载的文件的路径。
            File file = new File(path);
            // 取得文件名。
            String filename = file.getName();
            // 取得文件的后缀名。
            String ext = filename.substring(filename.lastIndexOf(".") + 1).toUpperCase();

            // 以流的形式下载文件。
            InputStream fis = new BufferedInputStream(new FileInputStream(file));
            byte[] buffer = new byte[fis.available()];
            fis.read(buffer);
            fis.close();
            // 清空response
            response.reset();
            // 设置response的Header
            OutputStream toClient = new BufferedOutputStream(response.getOutputStream());
            toClient.write(buffer);
            toClient.flush();
            toClient.close();
            file.delete() ;
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    public static void copyFile(String oldfilepath,String newpath,String fileName) {//复制文件
        File oldfile=new File(oldfilepath);
        File newfile=new File(newpath+File.separator+fileName);//创建新抽象文件

        if(newfile.exists()) {//新文件路径下有同名文件
            newfile.delete();
            try {
                newfile.createNewFile();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
        else {
            try {
                newfile.createNewFile();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }

        try {
            FileInputStream fin=new FileInputStream(oldfile);//输入流
            try {
                FileOutputStream fout=new FileOutputStream(newfile,true);//输出流
                byte[]b=new byte[1024];
                try {
                    while((fin.read(b))!=-1) {//读取到末尾 返回-1 否则返回读取的字节个数
                        fout.write(b);
                    }
                    fin.close();
                    fout.close();
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }

            } catch (FileNotFoundException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }

        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }

}
