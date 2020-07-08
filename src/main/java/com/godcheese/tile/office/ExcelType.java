package com.godcheese.tile.office;

/**
 * @author godcheese [godcheese@outlook.com]
 * @date 2018-02-07
 */
public enum ExcelType {
    /**
     * Excel 扩展名 .xls
     */
    XLS(".xls"),

    /**
     * Excel 扩展名 .xlsx
     */
    XLSX(".xlsx");

    private String suffix;

    ExcelType(String suffix) {
        this.suffix = suffix;
    }

    public String getSuffix() {
        return suffix;
    }
}