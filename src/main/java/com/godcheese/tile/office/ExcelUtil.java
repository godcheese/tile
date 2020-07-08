package com.godcheese.tile.office;

import com.godcheese.tile.util.FileUtil;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.DecimalFormat;

/**
 * @author godcheese [godcheese@outlook.com]
 * @date 2018-02-07
 */
public class ExcelUtil {

    public static Workbook getWorkbook(String filename) throws IOException {
        Workbook workbook;
        try (FileInputStream fileInputStream = new FileInputStream(filename)) {
            workbook = getWorkbook(filename, fileInputStream);
        }
        return workbook;
    }

    public static Workbook getWorkbook(String originalFilename, InputStream inputStream) throws IOException {
        Workbook workbook = null;
        ExcelType excelType = ExcelUtil.getExcelType(originalFilename);
        if (excelType != null) {
            switch (excelType) {
                case XLS:
                    workbook = new HSSFWorkbook(inputStream);
                    break;
                case XLSX:
                    workbook = new XSSFWorkbook(inputStream);
                    break;
                default:
                    workbook = null;
                    break;
            }
        }
        return workbook;
    }

    public static ExcelType getExcelType(String originalFilename) {
        int dotIndex;
        if ((dotIndex = originalFilename.toLowerCase().lastIndexOf('.')) > 0) {
            String suffix = originalFilename.substring(dotIndex);
            for (ExcelType excelType : ExcelType.values()) {
                if (excelType.getSuffix().equals(suffix)) {
                    return excelType;
                }
            }
        }
        return null;
    }

    public static Cell getCell(Row row, int cellNum) {
        return row.getCell(cellNum);
    }

    public static Row getRow(Sheet sheet, int rowNum) {
        return sheet.getRow(rowNum);
    }

    public static String getCellValue(Row row, int cellNum) {
        String cellValue = null;
        Cell cell = ExcelUtil.getCell(row, cellNum);
        if (cell != null) {
            switch (cell.getCellType()) {
                case STRING:
                    // 字符串
                    cellValue = cell.getStringCellValue();
                    break;
                case NUMERIC:
                    // 数值，避免读取时科学计数
                    DecimalFormat decimalFormat = new DecimalFormat("0");
                    cellValue = decimalFormat.format(cell.getNumericCellValue());
//                    if (cellValue.contains(",")) {
//                        cellValue = cellValue.replace(",", "");
//                    }
                    break;
                case FORMULA:
                    // 公式
                    cellValue = cell.getCellFormula() + "";
                    break;
                case BOOLEAN:
                    // 布尔值
                    cellValue = cell.getBooleanCellValue() + "";
                    break;
                case BLANK:
                    // 空值
                    cellValue = "";
                    break;
                case ERROR:
                    // 错误
                    cellValue = "非法字符";
                    break;
                case _NONE:
                default:
                    cellValue = "未知类型";
                    break;
            }
        }
        return cellValue;
    }

    /**
     * 只兼容 2003 Excel
     */
    public static void read2003AndDownloadExportExcel(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Workbook workbook, String filename) throws IOException {
        // 设置头
        httpServletResponse.setHeader("Cache-Control", "no-cache");
        //设置日期头
        httpServletResponse.setDateHeader("Expires", 0);
        // 设置文件流
        httpServletResponse.setContentType("application/octet-stream;charset=UTF-8");
        filename = FileUtil.avoidChineseMessyCode(httpServletRequest, filename);
        // 设置文件名
        httpServletResponse.setHeader("Content-Disposition", String.format("attachment; filename=\"%s\"", filename));
        workbook.write(httpServletResponse.getOutputStream());
//        httpServletResponse.getOutputStream().flush();
        httpServletResponse.getOutputStream().close();
    }

    public static Cell createCell(Row row, int cellNum) {
        Cell cell = row.getCell(cellNum);
        if (cell == null) {
            cell = row.createCell(cellNum);
        }
        return cell;
    }

    public static Row createRow(Sheet sheet, int rowNum) {
        Row row = sheet.getRow(rowNum);
        if (row == null) {
            row = sheet.createRow(rowNum);
        }
        return row;
    }

    public static Sheet createSheet(Workbook workbook, String sheetName) {
        Sheet sheet = workbook.getSheet(sheetName);
        if (sheet == null) {
            sheet = workbook.createSheet(sheetName);
        }
        return sheet;
    }

    public static Sheet createSheet(Workbook workbook) {
        return workbook.createSheet();
    }

    /**
     * XLS
     *
     * @return
     */
    public static Workbook createWorkbook() {
        return createHSSFWorkbook();
    }

    /**
     * XLS
     *
     * @return
     */
    public static HSSFWorkbook createHSSFWorkbook() {
        return new HSSFWorkbook();
    }

    /**
     * XLSX
     *
     * @return
     */
    public static XSSFWorkbook createXSSFWorkbook() {
        return new XSSFWorkbook();
    }
}
