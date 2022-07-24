package com.zhixiang.health.common.utils.poi.excel;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.StrUtil;
import com.zhixiang.health.common.utils.poi.ExcelField;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.util.ReflectionUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.*;

public class ImportExcelUtil {

    /**
     * 工作薄对象
     */
    public Workbook wb;

    /**
     * 工作表对象
     */
    public Sheet sheet;

    /**
     * 标题行号
     */
    public int headerNum;

    public ImportExcelUtil(File file, int headerNum) throws IOException {
        this(file, headerNum, 0);
    }

    public ImportExcelUtil(File file, int headerNum, int sheetIndex) throws IOException {
        this(file.getName(), new FileInputStream(file), headerNum, sheetIndex);
    }

    public ImportExcelUtil(String fileName, InputStream is, int headerNum, int sheetIndex) throws IOException {
        if (StringUtils.isBlank(fileName)) throw new RuntimeException("导入文档为空!");

        if(fileName.toLowerCase().endsWith("xls")) this.wb = new HSSFWorkbook(is);
        else if(fileName.toLowerCase().endsWith("xlsx")) this.wb = new XSSFWorkbook(is);
        else throw new RuntimeException("文档格式不正确!");

        if (this.wb.getNumberOfSheets()<sheetIndex) throw new RuntimeException("文档中没有工作表!");

        this.sheet = this.wb.getSheetAt(sheetIndex);
        this.headerNum = headerNum;
    }

    /**
     * 获取导入数据列表
     * @param cls 导入对象类型
     * @return 对象实体list
     * @throws IllegalAccessException
     * @throws InstantiationException
     */
    public <E> List<E> getDataList(Class<E> cls) throws Exception {
        List<Object[]> annotationList = CollectionUtil.newArrayList();

        // 获取ExcelField注解的字段
        Field[] fs = cls.getDeclaredFields();
        Arrays.stream(fs).forEach(f -> {
            ExcelField ef = f.getAnnotation(ExcelField.class);
            if (null != ef) annotationList.add(new Object[]{ef, f});
        });

        List<E> dataList = CollectionUtil.newArrayList();
        // 遍历Excel单元格
        for (int i = this.getDataRowNum(); i < this.getLastDataRowNum(); i++) {
            E e = cls.newInstance();

            int column = 0;
            Row row = this.getRow(i);
            // 获取实体字段值
            for (Object[] os : annotationList){
                Object val = this.getCellValue(row, column++);

                if (val != null && StrUtil.isNotEmpty(val.toString())){
                    Class<?> valType = ((Field)os[1]).getType();
                    val = this.converValue(valType, val);

                    Field f = (Field)os[1];
                    f.setAccessible(true);
                    ReflectionUtils.setField(f, e, val);
                }
            }

            dataList.add(e);
        }

        return dataList;
    }

    /**
     *@description 保存Excel中上传的数据到List中
     *@param cls
     *@return java.util.List<E>
     *@author Hanz
     *@date 2020/12/1 15:19
     */
    public <E> List<E> getDataListByFile(Class<E> cls) throws Exception {
        List<Object[]> annotationList = CollectionUtil.newArrayList();

        // 获取ExcelField注解的字段
        Field[] fs = cls.getDeclaredFields();
        Arrays.stream(fs).forEach(f -> {
            ExcelField ef = f.getAnnotation(ExcelField.class);
            if (null != ef) annotationList.add(new Object[]{ef, f});
        });

        List<E> dataList = CollectionUtil.newArrayList();
        // 遍历Excel单元格
        for (int i = this.getDataRowNum(); i < this.sheet.getLastRowNum() + 1; i++) {
            E e = cls.newInstance();

            int column = 0;
            Row row = this.getRow(i);
            // 获取实体字段值
            for (Object[] os : annotationList){
                Object val = this.getCellValue(row, column++);

                if (val != null && StrUtil.isNotEmpty(val.toString())){
                    Class<?> valType = ((Field)os[1]).getType();
                    val = this.converValue(valType, val);

                    Field f = (Field)os[1];
                    f.setAccessible(true);
                    ReflectionUtils.setField(f, e, val);
                }
            }

            dataList.add(e);
        }

        return dataList;
    }

    /**
     *@description 指定保存Excel中上传的数据到List中
     *@param cls
     *@return java.util.List<E>
     *@author Hanz
     *@date 2020/12/1 15:19
     */
    public <E> List<E> getDataOneByFile(Class<E> cls) throws Exception {
        List<Object[]> annotationList = CollectionUtil.newArrayList();

        // 获取ExcelField注解的字段
        Field[] fs = cls.getDeclaredFields();
        Arrays.stream(fs).forEach(f -> {
            ExcelField ef = f.getAnnotation(ExcelField.class);
            if (null != ef) annotationList.add(new Object[]{ef, f});
        });

        List<E> dataList = CollectionUtil.newArrayList();
        // 遍历Excel单元格
        for (int i = this.getDataRowNum(); i < this.getDataRowNum() + 1; i++) {
            E e = cls.newInstance();

            int column = 0;
            Row row = this.getRow(i);
            // 获取实体字段值
            for (Object[] os : annotationList){
                Object val = this.getCellValue(row, column++);

                if (val != null && StrUtil.isNotEmpty(val.toString())){
                    Class<?> valType = ((Field)os[1]).getType();
                    val = this.converValue(valType, val);

                    Field f = (Field)os[1];
                    f.setAccessible(true);
                    ReflectionUtils.setField(f, e, val);
                }
            }

            dataList.add(e);
        }

        return dataList;
    }

    /**
     * 获取导入数据列表
     * @param cls 导入对象类型
     * @return 对象实体list
     * @throws IllegalAccessException
     * @throws InstantiationException
     */
    public <E> List<E> getDataListExcludeHeader(Class<E> cls) throws Exception {
        List<Object[]> annotationList = CollectionUtil.newArrayList();

        // 获取ExcelField注解的字段
        Field[] fs = cls.getDeclaredFields();
        Arrays.stream(fs).forEach(f -> {
            ExcelField ef = f.getAnnotation(ExcelField.class);
            if (null != ef) annotationList.add(new Object[]{ef, f});
        });

        List<E> dataList = CollectionUtil.newArrayList();
        // 遍历Excel单元格
        for (int i = this.getDataRowNum(); i < this.getLastDataRowNum() -headerNum; i++) {
            E e = cls.newInstance();

            int column = 0;
            Row row = this.getRow(i);
            // 获取实体字段值
            for (Object[] os : annotationList){
                Object val = this.getCellValue(row, column++);

                if (val != null && StrUtil.isNotEmpty(val.toString())){
                    Class<?> valType = ((Field)os[1]).getType();
                    val = this.converValue(valType, val);

                    Field f = (Field)os[1];
                    f.setAccessible(true);
                    ReflectionUtils.setField(f, e, val);
                }
            }

            dataList.add(e);
        }

        return dataList;
    }

    /**
     * 转换值类型
     * @param valType 值类型
     * @param val 值
     * @return 值
     */
    public Object converValue (Class<?> valType, Object val) throws Exception {
        if (valType == String.class){
            String s = String.valueOf(val.toString());
            if(StringUtils.endsWith(s, ".0")) val = StringUtils.substringBefore(s, ".0");
            else val = String.valueOf(val.toString());
        }else if (valType == Integer.class){
            val = Double.valueOf(val.toString()).intValue();
        }else if (valType == Long.class){
            val = Double.valueOf(val.toString()).longValue();
        }else if (valType == Double.class){
            val = Double.valueOf(val.toString());
        }else if (valType == Float.class){
            val = Float.valueOf(val.toString());
        }else if (valType == Date.class){
            if(val.toString().contains(".")){
                val = val.toString().replaceAll("\\.","-");
            }
            if(val.toString().length() == 10){
                SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
                val = sdf.parse( val.toString() );
            }else if (val.toString().length() == 7){
                SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM");
                val = sdf.parse( val.toString() );
            }else if(val.toString().length() == 4){
                SimpleDateFormat sdf=new SimpleDateFormat("yyyy");
                val = sdf.parse( val.toString() );
            }
        }

        return val;
    }

    /**
     * 获取单元格值
     * @param row 获取的行
     * @param column 获取单元格列号
     * @return 单元格值
     */
    public Object getCellValue(Row row, int column) {
        Object val = "";
        try {
            Cell cell = row.getCell(column);
            if (cell != null) {
                if (cell.getCellTypeEnum() == CellType.NUMERIC) {
                    val = this.getCellValueNumeric(cell);
                } else if (cell.getCellTypeEnum() == CellType.STRING) {
                    val = cell.getStringCellValue();
                }  else if (cell.getCellTypeEnum() == CellType.FORMULA) {
                    val = cell.getStringCellValue();
                } else if (cell.getCellTypeEnum() == CellType.BOOLEAN) {
                    val = cell.getStringCellValue();
                } else if (cell.getCellTypeEnum() ==CellType.ERROR) {
                    val = cell.getStringCellValue();
                }
            }
        } catch (Exception e) {
            return val;
        }
        return val;
    }

    /**
     * 获取单元格数值
     * 当excel 中的数据为数值或日期是需要特殊处理
     * @param cell 单元格
     * @return
     */
    private Object getCellValueNumeric(Cell cell) {
        Object result = "";
        if (cell.getCellTypeEnum() != CellType.NUMERIC) return result;

        if (HSSFDateUtil.isCellDateFormatted(cell)) {
            double d = cell.getNumericCellValue();
            Date date = HSSFDateUtil.getJavaDate(d);
            SimpleDateFormat dformat = new SimpleDateFormat("yyyy-MM-dd");
            result = dformat.format(date);
        } else {
            DecimalFormat df = new DecimalFormat("#.#########");
            df.setGroupingUsed(false);// true时的格式：1,234,567,890
            result = df.format(cell.getNumericCellValue());// 数值类型的数据为double，所以需要转换一下
        }

        return result;
    }

    /**
     * 获取数据行号
     * @return 数据行号
     */
    public int getDataRowNum(){
        return headerNum + 1;
    }

    /**
     * 获取最后一个数据行号
     * @return 最后一个数据行号
     */
    public int getLastDataRowNum(){
        return this.sheet.getLastRowNum() + 1 + headerNum;
    }

    /**
     * 获取行对象
     * @param rownum 行数
     * @return 行对象
     */
    public Row getRow(int rownum){
        return this.sheet.getRow(rownum);
    }

    public static String getCellContent(Sheet sheet, int rowNum, Cell cell) {
        if(cell==null){
            return null;
        }
        int column = cell.getColumnIndex();
        //	判断是否为合并的单元格
        if (isMergedRegion(sheet, rowNum, column)) {
            return getMergedRegionValue(sheet, rowNum, column);
        } else {
            return getCellValue(cell);
        }
    }

    /**
     * 判断指定的单元格是否是合并单元格
     * @param sheet sheet
     * @param row row
     * @param column column
     * @return boolean
     */
    public static boolean isMergedRegion(Sheet sheet , int row , int column){
        int sheetMergeCount = sheet.getNumMergedRegions();

        for(int i = 0 ; i < sheetMergeCount ; i++ ){
            CellRangeAddress ca = sheet.getMergedRegion(i);
            int firstColumn = ca.getFirstColumn();
            int lastColumn = ca.getLastColumn();
            int firstRow = ca.getFirstRow();
            int lastRow = ca.getLastRow();

            if(row >= firstRow && row <= lastRow){
                if(column >= firstColumn && column <= lastColumn){

                    return true ;
                }
            }
        }
        return false ;
    }

    public static Map<String, Integer> getMergedRange(Sheet sheet , int rowNum , Cell cell) {
        Map<String, Integer> mergedRange = new HashMap<>();
        int column = cell.getColumnIndex();
        int sheetMergeCount = sheet.getNumMergedRegions();
        for(int i = 0 ; i < sheetMergeCount ; i++ ){
            CellRangeAddress ca = sheet.getMergedRegion(i);
            int firstColumn = ca.getFirstColumn();
            int lastColumn = ca.getLastColumn();
            int firstRow = ca.getFirstRow();
            int lastRow = ca.getLastRow();

            if(rowNum >= firstRow && rowNum <= lastRow){
                if(column >= firstColumn && column <= lastColumn){
                    mergedRange.put("firstRow", firstRow);
                    mergedRange.put("lastRow", lastRow);
                    return mergedRange ;
                }
            }
        }
        mergedRange.put("firstRow", rowNum);
        mergedRange.put("lastRow", rowNum);
        return mergedRange;
    }

    /**
     * 获取合并单元格的值
     * @param sheet sheet
     * @param row row
     * @param column column
     * @return String
     */
    public static String getMergedRegionValue(Sheet sheet ,int row , int column){
        int sheetMergeCount = sheet.getNumMergedRegions();

        for(int i = 0 ; i < sheetMergeCount ; i++){
            CellRangeAddress ca = sheet.getMergedRegion(i);
            int firstColumn = ca.getFirstColumn();
            int lastColumn = ca.getLastColumn();
            int firstRow = ca.getFirstRow();
            int lastRow = ca.getLastRow();

            if(row >= firstRow && row <= lastRow){
                if(column >= firstColumn && column <= lastColumn){
                    Row fRow = sheet.getRow(firstRow);
                    Cell fCell = fRow.getCell(firstColumn);
                    return getCellValue(fCell) ;
                }
            }
        }
        return null ;
    }

    /**
     * 获取单元格的值
     * @param cell cell
     * @return String
     */
    public static String getCellValue(Cell cell){
        if(cell == null) {
            return "";
        }
        if(cell.getCellTypeEnum() == CellType.STRING){
            return cell.getStringCellValue();
        }else if(cell.getCellTypeEnum() == CellType.BOOLEAN){
            return String.valueOf(cell.getBooleanCellValue());
        }else if(cell.getCellTypeEnum() == CellType.FORMULA){
            String temp = String.valueOf(cell.getNumericCellValue());
            // 判断是否包含小数点，如果不含小数点，则以字符串读取，如果含小数点，截取字符串
            if (temp.contains(".")) {
                temp = temp.trim().substring(0, temp.indexOf("."));
            } else {
                temp = temp.trim();
            }
            return temp;
           // return cell.getCellFormula() ;
        }else if(HSSFDateUtil.isCellDateFormatted(cell)){
            //用于转化为日期格式
            Date d = cell.getDateCellValue();
            DateFormat formater = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String result = "";
            if(d != null) {
                result = formater.format(d);
                return result;
            }else {
                return result;
            }
        }else if(cell.getCellTypeEnum() == CellType.NUMERIC){
            NumberFormat nf = NumberFormat.getInstance();
            //去掉.0
            String str = nf.format(cell.getNumericCellValue());
            //去掉逗号
            str=str.replace(",","");
            return str;
        }
        return "";
    }

    /**
     * 获取单元格的值（数字）
     * @param cell cell
     * @return String String
     */
    public static String getCellValueNum(Cell cell) {

        String val = "";
        if (cell == null) {
            return "";
        }
        if (cell.getCellTypeEnum() == CellType.STRING) {
            return cell.getStringCellValue();
        } else if (cell.getCellTypeEnum() == CellType.NUMERIC) {
            if (DateUtil.isCellDateFormatted(cell)) {
                val = String.valueOf(cell.getDateCellValue());
            } else {
                cell.setCellValue(String.valueOf(CellType.STRING));
                String temp = cell.getStringCellValue();
                // 判断是否包含小数点，如果不含小数点，则以字符串读取，如果含小数点，则转换为Double类型的字符串
                if (temp.contains(".")) {
                    val = String.valueOf(new Double(temp)).trim();
                } else {
                    val = temp.trim();
                }
            }
            return val;
        } else if (cell.getCellTypeEnum() == CellType.FORMULA) {
            String temp = String.valueOf(cell.getNumericCellValue());
            // 判断是否包含小数点，如果不含小数点，则以字符串读取，如果含小数点，截取字符串
            if (temp.contains(".")) {
                val = temp.trim().substring(0, temp.indexOf("."));
            } else {
                val = temp.trim();
            }
            return val;
        }
        return "";
    }

}
