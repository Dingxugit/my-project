package com.zhixiang.health.modules.t.api;

import com.zhixiang.health.common.utils.ZipMultiFileUtil;
import com.zhixiang.health.common.utils.poi.excel.ExportExcelUtil;
import com.zhixiang.health.modules.t.model.entity.*;
import com.zhixiang.health.modules.t.model.param.*;
import com.zhixiang.health.modules.t.service.*;
import org.apache.ibatis.annotations.Param;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Stream;

@Controller
@RequestMapping("/t/shh/file/")
public class TShhFileApi {

    @Value("${ylxxh.common.static-file-path}")
    private String staticFilePath;

    @Value("${ylxxh.car.photo.ylshh}")
    private String filePath;

    @Value("${ylxxh.export.ylshh.zbgl}")
    private String zbglFilePath ;

    @Value("${ylxxh.export.ylshh.jyk}")
    private String jykFilePath ;

    @Value("${ylxxh.export.ylshh.rcgl}")
    private String rcglFilePath ;

    @Value("${ylxxh.export.ylshh.czpz}")
    private String czpzFilePath ;

    @Value("${ylxxh.export.ylshh.dcyh}")
    private String dcyhFilePath ;

    @Resource
    private ITShhJytjService jytjService ;

    @Resource
    private ITShhDckpService dckpService ;

    @Resource
    private ITShhGldjbService gldjbService ;

    @Resource
    private ITShhJypzService jypzService ;

    @Resource
    private ITShhYljfzbzService shhYljfzbzService ;

    @PostMapping("/exportPic")
    public void exportPic(HttpServletResponse response, HttpServletRequest request, @Param("fileName") String zipName, TShhJytjParam param) {

        String path =  staticFilePath+filePath ;

        List<TShhJytj> list = jytjService.getJykxxtjDcLists(param) ;

        List<File> fileList = new ArrayList<>() ;

        list.forEach(l->{
            File f = new File(path + l.getPhoto()) ;
            FileOutputStream outputStream = null ;
            if (f.exists()) {
                File f1 = new File(path+"/new") ;
                f1.mkdirs() ;
                // 文件复制
                ZipMultiFileUtil.copyFile(path+ l.getPhoto(),path+"/new/",l.getNumberplate() + l.getPhoto().substring(l.getPhoto().indexOf("."),l.getPhoto().length()));
                l.setIsdctp("Y") ;
                jytjService.updateById(l) ;
            }
        });

        File f = new File(path+"/new/") ;
        for (File f1:f.listFiles()){
            fileList.add(f1) ;
        }

        ZipMultiFileUtil.zip(path+"/new/",zipName,fileList);
        ZipMultiFileUtil.downLoad(response,request,path+"/new/"+zipName) ;
        new File(path+"/new/").delete() ;
    }

    /**
     * 导出三级单位油料指标供应账
     * @param response
     * @param request
     * @param param
     */
    @PostMapping("/exportYlzb")
    public void exportYlzb(HttpServletResponse response, HttpServletRequest request, @RequestBody TShhYljfzbzParam param) throws IOException {

        String path = staticFilePath + zbglFilePath ;

        File file = new File(path) ;
        if (!file.exists()){
            file.mkdirs() ;
        }
        //指定数据存放的位置
        OutputStream outputStream = new FileOutputStream(path +"zbgl.xls");//D://file//export//lqbz//zbgl/zbgl.xls
        //1.创建一个工作簿
        HSSFWorkbook workbook = new HSSFWorkbook();
        //2.创建一个工作表sheet
        HSSFSheet sheet = workbook.createSheet("指标管理");

        ExportExcelUtil.createCommonSheet(sheet,workbook,"附件8",param.getYear() + "年油料经费指标账（下级单位）",8) ;

        // 第三行 编制单位，计算单位
        HSSFRow row3 = sheet.createRow(2);
        HSSFCell cell3 = row3.createCell(0);
        cell3.setCellValue("编制单位：" + param.getBianzhidanwei());
        cell3.setCellStyle(ExportExcelUtil.setThirdStyle(workbook));

        HSSFCell cell7 = row3.createCell(7);
        cell7.setCellValue("计算单位：元");
        cell7.setCellStyle(ExportExcelUtil.setThirdStyle(workbook));

        // 合并第3行 1-7列
        ExportExcelUtil.mergeCell(sheet,2, 2, 0, 6) ;



        // 合并4-5行 第1列
        ExportExcelUtil.mergeCell(sheet,3, 4, 0, 0) ;

        // 合并4-5 行 第2列
        ExportExcelUtil.mergeCell(sheet,3, 4, 1, 1) ;

        // 合并4-5行 第3列
        ExportExcelUtil.mergeCell(sheet,3, 4, 2, 2) ;

        // 合并4-5行 第4列
        ExportExcelUtil.mergeCell(sheet,3, 4, 3, 3) ;

        // 合并第4行 5-8列
        ExportExcelUtil.mergeCell(sheet,3, 3, 4, 6) ;

        // 合并第4-5行 8列
        ExportExcelUtil.mergeCell(sheet,3, 4, 7, 7) ;

        // 第4行 表头
        Map<Integer,String> m = new HashMap<>() ;
        m.put(0,"序号") ;
        m.put(1,"车号") ;
        m.put(2,"年度分配") ;
        m.put(3,"主卡经费") ;
        m.put(4,"副卡消耗") ;
        m.put(5,"") ;
        m.put(6,"") ;
        m.put(7,"结存") ;

        // 创建第4行
        HSSFRow row = sheet.createRow(3);
        Stream.iterate(0, i->i+1).limit(8).forEach(i->{
            HSSFCell celli = ExportExcelUtil.createCells(sheet,row,i) ;
            celli.setCellValue(m.get(i));
            celli.setCellStyle(ExportExcelUtil.setTableHeadStyle(workbook));
        });
        // 创建第5行
        HSSFRow row4 = sheet.createRow(4);
        Stream.iterate(0,a->a+1).limit(8).forEach(a->{
            HSSFCell cell = ExportExcelUtil.createCells(sheet,row4,a) ;
            cell.setCellStyle(ExportExcelUtil.setTableHeadStyle(workbook));
            if(a == 4){
                cell.setCellValue("92#");
            }else if(a == 5){
                cell.setCellValue("95#");
            }else if(a == 6){
                cell.setCellValue("-35#");
            }else{
                cell.setCellValue("");
            }
        });


//        // 获取数据
        List<TShhYljfzbz> list =  shhYljfzbzService.getExportDataList(param);
        Stream.iterate(0, i -> i + 1).limit(list.size()).forEach(i -> {
            Map<Integer,String> m1 = new HashMap<>() ;
            m1.put(0,i+"") ;
            m1.put(1,list.get(i).getNumberplate()) ;
            m1.put(2,list.get(i).getNiandufenpei()+"") ;
            m1.put(3,list.get(i).getZhukajingfei()+"") ;
            m1.put(4,list.get(i).getZhichuleixing92()== null ? "":list.get(i).getZhichuleixing92()+"") ;
            m1.put(5,list.get(i).getZhichuleixing95() == null? "":list.get(i).getZhichuleixing95()+"") ;
            m1.put(6,list.get(i).getZhichuleixing35() ==null ? "":list.get(i).getZhichuleixing35()+"") ;
            m1.put(7,list.get(i).getUnitinformation() == null? "":list.get(i).getUnitinformation()+"") ;

            HSSFRow rowi = sheet.createRow(5+i);
            rowi.setHeightInPoints(30);//设置行的高度
            // 遍历创建列
            Stream.iterate(0, c -> c + 1).limit(8).forEach(c -> {
                HSSFCell celli = rowi.createCell(c);
                if (i == 0 && c== 0){
                    celli.setCellValue("");
                }else{
                    celli.setCellValue(m1.get(c));
                }
                celli.setCellStyle(ExportExcelUtil.setDataStyle(workbook));
            });


        });

        AtomicReference<Double> totalNdfp = new AtomicReference<>(0d);
        AtomicReference<Double> totalzb = new AtomicReference<>(0d);
        AtomicReference<Double> total92 = new AtomicReference<>(0d);
        AtomicReference<Double> total95 = new AtomicReference<>(0d);
        AtomicReference<Double> total35 = new AtomicReference<>(0d);
        AtomicReference<Double> totaljc = new AtomicReference<>(0d);

        list.stream().forEach(t->{
            double t92 = t.getZhichuleixing92()==null  ? 0d:t.getZhichuleixing92() ;
            double t95 = t.getZhichuleixing95()==null  ? 0d:t.getZhichuleixing95() ;
            double t35 = t.getZhichuleixing35()==null  ? 0d:t.getZhichuleixing35() ;
            double tu = t.getUnitinformation()==null  ? 0d:t.getUnitinformation() ;

            totalNdfp.updateAndGet(v -> new Double((double) (v + t.getNiandufenpei())));
            totalzb.updateAndGet(v -> new Double((double) (v + t.getZhukajingfei())));
            total92.updateAndGet(v -> new Double((double) (v + t92)));
            total95.updateAndGet(v -> new Double((double) (v + t95)));
            total35.updateAndGet(v -> new Double((double) (v + t35)));
            totaljc.updateAndGet(v -> new Double((double) (v + tu)));

        });

        HSSFRow rowi = sheet.createRow(5+list.size());
        Stream.iterate(0, c -> c + 1).limit(8).forEach(c -> {
            HSSFCell celli = rowi.createCell(c);
            if (c == 0 ){
                celli.setCellValue("合计");
            }else if (c == 2){
                celli.setCellValue(totalNdfp.get());
            }else if (c == 3){
                celli.setCellValue(totalzb.get());
            }else if (c == 4){
                celli.setCellValue(total92.get());
            }else if (c == 5){
                celli.setCellValue(total95.get());
            }else if (c == 6){
                celli.setCellValue(total35.get());
            }else if (c == 7){
                celli.setCellValue(totaljc.get());
            }
            celli.setCellStyle(ExportExcelUtil.setDataStyle(workbook));
        });

        ExportExcelUtil.mergeCell(sheet,5+list.size(),5+list.size(),0,1) ;

        workbook.write(outputStream);
        outputStream.close();

        ZipMultiFileUtil.downLoad(response,request,path+"zbgl.xls") ;
    }

    /**
     * 导出三级单位加油卡信息统计
     * @param response
     * @param request
     * @param param
     */
    @PostMapping("/exportJykxxtj")
    public void exportJykxxtj(HttpServletResponse response, HttpServletRequest request, @RequestBody TShhJytjParam param) throws IOException {

        String path = staticFilePath + jykFilePath ;

        File file = new File(path) ;
        if (!file.exists()){
            file.mkdirs() ;
        }
        //指定数据存放的位置
        OutputStream outputStream = new FileOutputStream(path +"jykxxtj.xls");//D://file//export//lqbz//zbgl/zbgl.xls
        //1.创建一个工作簿
        HSSFWorkbook workbook = new HSSFWorkbook();
        //2.创建一个工作表sheet
        HSSFSheet sheet = workbook.createSheet("加油卡信息");

        ExportExcelUtil.createCommonSheet(sheet,workbook,"附件9-2","加油卡信息统计表",8) ;

        // 第三行
        HSSFRow row3 = sheet.createRow(2);
        HSSFCell cell3 = row3.createCell(0);
        cell3.setCellValue("单位：（盖章）"+ param.getBianzhidanwei());
        cell3.setCellStyle(ExportExcelUtil.setThirdStyle(workbook));

        HSSFCell cell7 = row3.createCell(6);
        cell7.setCellValue("填报时间："+param.getTianbaoshijian().substring(0,4) + " 年" + param.getTianbaoshijian().substring(5,7) +" 月" + param.getTianbaoshijian().substring(8,10)+" 日");
        cell7.setCellStyle(ExportExcelUtil.setThirdStyle(workbook));


        // 合并第3行1-2列
        ExportExcelUtil.mergeCell(sheet,2, 2, 0, 1) ;

        // 合并3行 第7-8列
        ExportExcelUtil.mergeCell(sheet,2, 2, 6, 7) ;

        // 第4行 表头
        Map<Integer,String> m = new HashMap<>() ;
        m.put(0,"序号") ;
        m.put(1,"加油卡号") ;
        m.put(2,"车辆牌号") ;
        m.put(3,"厂牌型号") ;
        m.put(4,"发动机号") ;
        m.put(5,"车架号码") ;
        m.put(6,"剩余指标（元）") ;
        m.put(7,"确认签字") ;

        // 创建第4行
        HSSFRow row = sheet.createRow(3);
        Stream.iterate(0,i->i+1).limit(8).forEach(i->{
            HSSFCell celli = ExportExcelUtil.createCells(sheet,row,i) ;
            celli.setCellValue(m.get(i));
            celli.setCellStyle(ExportExcelUtil.setTableHeadStyle(workbook));
        });

        // 获取数据
        List<TShhJytj> list =  jytjService.getExportDataList(param);
        Stream.iterate(0, i -> i + 1).limit(list.size()).forEach(i -> {
            Map<Integer,String> m1 = new HashMap<>() ;
            m1.put(0,(i+1)+"") ;
            m1.put(1,list.get(i).getJiayoukahao()) ;
            m1.put(2,list.get(i).getNumberplate()+"") ;
            m1.put(3,list.get(i).getChangpaixinghao()+"") ;
            m1.put(4,list.get(i).getFadongjihao()+"") ;
            m1.put(5,list.get(i).getChejiahaoma()+"") ;
            m1.put(6,list.get(i).getShengyuzhibiao()+"") ;
            m1.put(7,list.get(i).getQuerequanzu()+"") ;

            HSSFRow rowi = sheet.createRow(4+i);
            rowi.setHeightInPoints(30);//设置行的高度
            // 遍历创建列
            Stream.iterate(0, c -> c + 1).limit(8).forEach(c -> {
                HSSFCell celli = rowi.createCell(c);
                celli.setCellValue(m1.get(c));
                celli.setCellStyle(ExportExcelUtil.setDataStyle(workbook));
            });


        });



        workbook.write(outputStream);
        outputStream.close();

        ZipMultiFileUtil.downLoad(response,request,path+"jykxxtj.xls") ;
    }

    /**
     * 导出三级单位加油卡日常管理
     * @param response
     * @param request
     * @param param
     */
    @PostMapping("/exportJykrcgl")
    public void exportJykrcgl(HttpServletResponse response, HttpServletRequest request,@RequestBody TShhGldjbParam param) throws IOException {

        String path = staticFilePath + rcglFilePath ;

        File file = new File(path) ;
        if (!file.exists()){
            file.mkdirs() ;
        }
        //指定数据存放的位置
        OutputStream outputStream = new FileOutputStream(path +"jykrcgl.xls");//D://file//export//lqbz//zbgl/zbgl.xls
        //1.创建一个工作簿
        HSSFWorkbook workbook = new HSSFWorkbook();
        //2.创建一个工作表sheet
        HSSFSheet sheet = workbook.createSheet("加油卡日常管理");

        ExportExcelUtil.createCommonSheet(sheet,workbook,"附件9","油料社会化保障加油卡日常管理登记表",16) ;


        Map<Integer,String> m = new HashMap<>() ;
        m.put(0,"序号") ;
        m.put(1,"领卡事由") ;
        m.put(2,"领取时间") ;
        m.put(3,"加油卡卡号") ;
        m.put(4,"车牌号") ;
        m.put(5,"厂牌型号") ;
        m.put(6,"油品") ;
        m.put(7,"上次里程读数(km)") ;
        m.put(8,"本次里程读数(km）") ;
        m.put(9,"行使里程(km）") ;
        m.put(10,"本次加油量(L）") ;
        m.put(11,"本次加油金额(元)") ;
        m.put(12,"卡内余额(元）") ;
        m.put(13,"归还时间") ;
        m.put(14,"是否上交小票") ;
        m.put(15,"驾驶员签字") ;

        // 创建第3行
        HSSFRow row = sheet.createRow(2);
        Stream.iterate(0,i->i+1).limit(16).forEach(i->{
            HSSFCell celli = ExportExcelUtil.createCells(sheet,row,i) ;
            celli.setCellValue(m.get(i));
            celli.setCellStyle(ExportExcelUtil.setTableHeadStyle(workbook));
        });

        // 获取数据
        List<TShhGldjb> list =  gldjbService.getExportDataList(param);
        Stream.iterate(0, i -> i + 1).limit(list.size()).forEach(i -> {
            Map<Integer,String> m1 = new HashMap<>() ;
            m1.put(0,(i+1)+"") ;
            m1.put(1,list.get(i).getLingkashiyou()) ;
            m1.put(2,list.get(i).getLingqushijian()+"") ;
            m1.put(3,list.get(i).getJiayoukakahao()+"") ;
            m1.put(4,list.get(i).getNumberplate()+"") ;
            m1.put(5,list.get(i).getChangpaixinghao()+"") ;
            m1.put(6,list.get(i).getYoupin()+"") ;
            m1.put(7,list.get(i).getShangcilichengdushu()+"") ;
            m1.put(8,list.get(i).getBencilichengdushu()+"") ;
            m1.put(9,list.get(i).getXiingshilicheng()+"") ;
            m1.put(10,list.get(i).getBencijiayouliang()+"") ;
            m1.put(11,list.get(i).getBencijiayoujine()+"") ;
            m1.put(12,list.get(i).getKaneiyue()+"") ;
            m1.put(13,list.get(i).getGuihuanshijian()+"") ;
            m1.put(14,list.get(i).getShifoushangjiaoxiaopiao()+"") ;
            m1.put(15,list.get(i).getJiashiyanname()+"") ;

            HSSFRow rowi = sheet.createRow(3+i);
            rowi.setHeightInPoints(30);//设置行的高度
            // 遍历创建列
            Stream.iterate(0, c -> c + 1).limit(16).forEach(c -> {
                HSSFCell celli = rowi.createCell(c);
                celli.setCellValue(m1.get(c));
                celli.setCellStyle(ExportExcelUtil.setDataStyle(workbook));
            });


        });

        workbook.write(outputStream);
        outputStream.close();

        ZipMultiFileUtil.downLoad(response,request,path+"jykrcgl.xls") ;
    }

    /**
     * 导出三级单位加油充值凭证
     * @param response
     * @param request
     * @param param
     */
    @PostMapping("/exportCzpz")
    public void exportCzpz(HttpServletResponse response, HttpServletRequest request,@RequestBody TShhJypzParam param) throws IOException {

        String path = staticFilePath + czpzFilePath ;

        File file = new File(path) ;
        if (!file.exists()){
            file.mkdirs() ;
        }
        //指定数据存放的位置
        OutputStream outputStream = new FileOutputStream(path +"czpz.xls");//D://file//export//lqbz//zbgl/zbgl.xls
        //1.创建一个工作簿
        HSSFWorkbook workbook = new HSSFWorkbook();
        //2.创建一个工作表sheet
        HSSFSheet sheet = workbook.createSheet("充值凭证");

        ExportExcelUtil.createCommonSheet(sheet,workbook,"附件10","油料社会化保障加油凭证",8) ;

        int height = 30 ;
        // 第三行
        HSSFRow row3 = sheet.createRow(2);
        row3.setHeightInPoints(height);
        HSSFCell cell3 = row3.createCell(0);
        cell3.setCellValue("用油单位");
        cell3.setCellStyle(ExportExcelUtil.setTableHeadStyle(workbook));

        HSSFCell cell4 = row3.createCell(1);
        cell4.setCellValue(param.getYongyoudanwei());
        cell4.setCellStyle(ExportExcelUtil.setTableHeadStyle(workbook));

        HSSFCell cell5 = row3.createCell(2);
        cell5.setCellValue("");
        cell5.setCellStyle(ExportExcelUtil.setTableHeadStyle(workbook));

        HSSFCell cell6 = row3.createCell(3);
        cell6.setCellValue("");
        cell6.setCellStyle(ExportExcelUtil.setTableHeadStyle(workbook));

        HSSFCell cell7 = row3.createCell(4);
        cell7.setCellValue("");
        cell7.setCellStyle(ExportExcelUtil.setTableHeadStyle(workbook));

        HSSFCell cell8 = row3.createCell(5);
        cell8.setCellValue("加油时间");
        cell8.setCellStyle(ExportExcelUtil.setTableHeadStyle(workbook));

        HSSFCell cell9 = row3.createCell(6);
        cell9.setCellValue(param.getJiayousj());
        cell9.setCellStyle(ExportExcelUtil.setTableHeadStyle(workbook));

        HSSFCell cell10 = row3.createCell(7);
        cell10.setCellValue("");
        cell10.setCellStyle(ExportExcelUtil.setTableHeadStyle(workbook));

        // 合并第3行 2,3,4列
        ExportExcelUtil.mergeCell(sheet,2,2,1,4) ;
        // 合并第3行 6,7列
        ExportExcelUtil.mergeCell(sheet,2,2,6,7) ;
        // 合并第4,5行 第1列
//        ExportExcelUtil.mergeCell(sheet,3,4,0,0);
        // 合并第4，5行 第2列
        ExportExcelUtil.mergeCell(sheet,3,4,1,1);
        // 合并第4，5行 第3列
        ExportExcelUtil.mergeCell(sheet,3,4,2,2);
        // 合并第4，5行 第4列
        ExportExcelUtil.mergeCell(sheet,3,4,3,3);
        // 合并第4，5行 第5列
        ExportExcelUtil.mergeCell(sheet,3,4,4,4);
        // 合并第4，5行 第6列
        ExportExcelUtil.mergeCell(sheet,3,4,5,5);
        // 合并第4行 第6，7列
        ExportExcelUtil.mergeCell(sheet,3,3,6,7);

        Map<Integer,String> m = new HashMap<>() ;
        m.put(0,"加油明细") ;
        m.put(1,"车牌号码") ;
        m.put(2,"油品") ;
        m.put(3,"数量（升）") ;
        m.put(4,"卡内余额（元）") ;
        m.put(5,"经办人") ;
        m.put(6,"仪表显示公里（公里）") ;
        m.put(7,"") ;

        // 创建第4行
        HSSFRow row4 = sheet.createRow(3);
        row3.setHeightInPoints(height);
        Stream.iterate(0,i->i+1).limit(8).forEach(i->{
            HSSFCell celli = ExportExcelUtil.createCells(sheet,row4,i) ;
            celli.setCellValue(m.get(i));
            celli.setCellStyle(ExportExcelUtil.setTableHeadStyle(workbook));
        });

        // 创建第5行
        HSSFRow row5 = sheet.createRow(4);
        row3.setHeightInPoints(height);
        Stream.iterate(0,i->i+1).limit(8).forEach(i->{
            HSSFCell celli = ExportExcelUtil.createCells(sheet,row5,i) ;
            celli.setCellValue("");
            if (i == 6 )celli.setCellValue("上次");
            if (i == 7 )celli.setCellValue("本次");
            celli.setCellStyle(ExportExcelUtil.setTableHeadStyle(workbook));
        });


        // 获取数据
        List<TShhJypz> list =  jypzService.getExportDataList(param);

        double total = list.stream().mapToDouble(TShhJypz::getShuliang).sum();

        Stream.iterate(0, i -> i + 1).limit(list.size()).forEach(i -> {
            Map<Integer,String> m1 = new HashMap<>() ;
            m1.put(0,"") ;
            m1.put(1,list.get(i).getNumberplate()) ;
            m1.put(2,list.get(i).getYoupin()+"") ;
            m1.put(3,list.get(i).getShuliang()+"") ;
            m1.put(4,list.get(i).getYue()+"") ;
            m1.put(5,list.get(i).getJingbanren()+"") ;
            m1.put(6,list.get(i).getShangciyibiaoshu()+"") ;
            m1.put(7,list.get(i).getBenciyibiaoshu()+"") ;

            HSSFRow rowi = sheet.createRow(5+i);
            rowi.setHeightInPoints(height);//设置行的高度
            // 遍历创建列
            Stream.iterate(0, c -> c + 1).limit(8).forEach(c -> {
                HSSFCell celli = rowi.createCell(c);
                celli.setCellValue(m1.get(c));
                celli.setCellStyle(ExportExcelUtil.setDataStyle(workbook));
            });

        });
        // 合计行上方创建一行空白行
        HSSFRow rowkh = sheet.createRow(5+list.size());
        rowkh.setHeightInPoints(height);//设置行的高度
        Stream.iterate(0,a->a+1).limit(8).forEach(a->{
            HSSFCell celli = rowkh.createCell(a);
            celli.setCellValue("");
            celli.setCellStyle(ExportExcelUtil.setDataStyle(workbook));
        });
        // 创建合计行
        HSSFRow rowhj = sheet.createRow(5+list.size()+1);
        rowhj.setHeightInPoints(height);//设置行的高度
        // 遍历创建列
        Stream.iterate(0, c -> c + 1).limit(8).forEach(c -> {
            HSSFCell celli = rowhj.createCell(c);
            if (c == 0 ){
                celli.setCellValue("");
            }else if(c ==1){
                celli.setCellValue("合计（公斤）");
            }else{
                celli.setCellValue(total);
            }
            celli.setCellStyle(ExportExcelUtil.setDataStyle(workbook));
        });

        ExportExcelUtil.mergeCell(sheet,5+list.size()+1,5+list.size()+1,2,7) ;
        ExportExcelUtil.mergeCell(sheet,3,5+list.size()+1,0,0) ;

        // 创建用油单位意见行
        HSSFRow rowyj = sheet.createRow(5+list.size()+2);
        rowyj.setHeightInPoints(60);

        Stream.iterate(0, c -> c + 1).limit(8).forEach(c -> {
            HSSFCell celli = rowyj.createCell(c);
            if (c == 0 ){
                celli.setCellValue("用油单位意见");
            }else{
                celli.setCellValue("单位盖章：                        负责人签名：");
            }
            celli.setCellStyle(ExportExcelUtil.setDataStyle(workbook));
        });

        // 合并用油单位意见行2-7列
        ExportExcelUtil.mergeCell(sheet,5+list.size()+2,5+list.size()+2,1,7) ;


        workbook.write(outputStream);
        outputStream.close();

        ZipMultiFileUtil.downLoad(response,request,path+"czpz.xls") ;
    }

    /**
     * 导出三级单位单车油耗
     * @param response
     * @param request
     * @param param
     */
    @PostMapping("/exportDcyh")
    public void exportDcyh(HttpServletResponse response, HttpServletRequest request,@RequestBody TShhDckpParam param) throws IOException {

        String path = staticFilePath + dcyhFilePath ;

        File file = new File(path) ;
        if (!file.exists()){
            file.mkdirs() ;
        }
        //指定数据存放的位置
        OutputStream outputStream = new FileOutputStream(path +"dcyh.xls");//D://file//export//lqbz//zbgl/zbgl.xls
        //1.创建一个工作簿
        HSSFWorkbook workbook = new HSSFWorkbook();
        //2.创建一个工作表sheet
        HSSFSheet sheet = workbook.createSheet("单车油耗卡");

        ExportExcelUtil.createCommonSheet(sheet,workbook,"附件11","油料社会化保障车辆单车油耗考核卡片",9) ;

        TShhDckp dcyhkp = dckpService.getDcyhxx(param) ;

        int height = 40 ;
        // 第3行
        HSSFRow row3 = sheet.createRow(2);
        row3.setHeightInPoints(height);
        Stream.iterate(0,i->i+1).limit(9).forEach(i->{
            HSSFCell celli = ExportExcelUtil.createCells(sheet,row3,i) ;
            if(i == 0){
                celli.setCellValue("用油单位");
            }else if (i ==1){
                if (dcyhkp!= null)
                celli.setCellValue(dcyhkp.getYingyoudanwei());
                else celli.setCellValue("");
            }else if (i ==4){
                celli.setCellValue("车牌号");
            }else if (i ==5){
                if (dcyhkp!= null)
                celli.setCellValue(dcyhkp.getNumberplate());
                else celli.setCellValue("");
            }else if (i == 6){
                celli.setCellValue("油品");
            }else if (i == 7){
                if (dcyhkp!= null)
                celli.setCellValue(dcyhkp.getYoupin());
                else celli.setCellValue("");
            }else{
                celli.setCellValue("");
            }
            celli.setCellStyle(ExportExcelUtil.setTableHeadStyle(workbook));
        });
        // 合并 第3行 2-4 列
        ExportExcelUtil.mergeCell(sheet,2,2,1,3) ;
        // 合并 第3行 8-9 列
        ExportExcelUtil.mergeCell(sheet,2,2,7,8) ;


        // 第4行
        HSSFRow row4 = sheet.createRow(3);
        row4.setHeightInPoints(height);
        Stream.iterate(0,i->i+1).limit(9).forEach(i->{
            HSSFCell celli = ExportExcelUtil.createCells(sheet,row4,i) ;
            if(i == 0){
                celli.setCellValue("类型");
            }else if (i ==1){
                if (dcyhkp!= null)
                celli.setCellValue(dcyhkp.getLeixing());
                else celli.setCellValue("");
            }else if (i ==2){
                celli.setCellValue("品牌");
            }else if (i ==3){
                if (dcyhkp!= null)
                celli.setCellValue(dcyhkp.getPinpai());
                else celli.setCellValue("");
            }else if (i == 4){
                celli.setCellValue("排量");
            }else if (i == 5){
                if (dcyhkp!= null)
                celli.setCellValue(dcyhkp.getPailiang());
                else celli.setCellValue("");
            }else if (i == 6){
                celli.setCellValue("型号");
            }else if (i == 7){
                if (dcyhkp!= null)
                    celli.setCellValue(dcyhkp.getXinghao());
                else celli.setCellValue("");
            }else{
                celli.setCellValue("");
            }
            celli.setCellStyle(ExportExcelUtil.setTableHeadStyle(workbook));
        });
        // 合并 第4行 8-9 列
        ExportExcelUtil.mergeCell(sheet,3,3,7,8) ;

        Map<Integer,String> m = new HashMap<>() ;
        m.put(0,"月份") ;
        m.put(1,"消耗标准（升/百公里）") ;
        m.put(2,"执行标准（升/百公里）") ;
        m.put(3,"里程初读数（公里）") ;
        m.put(4,"里程末读数（公里）") ;
        m.put(5,"行使里程（公里）") ;
        m.put(6,"摩托小时（小时）") ;
        m.put(7,"耗油量（升）") ;
        m.put(8,"确认签字") ;

        // 创建第5行
        HSSFRow row5 = sheet.createRow(4);
        row5.setHeightInPoints(height);
        Stream.iterate(0,l->l+1).limit(9).forEach(l->{
            HSSFCell celll = ExportExcelUtil.createCells(sheet,row5,l) ;
            celll.setCellValue(m.get(l));
            celll.setCellStyle(ExportExcelUtil.setTableHeadStyle(workbook));
        });

        List<TShhDckp> list = dckpService.getDcyhxxList(param) ;

        // 创建数据行
        Stream.iterate(0,i->i+1).limit(list.size()).forEach(i->{
            Map<Integer,String> m1 = new HashMap<>() ;
            m1.put(0,list.get(i).getYuefen()+"月") ;
            m1.put(1,list.get(i).getYouhaobiaozhun() == null ? "": list.get(i).getYouhaobiaozhun()+"") ;
            m1.put(2,list.get(i).getZhixingbiaozhun()== null ? "":list.get(i).getZhixingbiaozhun()+"") ;
            m1.put(3,list.get(i).getLichengchudu()== null ? "":list.get(i).getLichengchudu()+"") ;
            m1.put(4,list.get(i).getLichengweidu()== null ? "":list.get(i).getLichengweidu()+"") ;
            m1.put(5,list.get(i).getXingshilicheng()== null ? "":list.get(i).getXingshilicheng()+"") ;
            m1.put(6,list.get(i).getMotuoxiaoshi() == null ? "": list.get(i).getMotuoxiaoshi()+"") ;
            m1.put(7,list.get(i).getHaoyouliang()== null ? "":list.get(i).getHaoyouliang()+"") ;
            m1.put(8,list.get(i).getQianziname()) ;

            HSSFRow rowi = sheet.createRow(5+i);
            rowi.setHeightInPoints(height);
            Stream.iterate(0,l->l+1).limit(9).forEach(l->{
                HSSFCell celll = ExportExcelUtil.createCells(sheet,rowi,l) ;
                celll.setCellValue(m1.get(l));
                celll.setCellStyle(ExportExcelUtil.setTableHeadStyle(workbook));
            });

        });


        HSSFRow rowi = sheet.createRow(5+list.size());
        rowi.setHeightInPoints(height);
        Stream.iterate(0,l->l+1).limit(9).forEach(l->{
            HSSFCell celll = ExportExcelUtil.createCells(sheet,rowi,l) ;
            if(l ==6){
                celll.setCellValue("填报人：");
            }else{
                celll.setCellValue("");
            }
            celll.setCellStyle(ExportExcelUtil.setThirdStyle(workbook));
        });

        workbook.write(outputStream);
        outputStream.close();

        ZipMultiFileUtil.downLoad(response,request,path+"dcyh.xls") ;
    }

    /**
     * 导出二级级单位油料指标供应账
     * @param response
     * @param request
     * @param param
     */
    @PostMapping("/exportEjYlzb")
    public void exportEjYlzb(HttpServletResponse response, HttpServletRequest request, @RequestBody TShhYljfzbzParam param) throws IOException {

        String path = staticFilePath + zbglFilePath ;

        File file = new File(path) ;
        if (!file.exists()){
            file.mkdirs() ;
        }
        int countColumn = 8 ;
        //指定数据存放的位置
        OutputStream outputStream = new FileOutputStream(path +"ejzbgl.xls");//D://file//export//lqbz//zbgl/zbgl.xls
        //1.创建一个工作簿
        HSSFWorkbook workbook = new HSSFWorkbook();
        //2.创建一个工作表sheet
        HSSFSheet sheet = workbook.createSheet("指标管理");

        ExportExcelUtil.createCommonSheet(sheet,workbook,"附件7",param.getYear() + "年油料经费指标账（上级单位）",countColumn) ;

        // 第三行 编制单位，计算单位
        HSSFRow row3 = sheet.createRow(2);
        HSSFCell cell3 = row3.createCell(0);
        cell3.setCellValue("编制单位：" );
        cell3.setCellStyle(ExportExcelUtil.setThirdStyle(workbook));

        HSSFCell cell7 = row3.createCell(7);
        cell7.setCellValue("计算单位：元");
        cell7.setCellStyle(ExportExcelUtil.setThirdStyle(workbook));

        // 合并第3行 1-7列
        ExportExcelUtil.mergeCell(sheet,2, 2, 0, 6) ;

        // 合并4-5行 第1列
        ExportExcelUtil.mergeCell(sheet,3, 4, 0, 0) ;

        // 合并4-5 行 第2列
        ExportExcelUtil.mergeCell(sheet,3, 4, 1, 1) ;

        // 合并4行 第3-7列
        ExportExcelUtil.mergeCell(sheet,3, 3, 2, 6) ;

        // 合并第4-5行 8列
        ExportExcelUtil.mergeCell(sheet,3, 4, 7, 7) ;

        // 第4行 表头
        Map<Integer,String> m = new HashMap<>() ;
        m.put(0,"序号") ;
        m.put(1,"摘 要") ;
        m.put(2,param.getYear() + "年度") ;
        m.put(3,"") ;
        m.put(4,"") ;
        m.put(5,"") ;
        m.put(6,"") ;
        m.put(7,"结存") ;

        // 创建第4行
        HSSFRow row = sheet.createRow(3);
        Stream.iterate(0, i->i+1).limit(countColumn).forEach(i->{
            HSSFCell celli = ExportExcelUtil.createCells(sheet,row,i) ;
            celli.setCellValue(m.get(i));
            celli.setCellStyle(ExportExcelUtil.setTableHeadStyle(workbook));
        });
        // 创建第5行
        HSSFRow row4 = sheet.createRow(4);
        Stream.iterate(0,a->a+1).limit(countColumn).forEach(a->{
            HSSFCell cell = ExportExcelUtil.createCells(sheet,row4,a) ;
            cell.setCellStyle(ExportExcelUtil.setTableHeadStyle(workbook));
            if(a == 2){
                cell.setCellValue("年度分配");
            }else if(a == 3){
                cell.setCellValue("主卡经费");
            }else if(a == 4){
                cell.setCellValue("92#");
            }else if(a == 5){
                cell.setCellValue("95#");
            }else if(a == 6){
                cell.setCellValue("-35#");
            }else{
                cell.setCellValue("");
            }
        });


//        // 获取数据
        List<TShhYljfzbz> list =  shhYljfzbzService.getExportDataList(param);
        Stream.iterate(0, i -> i + 1).limit(list.size()).forEach(i -> {
            Map<Integer,String> m1 = new HashMap<>() ;
            m1.put(0,i+"") ;
            m1.put(1,list.get(i).getNumberplate()) ;
            m1.put(2,list.get(i).getNiandufenpei()+"") ;
            m1.put(3,list.get(i).getZhukajingfei()+"") ;
            m1.put(4,list.get(i).getZhichuleixing92() == null ? "" : list.get(i).getZhichuleixing92()+"") ;
            m1.put(5,list.get(i).getZhichuleixing95() == null ? "": list.get(i).getZhichuleixing95()+"") ;
            m1.put(6,list.get(i).getZhichuleixing35() ==null ? "" : list.get(i).getZhichuleixing35()+"") ;
            m1.put(7,list.get(i).getUnitinformation() == null ? "": list.get(i).getUnitinformation()+"") ;

            HSSFRow rowi = sheet.createRow(5+i);
            rowi.setHeightInPoints(30);//设置行的高度
            // 遍历创建列
            Stream.iterate(0, c -> c + 1).limit(countColumn).forEach(c -> {
                HSSFCell celli = rowi.createCell(c);
                if (i == 0 && c== 0){
                    celli.setCellValue("");
                }else{
                    celli.setCellValue(m1.get(c));
                }
                celli.setCellStyle(ExportExcelUtil.setDataStyle(workbook));
            });


        });

        AtomicReference<Double> totalNdfp = new AtomicReference<>(0d);
        AtomicReference<Double> totalzb = new AtomicReference<>(0d);
        AtomicReference<Double> total92 = new AtomicReference<>(0d);
        AtomicReference<Double> total95 = new AtomicReference<>(0d);
        AtomicReference<Double> total35 = new AtomicReference<>(0d);
        AtomicReference<Double> totaljc = new AtomicReference<>(0d);

        list.stream().forEach(t->{
            double t92 = t.getZhichuleixing92()==null  ? 0d:t.getZhichuleixing92() ;
            double t95 = t.getZhichuleixing95()==null  ? 0d:t.getZhichuleixing95() ;
            double t35 = t.getZhichuleixing35()==null  ? 0d:t.getZhichuleixing35() ;
            double tu = t.getUnitinformation()==null  ? 0d:t.getUnitinformation() ;

            totalNdfp.updateAndGet(v -> new Double((double) (v + t.getNiandufenpei())));
            totalzb.updateAndGet(v -> new Double((double) (v + t.getZhukajingfei())));
            total92.updateAndGet(v -> new Double((double) (v + t92)));
            total95.updateAndGet(v -> new Double((double) (v + t95)));
            total35.updateAndGet(v -> new Double((double) (v + t35)));
            totaljc.updateAndGet(v -> new Double((double) (v + tu)));

        });

        ExportExcelUtil.mergeCell(sheet,5+list.size(),5+list.size(),0,1) ;

        workbook.write(outputStream);
        outputStream.close();

        ZipMultiFileUtil.downLoad(response,request,path+"ejzbgl.xls") ;
    }

}