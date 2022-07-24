package com.zhixiang.health.common.utils.poi.excel;

import org.apache.poi.hssf.record.cf.FontFormatting;
import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.VerticalAlignment;
import org.apache.poi.ss.util.CellRangeAddress;

import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.stream.Stream;

public class ExportExcelUtil {

    public static void export(String fj,String title) throws Exception{
//        //指定数据存放的位置
//        OutputStream outputStream = new FileOutputStream("D:\\test.xls");
//        //1.创建一个工作簿
//        HSSFWorkbook workbook = new HSSFWorkbook();
//        //2.创建一个工作表sheet
//        HSSFSheet sheet = workbook.createSheet("sheet1");
//
//        HSSFRow row1 = sheet.createRow(0);
//        HSSFCell cell1 = row1.createCell(0);
////        //设置值，这里合并单元格后相当于标题
//        cell1.setCellValue(fj);
//        HSSFCellStyle style = setStyle(workbook) ;
////        //将样式添加生效
//        cell1.setCellStyle(style);
//
//        HSSFRow row2 = sheet.createRow(1);
//        HSSFCell cell2 = row2.createCell(0);
//        cell2.setCellStyle(style);
//
////        for(int i = 0;i<userList.size();i++){
////            //行
////            HSSFRow row = sheet.createRow(i+1);
////            //对列赋值
////            row.createCell(0).setCellValue(userList.get(i).getId());
////            row.createCell(1).setCellValue(userList.get(i).getName());
////            row.createCell(2).setCellValue(userList.get(i).getPassword());
////            row.createCell(3).setCellValue(userList.get(i).getRemark());
////        }
//        workbook.write(outputStream);
//        outputStream.close();
    }

    /**
     * 设置居中样式
     * @param style
     * @return
     */
    public static  HSSFCellStyle setCenterStyle(HSSFCellStyle style){
        style.setAlignment(HorizontalAlignment.CENTER);//水平居中
        style.setVerticalAlignment(VerticalAlignment.CENTER);//垂直居中
        return style ;
    }

    /**
     * 设置首行样式
     */
    public static HSSFCellStyle setFirstRowStyle(HSSFWorkbook workbook){
        HSSFCellStyle style = workbook.createCellStyle();
        HSSFFont font = workbook.createFont();
        font.setFontName("黑体");//设置字体名称
        font.setFontHeightInPoints((short)14);//设置字号
        font.setItalic(false);//设置是否为斜体
        font.setBold(false);//设置是否加粗
        font.setColor(IndexedColors.BLACK.index);//设置字体颜色
        style.setFont(font);

        return style ;

    }

    /**
     * 设置标题行样式
     * @return
     */
    public static  HSSFCellStyle setTitleStyle(HSSFWorkbook workbook){
        HSSFCellStyle style = workbook.createCellStyle();
        setCenterStyle(style) ;
        HSSFFont font = workbook.createFont();
        font.setFontName("方正小标宋简体");//设置字体名称
        font.setFontHeightInPoints((short)22);//设置字号
        style.setFont(font);

        return style ;
    }

    /**
     * 第三行样式
     * @param workbook
     * @return
     */
    public static  HSSFCellStyle setThirdStyle(HSSFWorkbook workbook){
        HSSFCellStyle style = workbook.createCellStyle();
        HSSFFont font = workbook.createFont();
        font.setFontName("楷体_GB2312");//设置字体名称
        font.setFontHeightInPoints((short)10);//设置字号
        style.setFont(font);

        return style ;
    }

    /**
     * 表头样式
     * @param workbook
     * @return
     */
    public static  HSSFCellStyle setTableHeadStyle(HSSFWorkbook workbook){
        HSSFCellStyle style = workbook.createCellStyle();
        setCenterStyle(style) ;
        setBorder(style) ;
        HSSFFont font = workbook.createFont();
        font.setFontName("方正小标宋简体");//设置字体名称
        font.setFontHeightInPoints((short)10);//设置字号
        style.setFont(font);

        return style ;
    }

    /**
     * 数据样式
     * @param workbook
     * @return
     */
    public static  HSSFCellStyle setDataStyle(HSSFWorkbook workbook){
        HSSFCellStyle style = workbook.createCellStyle();
        setCenterStyle(style) ;
        setBorder(style);
        HSSFFont font = workbook.createFont();
        font.setFontName("宋体");//设置字体名称
        font.setFontHeightInPoints((short)10);//设置字号
        style.setFont(font);
        style.setWrapText(true);
        return style ;
    }

    public static HSSFCellStyle setBorder(HSSFCellStyle style){
        style.setBorderBottom(BorderStyle.THIN);//下边框
        style.setBorderLeft(BorderStyle.THIN);//左边框
        style.setBorderRight(BorderStyle.THIN);//右边框
        style.setBorderTop(BorderStyle.THIN); //上边框
        return style ;
    }

    public static HSSFSheet createCommonSheet(HSSFSheet sheet,HSSFWorkbook workbook,String firstTitle,String title,int columnCount){
        Stream.iterate(0, i -> i + 1).limit(columnCount).forEach(i -> {
            if (i == 0){
                sheet.setColumnWidth(i, 10 * 256);//设置列的宽度
            }else{
                sheet.setColumnWidth(i, 20 * 256);//设置列的宽度
            }
        });

        //第一行
        HSSFRow row1 = sheet.createRow(0);
        HSSFCell cell1 = row1.createCell(0);
        //设置值，这里合并单元格后相当于标题
        cell1.setCellValue(firstTitle);
        cell1.setCellStyle(ExportExcelUtil.setFirstRowStyle(workbook));

        // 第二行 标题
        HSSFRow row2 = sheet.createRow(1);
        HSSFCell cell2 = row2.createCell(0);
        cell2.setCellValue(title);
        cell2.setCellStyle(ExportExcelUtil.setTitleStyle(workbook));

        CellRangeAddress region=new CellRangeAddress(1, 1, 0, columnCount);
        sheet.addMergedRegion(region);
        return sheet ;
    }

    /**
     * 合并单元格
     * @param sheet
     * @param firstRow
     * @param lastRow
     * @param firstCol
     * @param lastCol
     * @return
     */
    public static HSSFSheet mergeCell(HSSFSheet sheet,int firstRow,int lastRow,int firstCol,int lastCol){
        CellRangeAddress region=new CellRangeAddress(firstRow, lastRow, firstCol, lastCol);
        sheet.addMergedRegion(region);
        return sheet ;
    }

    public static HSSFCell createCells(HSSFSheet sheet,HSSFRow row ,int col){
        row.setHeightInPoints(15);
        HSSFCell cell = row.createCell(col);
        return cell ;
    }
}
