package com.zhixiang.health.modules.t.api;

import com.zhixiang.health.common.model.param.UserParam;
import com.zhixiang.health.common.utils.ZipMultiFileUtil;
import com.zhixiang.health.common.utils.poi.excel.ExportExcelUtil;
import com.zhixiang.health.modules.t.mapper.TLqbzYlgyzbzMapper;
import com.zhixiang.health.modules.t.model.dto.TLqbzJykxxtjDto;
import com.zhixiang.health.modules.t.model.dto.TLqbzYlgyzbzDto;
import com.zhixiang.health.modules.t.model.entity.*;
import com.zhixiang.health.modules.t.model.param.*;
import com.zhixiang.health.modules.t.service.*;
import org.apache.ibatis.annotations.Param;
import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Controller
@RequestMapping("/t/lqbz/file/")
public class TLqbzYlgyzbzFileApi {

    @Value("${ylxxh.common.static-file-path}")
    private String staticFilePath;

    @Value("${ylxxh.car.photo.lqbz}")
    private String filePath;
    @Value("${ylxxh.export.lqbz.zbgl}")
    private String zbglFilePath ;

    @Value("${ylxxh.export.lqbz.jyk}")
    private String jykFilePath ;

    @Value("${ylxxh.export.lqbz.rcgl}")
    private String rcglFilePath ;

    @Value("${ylxxh.export.lqbz.czpz}")
    private String czpzFilePath ;

    @Value("${ylxxh.export.lqbz.dcyh}")
    private String dcyhFilePath ;

    @Resource
    private ITLqbzJykxxtjService service ;

    @Resource
    private ITLqbzYlgyzbzService ylgyzbzService ;

    @Resource
    private ITLqbzYlgyczpzService ylgyczpzService ;

    @Resource
    private ITLqbzDcyhkpService dcyhkpService ;

    @Resource
    private ITLqbzJygcjykrcgldjService jygcjykrcgldjService ;

    @PostMapping("/exportPic")
    public void exportPic(HttpServletResponse response, HttpServletRequest request, @Param("fileName") String zipName, TLqbzJykxxtjParam param) {

        String path =  staticFilePath+filePath ;//D://file/lqbz/

        List<TLqbzJykxxtj> list = service.getJykxxtjDcLists(param) ;
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
                service.updateById(l) ;
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
    public void exportYlzb(HttpServletResponse response, HttpServletRequest request, @RequestBody TLqbzYlgyzbzParam param) throws IOException {

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

        ExportExcelUtil.createCommonSheet(sheet,workbook,"附件3",param.getYear()+"年油料供应指标账（下级单位）",8) ;

        // 第三行 编制单位，计算单位
        HSSFRow row3 = sheet.createRow(2);
        HSSFCell cell3 = row3.createCell(0);
        cell3.setCellValue("编制单位："+param.getBianzhidanwei());
        cell3.setCellStyle(ExportExcelUtil.setThirdStyle(workbook));

        HSSFCell cell7 = row3.createCell(7);
        cell7.setCellValue("计算单位：公斤");
        cell7.setCellStyle(ExportExcelUtil.setThirdStyle(workbook));

        // 合并第3行 1-7列
        ExportExcelUtil.mergeCell(sheet,2, 2, 0, 6) ;

        // 合并第3行8-9列
        ExportExcelUtil.mergeCell(sheet,2, 2, 7, 8) ;

        // 合并4-5行 第1列
        ExportExcelUtil.mergeCell(sheet,3, 4, 0, 0) ;

        // 合并4-5 行 第2列
        ExportExcelUtil.mergeCell(sheet,3, 4, 1, 1) ;

        // 合并4-5行 第3列
        ExportExcelUtil.mergeCell(sheet,3, 4, 2, 2) ;

        // 合并4-5行 第4列
        ExportExcelUtil.mergeCell(sheet,3, 4, 3, 3) ;

        // 合并4-5 行第5列
        ExportExcelUtil.mergeCell(sheet,3, 4, 4, 4) ;

        // 合并第4行 5-8列
        ExportExcelUtil.mergeCell(sheet,3, 3, 5, 7) ;

        // 合并第4-5行 第9列
        ExportExcelUtil.mergeCell(sheet,3, 4, 8, 8) ;

        // 第4行 表头
        Map<Integer,String> m = new HashMap<>() ;
        m.put(0,"序号") ;
        m.put(1,"车号") ;
        m.put(2,"年度分配") ;
        m.put(3,"增拨") ;
        m.put(4,"转供") ;
        m.put(5,"充卡支出") ;
        m.put(6,"") ;
        m.put(7,"") ;
        m.put(8,"结存") ;

        // 创建第4行
        HSSFRow row = sheet.createRow(3);
        Stream.iterate(0,i->i+1).limit(9).forEach(i->{
            HSSFCell celli = ExportExcelUtil.createCells(sheet,row,i) ;
            celli.setCellValue(m.get(i));
            celli.setCellStyle(ExportExcelUtil.setTableHeadStyle(workbook));
         });
        // 创建第5行
        HSSFRow row4 = sheet.createRow(4);
        Stream.iterate(0,a->a+1).limit(9).forEach(a->{
            HSSFCell cell = ExportExcelUtil.createCells(sheet,row4,a) ;
            cell.setCellStyle(ExportExcelUtil.setTableHeadStyle(workbook));
            if(a == 5){
                cell.setCellValue("92#");
            }else if(a == 6){
                cell.setCellValue("95#");
            }else if(a == 7){
                cell.setCellValue("-35#");
            }else{
                cell.setCellValue("");
            }
        });


        // 获取数据
        List<TLqbzYlgyzbz> list =  ylgyzbzService.getExportDataList(param);

        double totalNdfp = list.stream().mapToDouble(TLqbzYlgyzbz::getNiandufenpei).sum();
        double totalzb = list.stream().mapToDouble(TLqbzYlgyzbz::getZengbo).sum();
        double totalzg = list.stream().mapToDouble(TLqbzYlgyzbz::getZhuangong).sum();
        double total92 = list.stream().mapToDouble(TLqbzYlgyzbz::getZhichuleixing92).sum();
        double total95 = list.stream().mapToDouble(TLqbzYlgyzbz::getZhichuleixing95).sum();
        double total35 = list.stream().mapToDouble(TLqbzYlgyzbz::getZhichuleixing35).sum();
        double totaljc = list.stream().mapToDouble(TLqbzYlgyzbz::getUnitinformation).sum();

        Stream.iterate(0, i -> i + 1).limit(list.size()).forEach(i -> {
            Map<Integer,String> m1 = new HashMap<>() ;
            m1.put(0,i+"") ;
            m1.put(1,list.get(i).getNumberplate()) ;
            m1.put(2,list.get(i).getNiandufenpei()+"") ;
            m1.put(3,list.get(i).getZengbo()+"") ;
            m1.put(4,list.get(i).getZhuangong()+"") ;
            m1.put(5,list.get(i).getZhichuleixing92()+"") ;
            m1.put(6,list.get(i).getZhichuleixing95()+"") ;
            m1.put(7,list.get(i).getZhichuleixing35()+"") ;
            m1.put(8,list.get(i).getUnitinformation()+"") ;

            HSSFRow rowi = sheet.createRow(5+i);
            rowi.setHeightInPoints(30);//设置行的高度
            // 遍历创建列
            Stream.iterate(0, c -> c + 1).limit(9).forEach(c -> {
                HSSFCell celli = rowi.createCell(c);
                if (i == 0 && c== 0){
                    celli.setCellValue("");
                }else{
                    celli.setCellValue(m1.get(c));
                }
                celli.setCellStyle(ExportExcelUtil.setDataStyle(workbook));
            });


        });

        HSSFRow rowi = sheet.createRow(5+list.size());
        Stream.iterate(0, c -> c + 1).limit(9).forEach(c -> {
            HSSFCell celli = rowi.createCell(c);
            if (c == 0 ){
                celli.setCellValue("合计");
            }else if (c == 2){
                celli.setCellValue(totalNdfp);
            }else if (c == 3){
                celli.setCellValue(totalzb);
            }else if (c == 4){
                celli.setCellValue(totalzg);
            }else if (c == 5){
                celli.setCellValue(total92);
            }else if (c == 6){
                celli.setCellValue(total95);
            }else if (c == 7){
                celli.setCellValue(total35);
            }else if (c == 8){
                celli.setCellValue(totaljc);
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
    public void exportJykxxtj(HttpServletResponse response, HttpServletRequest request,@RequestBody TLqbzJykxxtjParam param) throws IOException {

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
        HSSFSheet sheet = workbook.createSheet("加油卡");

        ExportExcelUtil.createCommonSheet(sheet,workbook,"附件4","加油卡信息统计表",8) ;

        // 第三行
        HSSFRow row3 = sheet.createRow(2);
        HSSFCell cell3 = row3.createCell(0);
        cell3.setCellValue("单位：（盖章）"+param.getBianzhidanwei());
        cell3.setCellStyle(ExportExcelUtil.setThirdStyle(workbook));

        HSSFCell cell7 = row3.createCell(6);
        cell7.setCellValue("填报时间:" + param.getTianbaoshijian().substring(0,4) + " 年" + param.getTianbaoshijian().substring(5,7) +" 月" + param.getTianbaoshijian().substring(8,10)+" 日");
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
        m.put(6,"剩余指标") ;
        m.put(7,"确认签字") ;

        // 创建第4行
        HSSFRow row = sheet.createRow(3);
        Stream.iterate(0,i->i+1).limit(8).forEach(i->{
            HSSFCell celli = ExportExcelUtil.createCells(sheet,row,i) ;
            celli.setCellValue(m.get(i));
            celli.setCellStyle(ExportExcelUtil.setTableHeadStyle(workbook));
        });

        // 获取数据
        List<TLqbzJykxxtj> list =  service.getExportDataList(param);
        Stream.iterate(0, i -> i + 1).limit(list.size()).forEach(i -> {
            Map<Integer,String> m1 = new HashMap<>() ;
            m1.put(0,(i+1)+"") ;
            m1.put(1,list.get(i).getFuelcardnumber()) ;
            m1.put(2,list.get(i).getNumberplate()+"") ;
            m1.put(3,list.get(i).getBrandmodel()+"") ;
            m1.put(4,list.get(i).getEnginenumber()+"") ;
            m1.put(5,list.get(i).getFramenumber()+"") ;
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
    public void exportJykrcgl(HttpServletResponse response, HttpServletRequest request, @RequestBody TLqbzJygcjykrcgldjParam param) throws IOException {

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

        ExportExcelUtil.createCommonSheet(sheet,workbook,"附件4-2","军油工程加油卡日常管理登记本",8) ;

        // 第三行

        Map<Integer,String> m = new HashMap<>() ;
        m.put(0,"序号") ;
        m.put(1,"加油卡号") ;
        m.put(2,"车辆牌号") ;
        m.put(3,"油品") ;
        m.put(4,"领卡事由") ;
        m.put(5,"领取时间") ;
        m.put(6,"卡内油量（L）") ;
        m.put(7,"归还时间") ;
        m.put(8,"卡内余额") ;
        m.put(9,"驾驶员签字") ;

        // 创建第3行
        HSSFRow row = sheet.createRow(2);
        Stream.iterate(0,i->i+1).limit(10).forEach(i->{
            HSSFCell celli = ExportExcelUtil.createCells(sheet,row,i) ;
            celli.setCellValue(m.get(i));
            celli.setCellStyle(ExportExcelUtil.setTableHeadStyle(workbook));
        });

        // 获取数据
        List<TLqbzJygcjykrcgldj> list =  jygcjykrcgldjService.getExportDataList(param);
        Stream.iterate(0, i -> i + 1).limit(list.size()).forEach(i -> {
            Map<Integer,String> m1 = new HashMap<>() ;
            m1.put(0,(i+1)+"") ;
            m1.put(1,list.get(i).getFuelcardnumber()) ;
            m1.put(2,list.get(i).getNumberplate()+"") ;
            m1.put(3,list.get(i).getYoupin()+"") ;
            m1.put(4,list.get(i).getLingkashiyou()+"") ;
            m1.put(5,list.get(i).getLingqusj()+"") ;
            m1.put(6,list.get(i).getKaneiyouliang()+"") ;
            m1.put(7,list.get(i).getGuihuanshijian()+"") ;
            m1.put(8,list.get(i).getKaneiyue()+"") ;
            m1.put(9,list.get(i).getJaishiyuanqianzi()+"") ;

            HSSFRow rowi = sheet.createRow(3+i);
            rowi.setHeightInPoints(30);//设置行的高度
            // 遍历创建列
            Stream.iterate(0, c -> c + 1).limit(10).forEach(c -> {
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
    public void exportCzpz(HttpServletResponse response, HttpServletRequest request, @RequestBody TLqbzYlgyczpzParam param) throws IOException {

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

        ExportExcelUtil.createCommonSheet(sheet,workbook,"附件5","油料供应充值凭证",7) ;

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
        cell7.setCellValue("充值时间" );
        cell7.setCellStyle(ExportExcelUtil.setTableHeadStyle(workbook));

        HSSFCell cell8 = row3.createCell(5);
        cell8.setCellValue(param.getChongzhisj());
        cell8.setCellStyle(ExportExcelUtil.setTableHeadStyle(workbook));

        HSSFCell cell9 = row3.createCell(6);
        cell9.setCellValue("");
        cell9.setCellStyle(ExportExcelUtil.setTableHeadStyle(workbook));

        // 合并第3行 2,3,4列
        ExportExcelUtil.mergeCell(sheet,2,2,1,3) ;
        // 合并第3行 6,7列
        ExportExcelUtil.mergeCell(sheet,2,2,5,6) ;
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
        // 合并第4行 第6，7列
        ExportExcelUtil.mergeCell(sheet,3,3,5,6);

        Map<Integer,String> m = new HashMap<>() ;
        m.put(0,"充值明细") ;
        m.put(1,"车牌号码") ;
        m.put(2,"油品") ;
        m.put(3,"数量（公斤）") ;
        m.put(4,"经办人") ;
        m.put(5,"仪表显示公里（公里）") ;
        m.put(6,"") ;

        // 创建第4行
        HSSFRow row4 = sheet.createRow(3);
        row3.setHeightInPoints(height);
        Stream.iterate(0,i->i+1).limit(7).forEach(i->{
            HSSFCell celli = ExportExcelUtil.createCells(sheet,row4,i) ;
            celli.setCellValue(m.get(i));
            celli.setCellStyle(ExportExcelUtil.setTableHeadStyle(workbook));
        });

        // 创建第5行
        HSSFRow row5 = sheet.createRow(4);
        row3.setHeightInPoints(height);
        Stream.iterate(0,i->i+1).limit(7).forEach(i->{
            HSSFCell celli = ExportExcelUtil.createCells(sheet,row5,i) ;
            celli.setCellValue("");
            if (i == 5 )celli.setCellValue("上次");
            if (i == 6 )celli.setCellValue("本次");
            celli.setCellStyle(ExportExcelUtil.setTableHeadStyle(workbook));
        });


        // 获取数据
        List<TLqbzYlgyczpz> list =  ylgyczpzService.getExportDataList(param);

        double total = list.stream().mapToDouble(TLqbzYlgyczpz::getShuliang).sum();

        Stream.iterate(0, i -> i + 1).limit(list.size()).forEach(i -> {
            Map<Integer,String> m1 = new HashMap<>() ;
            m1.put(0,"") ;
            m1.put(1,list.get(i).getNumberplate()) ;
            m1.put(2,list.get(i).getYoupin()+"") ;
            m1.put(3,list.get(i).getShuliang()+"") ;
            m1.put(4,list.get(i).getJingbanren()+"") ;
            m1.put(5,list.get(i).getShangciyibiaoshu()+"") ;
            m1.put(6,list.get(i).getBenciyibiaoshu()+"") ;

            HSSFRow rowi = sheet.createRow(5+i);
            rowi.setHeightInPoints(height);//设置行的高度
            // 遍历创建列
            Stream.iterate(0, c -> c + 1).limit(7).forEach(c -> {
                HSSFCell celli = rowi.createCell(c);
                if (i == 0 && c== 0){
                    celli.setCellValue("");
                }else{
                    celli.setCellValue(m1.get(c));
                }
                celli.setCellStyle(ExportExcelUtil.setDataStyle(workbook));
            });

        });
        // 合计行上方创建一行空白行
        HSSFRow rowkh = sheet.createRow(4+list.size()+1);
        rowkh.setHeightInPoints(height);//设置行的高度
        Stream.iterate(0,a->a+1).limit(7).forEach(a->{
            HSSFCell celli = rowkh.createCell(a);
            celli.setCellValue("");
            celli.setCellStyle(ExportExcelUtil.setDataStyle(workbook));
        });
        // 创建合计行
        HSSFRow rowhj = sheet.createRow(4+list.size()+2);
        rowhj.setHeightInPoints(height);//设置行的高度
        // 遍历创建列
        Stream.iterate(0, c -> c + 1).limit(7).forEach(c -> {
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

        ExportExcelUtil.mergeCell(sheet,4+list.size()+2,4+list.size()+2,2,6) ;
        ExportExcelUtil.mergeCell(sheet,3,3+1+list.size()+2,0,0) ;

        // 创建用油单位意见行
        HSSFRow rowyj = sheet.createRow(4+list.size()+3);
        rowyj.setHeightInPoints(60);

        Stream.iterate(0, c -> c + 1).limit(7).forEach(c -> {
            HSSFCell celli = rowyj.createCell(c);
            if (c == 0 ){
                celli.setCellValue("用油单位意见");
            }else{
                celli.setCellValue("单位盖章：                        负责人签名：");
            }
            celli.setCellStyle(ExportExcelUtil.setDataStyle(workbook));
        });

        // 合并用油单位意见行2-7列
        ExportExcelUtil.mergeCell(sheet,4+list.size()+3,4+list.size()+3,1,6) ;

        // 油料审核部门意见
        HSSFRow rowshyj = sheet.createRow(4+list.size()+4);
        rowshyj.setHeightInPoints(60);

        Stream.iterate(0, c -> c + 1).limit(7).forEach(c -> {
            HSSFCell celli = rowshyj.createCell(c);
            if (c == 0 ){
                celli.setCellValue("油料审核部门意见");
            }else{
                celli.setCellValue("");
            }
            celli.setCellStyle(ExportExcelUtil.setDataStyle(workbook));
        });

        // 合并油料审核部门意见行2-7列
        ExportExcelUtil.mergeCell(sheet,4+list.size()+4,4+list.size()+4,1,6) ;

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
    public void exportDcyh(HttpServletResponse response, HttpServletRequest request,@RequestBody TLqbzDcyhkpParam param) throws IOException {

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

        ExportExcelUtil.createCommonSheet(sheet,workbook,"附件6","单车油耗考核卡片",9) ;

        TLqbzDcyhkp dcyhkp = dcyhkpService.getDcyhxx(param) ;

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

        List<TLqbzDcyhkp> list = dcyhkpService.getDcyhxxList(param) ;

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
     * 导出二级单位油料指标供应账
     * @param response
     * @param request
     * @param param
     */
    @PostMapping("/exportEjYlzb")
    public void exportEjYlzb(HttpServletResponse response, HttpServletRequest request, @RequestBody TLqbzYlgyzbzParam param) throws IOException {

        String path = staticFilePath + zbglFilePath ;

        File file = new File(path) ;
        if (!file.exists()){
            file.mkdirs() ;
        }
        int columnCount = 8 ;
        //指定数据存放的位置
        OutputStream outputStream = new FileOutputStream(path +"ejzbgl.xls");//D://file//export//lqbz//zbgl/zbgl.xls
        //1.创建一个工作簿
        HSSFWorkbook workbook = new HSSFWorkbook();
        //2.创建一个工作表sheet
        HSSFSheet sheet = workbook.createSheet("指标管理");

        ExportExcelUtil.createCommonSheet(sheet,workbook,"附件2",param.getYear()+"年油料供应指标账（上级单位）",columnCount) ;

        // 第三行 编制单位，计算单位
        HSSFRow row3 = sheet.createRow(2);
        HSSFCell cell3 = row3.createCell(0);
        cell3.setCellValue("编制单位："+param.getBianzhidanwei());
        cell3.setCellStyle(ExportExcelUtil.setThirdStyle(workbook));

        HSSFCell cell7 = row3.createCell(7);
        cell7.setCellValue("计算单位：公斤");
        cell7.setCellStyle(ExportExcelUtil.setThirdStyle(workbook));

        // 合并第3行 1-7列
        ExportExcelUtil.mergeCell(sheet,2, 2, 0, 6) ;

        // 合并4-5行 第1列
        ExportExcelUtil.mergeCell(sheet,3, 4, 0, 0) ;

        // 合并4-5 行 第2列
        ExportExcelUtil.mergeCell(sheet,3, 4, 1, 1) ;

        // 合并第4-5行 第8列
        ExportExcelUtil.mergeCell(sheet,3, 4, 7, 7) ;
        // 合并第 4行  第3,7列
        ExportExcelUtil.mergeCell(sheet,3, 3, 2, 6) ;

        // 第4行 表头
        Map<Integer,String> m = new HashMap<>() ;
        m.put(0,"序号") ;
        m.put(1,"摘要") ;
        m.put(2,param.getYear() + "年度") ;
        m.put(3,"") ;
        m.put(4,"") ;
        m.put(5,"") ;
        m.put(6,"") ;
        m.put(7,"备注") ;

        // 创建第4行
        HSSFRow row = sheet.createRow(3);
        Stream.iterate(0,i->i+1).limit(columnCount).forEach(i->{
            HSSFCell celli = ExportExcelUtil.createCells(sheet,row,i) ;
            celli.setCellValue(m.get(i));
            celli.setCellStyle(ExportExcelUtil.setTableHeadStyle(workbook));
        });
        // 创建第5行
        HSSFRow row4 = sheet.createRow(4);
        Stream.iterate(0,a->a+1).limit(columnCount).forEach(a->{
            HSSFCell cell = ExportExcelUtil.createCells(sheet,row4,a) ;
            cell.setCellStyle(ExportExcelUtil.setTableHeadStyle(workbook));
            if(a == 2){
                cell.setCellValue("年度分配");
            }else if(a == 3){
                cell.setCellValue("战区增拨");
            }else if(a == 4){
                cell.setCellValue("转供支出");
            }else if(a == 5){
                cell.setCellValue("充卡支出");
            }else if(a == 6){
                cell.setCellValue("结存");
            }else{
                cell.setCellValue("");
            }
        });

        param.setType(1) ;
        // 获取数据
        List<TLqbzYlgyzbz> list =  ylgyzbzService.getExportDataList(param);

        double totalNdfp = list.stream().mapToDouble(TLqbzYlgyzbz::getNiandufenpei).sum();
        double totalzb = list.stream().mapToDouble(TLqbzYlgyzbz::getZengbo).sum();
        double totalzg = list.stream().mapToDouble(TLqbzYlgyzbz::getZhuangong).sum();
        double totalzc = list.stream().mapToDouble(TLqbzYlgyzbz::getChongkazc).sum();
        double totaljc = list.stream().mapToDouble(TLqbzYlgyzbz::getUnitinformation).sum();

        Stream.iterate(0, i -> i + 1).limit(list.size()).forEach(i -> {
            Map<Integer,String> m1 = new HashMap<>() ;
            m1.put(0,i+"") ;
            m1.put(1,list.get(i).getNumberplate()) ;
            m1.put(2,list.get(i).getNiandufenpei()+"") ;
            m1.put(3,list.get(i).getZengbo()+"") ;
            m1.put(4,list.get(i).getZhuangong()+"") ;
            m1.put(5,list.get(i).getChongkazc()+"") ;
            m1.put(6,list.get(i).getUnitinformation()+"") ;
            m1.put(7,list.get(i).getRemark()+"") ;

            HSSFRow rowi = sheet.createRow(5+i);
            rowi.setHeightInPoints(30);//设置行的高度
            // 遍历创建列
            Stream.iterate(0, c -> c + 1).limit(columnCount).forEach(c -> {
                HSSFCell celli = rowi.createCell(c);
                if (i == 0 && c== 0){
                    celli.setCellValue("");
                }else{
                    celli.setCellValue(m1.get(c));
                }
                celli.setCellStyle(ExportExcelUtil.setDataStyle(workbook));
            });


        });

        HSSFRow rowi = sheet.createRow(5+list.size());
        Stream.iterate(0, c -> c + 1).limit(columnCount).forEach(c -> {
            HSSFCell celli = rowi.createCell(c);
            if (c == 0 ){
                celli.setCellValue("合计");
            }else if (c == 2){
                celli.setCellValue(totalNdfp);
            }else if (c == 3){
                celli.setCellValue(totalzb);
            }else if (c == 4){
                celli.setCellValue(totalzg);
            }else if (c == 5){
                celli.setCellValue(totalzc);
            }else if (c == 6){
                celli.setCellValue(totaljc);
            }
            celli.setCellStyle(ExportExcelUtil.setDataStyle(workbook));
        });

        ExportExcelUtil.mergeCell(sheet,5+list.size(),5+list.size(),0,1) ;

        workbook.write(outputStream);
        outputStream.close();

        ZipMultiFileUtil.downLoad(response,request,path+"ejzbgl.xls") ;
    }
}