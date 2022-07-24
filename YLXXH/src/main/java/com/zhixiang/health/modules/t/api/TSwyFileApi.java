package com.zhixiang.health.modules.t.api;

import com.zhixiang.health.common.utils.ZipMultiFileUtil;
import com.zhixiang.health.common.utils.poi.excel.ExportExcelUtil;
import com.zhixiang.health.modules.t.model.entity.*;
import com.zhixiang.health.modules.t.model.param.*;
import com.zhixiang.health.modules.t.service.ITShhJytjService;
import com.zhixiang.health.modules.t.service.ITSwyDcyhService;
import com.zhixiang.health.modules.t.service.ITSwyDjService;
import com.zhixiang.health.modules.t.service.ITSwySwylzService;
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
import java.util.stream.Stream;

@Controller
@RequestMapping("/t/swy/file/")
public class TSwyFileApi {

    @Value("${ylxxh.common.static-file-path}")
    private String staticFilePath;

    @Value("${ylxxh.export.swy.swygl}")
    private String swyglFilePath ;

    @Value("${ylxxh.export.swy.djb}")
    private String djbFilePath ;

    @Value("${ylxxh.export.swy.dcyh}")
    private String dcyhFilePath ;

    @Resource
    private ITSwySwylzService swylzService ;

    @Resource
    private ITSwyDjService djService ;

    @Resource
    private ITSwyDcyhService dcyhService ;


    /**
     * 导出三级单位油料指标供应账
     * @param response
     * @param request
     * @param param
     */
    @PostMapping("/exportSwygl")
    public void exportSwygl(HttpServletResponse response, HttpServletRequest request, @RequestBody TSwySwylzParam param) throws IOException {

        String path = staticFilePath + swyglFilePath ;

        File file = new File(path) ;
        if (!file.exists()){
            file.mkdirs() ;
        }
        //指定数据存放的位置
        OutputStream outputStream = new FileOutputStream(path +"swylz.xls");//D://file//export//lqbz//zbgl/zbgl.xls
        //1.创建一个工作簿
        HSSFWorkbook workbook = new HSSFWorkbook();
        //2.创建一个工作表sheet
        HSSFSheet sheet = workbook.createSheet("实物油管理");

        ExportExcelUtil.createCommonSheet(sheet,workbook,"附件12","价拨/实物油料账",8) ;

        // 第三行 编制单位，计算单位
        HSSFRow row3 = sheet.createRow(2);
        HSSFCell cell3 = row3.createCell(0);
        cell3.setCellValue("用油单位（盖章）："+param.getYongyoudanwei());
        cell3.setCellStyle(ExportExcelUtil.setThirdStyle(workbook));

        // 合并第3行 1-8列
        ExportExcelUtil.mergeCell(sheet,2, 2, 0, 7) ;

        // 合并4-5行 第1列
        ExportExcelUtil.mergeCell(sheet,3, 4, 0, 0) ;

        // 合并4-5 行 第2列
        ExportExcelUtil.mergeCell(sheet,3, 4, 1, 1) ;

        // 合并4-5行 第3列
        ExportExcelUtil.mergeCell(sheet,3, 4, 2, 2) ;

        // 合并4-5行 第4列
        ExportExcelUtil.mergeCell(sheet,3, 4, 3, 3) ;

        // 合并4-5行 第5列
        ExportExcelUtil.mergeCell(sheet,3, 4, 4, 4) ;

        // 合并第4行 6-8列
        ExportExcelUtil.mergeCell(sheet,3, 3, 5, 7) ;

        // 第4行 表头
        Map<Integer,String> m = new HashMap<>() ;
        m.put(0,"序号") ;
        m.put(1,"日期") ;
        m.put(2,"凭证号") ;
        m.put(3,"摘要") ;
        m.put(4,"油品") ;
        m.put(5,"油料指标") ;
        m.put(6,"") ;
        m.put(7,"") ;

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
            if(a == 5){
                cell.setCellValue("收入");
            }else if(a ==6){
                cell.setCellValue("支出");
            }else if(a == 7){
                cell.setCellValue("结存");
            }else{
                cell.setCellValue("");
            }
        });


//        // 获取数据
        List<TSwySwylz> list =  swylzService.getExportDataList(param);
        Stream.iterate(0, i -> i + 1).limit(list.size()).forEach(i -> {
            Map<Integer,String> m1 = new HashMap<>() ;
            m1.put(0,i+"") ;
            m1.put(1,list.get(i).getRiqi()) ;
            m1.put(2,list.get(i).getPingzhenghao()+"") ;
            m1.put(3,list.get(i).getZhaiyao()+"") ;
            m1.put(4,list.get(i).getYoupin()+"") ;
            m1.put(5,list.get(i).getYouliaozhibiaoshouru()+"") ;
            m1.put(6,list.get(i).getYouliaozhibiaozhichu()+"") ;
            m1.put(7,list.get(i).getYouliaozhibiaojeicun()+"") ;

            HSSFRow rowi = sheet.createRow(5+i);
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

        ZipMultiFileUtil.downLoad(response,request,path+"swylz.xls") ;
    }


    /**
     * 导出三级单位加油卡日常管理
     * @param response
     * @param request
     * @param param
     */
    @PostMapping("/exportDjb")
    public void exportDjb(HttpServletResponse response, HttpServletRequest request,@RequestBody TSwyDjParam param) throws IOException {

        String path = staticFilePath + djbFilePath ;

        File file = new File(path) ;
        if (!file.exists()){
            file.mkdirs() ;
        }
        //指定数据存放的位置
        OutputStream outputStream = new FileOutputStream(path +"djb.xls");//D://file//export//lqbz//zbgl/zbgl.xls
        //1.创建一个工作簿
        HSSFWorkbook workbook = new HSSFWorkbook();
        //2.创建一个工作表sheet
        HSSFSheet sheet = workbook.createSheet("加油卡日常管理");

        ExportExcelUtil.createCommonSheet(sheet,workbook,"附件13","价拨实物油加注登记表",9) ;

        // 第三行 编制单位，计算单位
        HSSFRow row3 = sheet.createRow(2);
        HSSFCell cell3 = row3.createCell(0);
        cell3.setCellValue("用油单位（盖章）："+param.getYongyoudanwei());
        cell3.setCellStyle(ExportExcelUtil.setThirdStyle(workbook));

        HSSFCell cell9 = row3.createCell(8);
        cell9.setCellValue("第 "+param.getPingzhenghao()+" 号");
        cell9.setCellStyle(ExportExcelUtil.setThirdStyle(workbook));

        ExportExcelUtil.mergeCell(sheet,2,2,0,2) ;

        Map<Integer,String> m = new HashMap<>() ;
        m.put(0,"序号") ;
        m.put(1,"日期") ;
        m.put(2,"装备号") ;
        m.put(3,"发动机号") ;
        m.put(4,"车架号") ;
        m.put(5,"油品") ;
        m.put(6,"加油量（L）") ;
        m.put(7,"司机签字") ;
        m.put(8,"加油员签字") ;

        // 创建第4行
        HSSFRow row = sheet.createRow(3);
        Stream.iterate(0,i->i+1).limit(9).forEach(i->{
            HSSFCell celli = ExportExcelUtil.createCells(sheet,row,i) ;
            celli.setCellValue(m.get(i));
            celli.setCellStyle(ExportExcelUtil.setTableHeadStyle(workbook));
        });

        // 获取数据
        List<TSwyDj> list =  djService.getExportDataList(param);
        Stream.iterate(0, i -> i + 1).limit(list.size()).forEach(i -> {
            Map<Integer,String> m1 = new HashMap<>() ;
            m1.put(0,(i+1)+"") ;
            m1.put(1,list.get(i).getRiqi()) ;
            m1.put(2,list.get(i).getZhuangbeihao()+"") ;
            m1.put(3,list.get(i).getFadongjihao()+"") ;
            m1.put(4,list.get(i).getChejiahao()+"") ;
            m1.put(5,list.get(i).getYoupin()+"") ;
            m1.put(6,list.get(i).getYouliaozhibiaojeicun()+"") ;
            m1.put(7,list.get(i).getSijiname() == null ? "":list.get(i).getSijiname()+"") ;
            m1.put(8,list.get(i).getJiayouyuanname() == null ? "":list.get(i).getJiayouyuanname()+"") ;

            HSSFRow rowi = sheet.createRow(4+i);
            rowi.setHeightInPoints(30);//设置行的高度
            // 遍历创建列
            Stream.iterate(0, c -> c + 1).limit(9).forEach(c -> {
                HSSFCell celli = rowi.createCell(c);
                celli.setCellValue(m1.get(c));
                celli.setCellStyle(ExportExcelUtil.setDataStyle(workbook));
            });
        });

        workbook.write(outputStream);
        outputStream.close();

        ZipMultiFileUtil.downLoad(response,request,path+"djb.xls") ;
    }


    /**
     * 导出三级单位单车油耗
     * @param response
     * @param request
     * @param param
     */
    @PostMapping("/exportDcyh")
    public void exportDcyh(HttpServletResponse response, HttpServletRequest request,@RequestBody TSwyDcyhParam param) throws IOException {

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

        ExportExcelUtil.createCommonSheet(sheet,workbook,"附件14","XXXX单车油耗考核卡片（实物油）",9) ;

        TSwyDcyh dcyhkp = dcyhService.getDcyhxx(param) ;

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
                celli.setCellValue(dcyhkp.getYongyoudanwei());
                else celli.setCellValue("");
            }else if (i ==4){
                celli.setCellValue("车牌号");
            }else if (i ==5){
                if (dcyhkp!= null)
                celli.setCellValue(dcyhkp.getChepaihaoma());
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

        List<TSwyDcyh> list = dcyhService.getDcyhxxList(param) ;

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

}