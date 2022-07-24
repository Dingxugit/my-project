package com.zhixiang.health.modules.t.api;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;

@Controller
@RequestMapping("/t/img/")
public class ImageApi {

    @RequestMapping(value = "showImg")
    public void ShowImg(HttpServletRequest request, HttpServletResponse response, @RequestParam("headerUrl") String url)
            throws IOException {
        FileInputStream fileIs = null;
        try {
            fileIs = new FileInputStream("D:/carInfo/carPic/"+url);
        } catch (Exception e) {
            return;
        }
        int i = fileIs.available();
        // 得到文件大小
        byte data[] = new byte[i];
        fileIs.read(data);
        // 读数据
        response.setContentType("image/*");
        // 设置返回的文件类型
        OutputStream outStream = response.getOutputStream();
        // 得到向客户端输出二进制数据的对象
        outStream.write(data);
        // 输出数据
        outStream.flush();
        outStream.close();
        fileIs.close();
    }

}
