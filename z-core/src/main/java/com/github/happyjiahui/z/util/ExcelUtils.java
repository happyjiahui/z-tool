package com.github.happyjiahui.z.util;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.*;

import com.github.happyjiahui.z.exception.UtilException;

/**
 * excel 工具类
 *
 * @author zhaojinbing
 */
public class ExcelUtils {

    private ExcelUtils() {

    }

    /**
     * 根据模板填充excel
     * 
     * excel模板分为两部分： 1.全局替换的字符串,在excel模板中用 $key$ 表示; 2.表格数据,需先在excel定义一模板行,模板的信息用json表示;
     *
     *
     * @param templatePath
     *            模板路径
     * @param globalData
     *            全局数据
     * @param contentData
     *            内容数据
     * @return 填充后的excel字节数组
     */
    public static byte[] fillExcelByTemplate(String templatePath, Map<String, String> globalData,
        Map<String, List<Map<String, Object>>> contentData) {
        byte[] bytes;
        try (InputStream inputStream = new FileInputStream(templatePath);) {
            bytes = fillExcelByTemplate(inputStream, globalData, contentData);
        } catch (Exception e) {
            throw new UtilException("没找到模板文件");
        }

        return bytes;
    }

    /**
     * 根据模板填充excel
     *
     * @param templateStream
     *            模板输入流
     * @param globalData
     *            全局数据
     * @param contentData
     *            内容数据
     * @return 填充后的excel字节数组
     */
    public static byte[] fillExcelByTemplate(InputStream templateStream, Map<String, String> globalData,
        Map<String, List<Map<String, Object>>> contentData) {

        byte[] bytes;

        try (BufferedInputStream inputStream = new BufferedInputStream(templateStream);
            XSSFWorkbook workbook = new XSSFWorkbook(inputStream);
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream()) {

            XSSFSheet sheet = workbook.getSheetAt(0);
            for (int rowNum = 0; rowNum < sheet.getPhysicalNumberOfRows(); rowNum++) {
                XSSFRow row = sheet.getRow(rowNum);
                if (row == null) {
                    continue;
                }
                // 填充全局数据
                fillGlobalData(globalData, row);
                // 填充模板数据
                TemplateTitleInfo templateTitleInfo = getTemplateTitleInfo(row, 0);
                if (templateTitleInfo == null) {
                    continue;
                }
                String dataFlag = templateTitleInfo.getDataFlag();
                if (StringUtils.isEmpty(dataFlag)) {
                    throw new UtilException("无法找到数据标识");
                }
                fillContent(sheet, row, contentData.get(dataFlag));
            }
            workbook.write(outputStream);
            bytes = outputStream.toByteArray();
        } catch (Exception e) {
            throw new UtilException("填充excel失败", e);
        }

        return bytes;
    }

    /**
     * 获取内容模板信息
     *
     * @param templateRow
     *            信息行
     * @param cellNum
     *            单元格
     * @return 模板信息
     */
    private static TemplateTitleInfo getTemplateTitleInfo(XSSFRow templateRow, int cellNum) {
        XSSFCell cell = templateRow.getCell(cellNum);
        if (cell == null) {
            return null;
        }
        XSSFComment cellComment = cell.getCellComment();
        if (cellComment == null || cellComment.getString() == null) {
            return null;
        }
        String comment = cellComment.getString().getString();
        if (StringUtils.isEmpty(comment)) {
            return null;
        }
        return JsonUtils.parseObj(comment, TemplateTitleInfo.class);
    }

    /**
     * 填写全局数据
     *
     * @param globalData
     *            全局数据
     * @param row
     *            行
     */
    private static void fillGlobalData(Map<String, String> globalData, XSSFRow row) {
        if (globalData == null || row == null) {
            return;
        }
        for (int cellNum = 0; cellNum < row.getPhysicalNumberOfCells(); cellNum++) {
            XSSFCell cell = row.getCell(cellNum);
            if (cell == null || cell.getCellType() != CellType.STRING) {
                continue;
            }
            String value = cell.getRichStringCellValue().getString();
            if (StringUtils.isEmpty(value)) {
                continue;
            }
            if (!value.contains("$")) {
                continue;
            }
            for (Map.Entry<String, String> entry : globalData.entrySet()) {
                value = value.replace("$" + entry.getKey() + "$", String.valueOf(entry.getValue()));
            }
            cell.setCellValue(value);
        }
    }

    /**
     * 填写内容
     *
     * @param sheet
     *            sheet
     * @param templateRow
     *            模板行
     * @param list
     *            数据列表
     */
    private static void fillContent(XSSFSheet sheet, XSSFRow templateRow, List<Map<String, Object>> list) {
        if (list == null || list.isEmpty()) {
            return;
        }
        int templateRowNum = templateRow.getRowNum();
        sheet.shiftRows(templateRowNum, sheet.getLastRowNum(), list.size(), true, true);
        Map<String, List<Integer>> valueChangePositionMap = new HashMap<>(16);
        // 填充数据
        fillData(sheet, templateRow, list, valueChangePositionMap, templateRowNum);
        // 合并行
        mergeRegion(sheet, templateRow, valueChangePositionMap, list.size(), templateRowNum);
        // 删除模板行
        sheet.shiftRows(templateRowNum + list.size() + 1, sheet.getLastRowNum(), -1);
    }

    /**
     * 填充数据
     *
     * @param sheet
     *            sheet
     * @param templateRow
     *            模板行
     * @param list
     *            数据列表
     * @param valueChangePositionMap
     *            值变化map
     * @param beginRowNum
     *            起始行
     */
    private static void fillData(XSSFSheet sheet, XSSFRow templateRow, List<Map<String, Object>> list,
        Map<String, List<Integer>> valueChangePositionMap, int beginRowNum) {
        HashMap<Object, Object> lastCellValueMap = new HashMap<>(16);
        for (int i = 0; i < list.size(); i++) {

            Map<String, Object> item = list.get(i);
            XSSFRow newRow = sheet.createRow(beginRowNum + i);
            // 根据模板行填充数据
            fillRow(templateRow, newRow, valueChangePositionMap, lastCellValueMap, item, i);
        }
    }

    /**
     * 根据模板行填充数据
     *
     * @param templateRow
     *            模板行
     * @param newRow
     *            新建行
     * @param valueChangePositionMap
     *            值改变位置map
     * @param lastCellValueMap
     *            最新值map
     * @param item
     *            数据
     * @param position
     *            数据位置
     */
    private static void fillRow(XSSFRow templateRow, XSSFRow newRow, Map<String, List<Integer>> valueChangePositionMap,
        HashMap<Object, Object> lastCellValueMap, Map<String, Object> item, int position) {

        for (int cellNum = 0; cellNum < templateRow.getPhysicalNumberOfCells(); cellNum++) {
            TemplateTitleInfo templateTitleInfo = getTemplateTitleInfo(templateRow, cellNum);
            if (templateTitleInfo == null) {
                continue;
            }
            String fieldName = templateTitleInfo.getFieldName();
            if (StringUtils.isEmpty(fieldName)) {
                continue;
            }
            String value = item.getOrDefault(fieldName, "").toString();
            // 记录字段值变化位置
            recordValueChangePosition(valueChangePositionMap, lastCellValueMap, position, fieldName, value);
            // 填充单元格
            fillCell(templateRow, newRow, cellNum, value);
        }
    }

    /**
     * 记录字段值变化位置
     *
     * @param valueChangePositionMap
     *            值改变位置map
     * @param lastCellValueMap
     *            上次值map
     * @param position
     *            值位置
     * @param fieldName
     *            字段名称
     * @param value
     *            字段值
     */
    private static void recordValueChangePosition(Map<String, List<Integer>> valueChangePositionMap,
        HashMap<Object, Object> lastCellValueMap, int position, String fieldName, String value) {
        if (!value.equals(lastCellValueMap.get(fieldName))) {
            List<Integer> equalValuePositionList = new ArrayList<>();
            if (valueChangePositionMap.containsKey(fieldName)) {
                equalValuePositionList = valueChangePositionMap.get(fieldName);
            }
            equalValuePositionList.add(position);
            valueChangePositionMap.put(fieldName, equalValuePositionList);
        }
        lastCellValueMap.put(fieldName, value);
    }

    /**
     * 填写单元格
     *
     * @param templateRow
     *            模板行
     * @param newRow
     *            新建行
     * @param cellNum
     *            列号
     * @param value
     *            值
     */
    private static void fillCell(XSSFRow templateRow, XSSFRow newRow, int cellNum, String value) {
        XSSFCell templateCell = templateRow.getCell(cellNum);

        XSSFCell newCell = newRow.createCell(cellNum);
        newCell.setCellType(templateCell.getCellType());
        newCell.setCellStyle(templateCell.getCellStyle());

        if (templateCell.getCellType() == CellType.NUMERIC) {
            if (StringUtils.isEmpty(value)) {
                value = "0";
            }
            newCell.setCellValue(Double.parseDouble(value));
        } else {
            newCell.setCellValue(value);
        }
    }

    /**
     * 合并单元格
     *
     * @param sheet
     *            sheet
     * @param templateRow
     *            模板行
     * @param valueChangePositionMap
     *            值变化map
     * @param dataSize
     *            数据size
     * @param originalBeginRow
     *            原始开始行号
     */
    private static void mergeRegion(XSSFSheet sheet, XSSFRow templateRow,
        Map<String, List<Integer>> valueChangePositionMap, int dataSize, int originalBeginRow) {
        if (templateRow == null) {
            return;
        }
        for (int cellNum = 0; cellNum < templateRow.getPhysicalNumberOfCells(); cellNum++) {
            TemplateTitleInfo templateTitleInfo = getTemplateTitleInfo(templateRow, cellNum);
            String fieldName = templateTitleInfo.getFieldName();
            String mergedFieldName = templateTitleInfo.getMergedFieldName();
            if (StringUtils.isEmpty(fieldName) || StringUtils.isEmpty(mergedFieldName)) {
                continue;
            }
            List<Integer> valueChangePositionList = valueChangePositionMap.get(mergedFieldName);
            if (valueChangePositionList == null || valueChangePositionList.isEmpty()) {
                continue;
            }

            for (int position = 0; position < valueChangePositionList.size(); position++) {
                int beginRow = originalBeginRow + valueChangePositionList.get(position);
                int endRow = getMergedRegionEndRow(valueChangePositionList, originalBeginRow, dataSize, position);

                if (endRow - beginRow >= 1) {
                    if (templateTitleInfo.isSum()) {
                        // 合计待合并的单元格
                        setSumValueToMergedCell(sheet, beginRow, endRow, cellNum);
                    }
                    sheet.addMergedRegion(new CellRangeAddress(beginRow, endRow, cellNum, cellNum));
                }
                if (templateTitleInfo.isAutoNumber()) {
                    // 重新编写合并后单元格的序号
                    sheet.getRow(beginRow).getCell(cellNum).setCellValue(position + 1);
                }
            }
        }
    }

    /**
     * 获取合并区域的最后一行
     *
     * @param valueChangePositionList
     *            值改变list
     * @param templateRowNum
     *            模板行号
     * @param dataSize
     *            数据size
     * @param position
     *            数据位置
     * @return 合并区域的最后一行号
     */
    private static int getMergedRegionEndRow(List<Integer> valueChangePositionList, int templateRowNum, int dataSize,
        int position) {
        int endRow = 0;
        if (position == valueChangePositionList.size() - 1) {
            endRow = templateRowNum + dataSize - 1;
        } else {
            endRow = templateRowNum + valueChangePositionList.get(position + 1) - 1;
        }
        return endRow;
    }

    private static void setSumValueToMergedCell(XSSFSheet sheet, int beginRow, int endRow, int cellNum) {
        double total = 0;
        for (int rowNum = beginRow; rowNum <= endRow; rowNum++) {
            XSSFRow row = sheet.getRow(rowNum);
            total += row.getCell(cellNum).getNumericCellValue();
            row.getCell(cellNum).setCellValue(0);
        }
        sheet.getRow(beginRow).getCell(cellNum).setCellValue(total);
    }

    public static class TemplateTitleInfo {
        /**
         * 数据标识
         */
        private String dataFlag;
        /**
         * 字段名称
         */
        private String fieldName;
        /**
         * 合并字段名称
         */
        private String mergedFieldName;
        /**
         * 后并后是否需要合计
         */
        private boolean sum;
        /**
         * 合并后是否需要重新编号
         */
        private boolean autoNumber;

        public String getDataFlag() {
            return dataFlag;
        }

        public void setDataFlag(String dataFlag) {
            this.dataFlag = dataFlag;
        }

        public String getFieldName() {
            return fieldName;
        }

        public void setFieldName(String fieldName) {
            this.fieldName = fieldName;
        }

        public String getMergedFieldName() {
            return mergedFieldName;
        }

        public void setMergedFieldName(String mergedFieldName) {
            this.mergedFieldName = mergedFieldName;
        }

        public boolean isSum() {
            return sum;
        }

        public void setSum(boolean sum) {
            this.sum = sum;
        }

        public boolean isAutoNumber() {
            return autoNumber;
        }

        public void setAutoNumber(boolean autoNumber) {
            this.autoNumber = autoNumber;
        }
    }

}
