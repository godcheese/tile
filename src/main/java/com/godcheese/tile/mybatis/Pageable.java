package com.godcheese.tile.mybatis;

/**
 * @author godcheese [godcheese@outlook.com]
 * @date 2018-02-07
 */
public class Pageable {

    /**
     * 1
     */
    private int page = 1;

    /**
     * 20
     */
    private int size = 20;

    /**
     * mysql
     * select * from `table` ORDER BY `field` DESC LIMIT offset, rows
     */
    private int offset = 1;
    private int rows = 0;

    /**
     * oracle
     * select * from(select a.*,rownum rn from (select * from t_articles) a where rownum < rownum1) where rn > rownum2
     */
    private int rownum1 = 1;
    private int rownum2 = 0;

    /**
     * sql server
     * select top(@pageCount) ID,Name,Age from [dbo].[Student] where ID not in ( select top(@startIndex) t.ID from [dbo].[Student] as t )
     */
    private int pageCount;
    private int startIndex;

    private Sort sort;
    private Object entity;

    private DatabaseType databaseType;

    /**
     * mysql 分页语句
     */
    private String pageable;

    public Pageable(int page, int size, Sort sort, Object entity) {
        page = page < 0 ? 1 : page;
        size = size < 0 ? 0 : size;

        // limit offset, rows ==> (page-1) * size, size; ==> limit 0, 20
        this.offset = (page - 1) * size;
        this.rows = size;

        // ORDER BY `field` DESC LIMIT 0,20
        this.pageable = "ORDER BY " + sort.getField() + " " + sort.getDirection() + " LIMIT " + this.offset + ", " + this.rows;

        this.page = page;
        this.size = size;
        this.sort = sort;
        this.entity = entity;
    }

    public Pageable(int page, int size, Sort sort, Object entity, DatabaseType databaseType) {
        page = page < 0 ? 1 : page;
        size = size < 0 ? 0 : size;

        if (databaseType == DatabaseType.MYSQL) {
            // limit offset, rows ==> (page-1) * size, size; ==> limit 0, 20
            this.offset = (page - 1) * size;
            this.rows = size;

            // ORDER BY `field` DESC LIMIT 0,20
            this.pageable = "ORDER BY " + sort.getField() + " " + sort.getDirection() + " LIMIT " + this.offset + ", " + this.rows;
        }

        if (databaseType == DatabaseType.ORACLE) {
            // select * from(select a.*,rownum rn from (select * from t_articles) a where rownum < rownum1) where rn > rownum2
            this.rownum1 = page * size + 1;
            this.rownum2 = (page - 1) * size;
        }

        if (databaseType == DatabaseType.SQL_SERVER) {
            // select top(@pageCount) ID,Name,Age from [dbo].[Student] where ID not in ( select top(@startIndex) t.ID from [dbo].[Student] as t )
            this.pageCount = size;
            this.startIndex = (page * size) - size;
        }

        this.page = page;
        this.sort = sort;
        this.entity = entity;
        this.databaseType = databaseType;
    }

    public Pageable(int page, int size, Sort sort) {
        page = page < 0 ? 1 : page;
        size = size < 0 ? 0 : size;

        // limit offset, rows ==> (page-1) * size, size; ==> limit 0, 20
        this.offset = (page - 1) * size;
        this.rows = size;

        // ORDER BY `field` DESC LIMIT offset, rows;
        this.pageable = "ORDER BY " + sort.getField() + " " + sort.getDirection() + " LIMIT " + this.offset + ", " + this.rows;

        this.page = page;
        this.size = size;
        this.sort = sort;
    }

    public Pageable(int page, int size, Sort sort, DatabaseType databaseType) {
        page = page < 0 ? 1 : page;
        size = size < 0 ? 0 : size;

        if (databaseType == DatabaseType.MYSQL) {
            // limit offset, rows ==> (page-1) * size, size; ==> limit 0, 20
            this.offset = (page - 1) * size;
            this.rows = size;

            // ORDER BY `field` DESC LIMIT offset, rows;
            this.pageable = "ORDER BY " + sort.getField() + " " + sort.getDirection() + " LIMIT " + this.offset + ", " + this.rows;
        }

        if (databaseType == DatabaseType.ORACLE) {
            // select * from(select a.*,rownum rn from (select * from t_articles) a where rownum < rownum1) where rn > rownum2
            this.rownum1 = page * size + 1;
            this.rownum2 = (page - 1) * size;
        }

        if (databaseType == DatabaseType.SQL_SERVER) {
            // select top(@pageCount) ID,Name,Age from [dbo].[Student] where ID not in ( select top(@startIndex) t.ID from [dbo].[Student] as t )
            this.pageCount = size;
            this.startIndex = (page * size) - size;
        }

        this.page = page;
        this.size = size;
        this.sort = sort;
        this.databaseType = databaseType;
    }

    public Pageable(int page, int size, OrderBy orderBy) {
        page = page < 0 ? 1 : page;
        size = size < 0 ? 0 : size;

        // limit offset, rows ==> (page-1) * size, size; ==> limit 0, 20
        this.offset = (page - 1) * size;
        this.rows = size;

        // ORDER BY `field1`, `field2` DESC LIMIT offset, rows;
        this.pageable = "ORDER BY " + orderBy.getFields() + " " + orderBy.getDirection() + " LIMIT " + this.offset + ", " + this.rows;

        this.page = page;
        this.size = size;
    }

    public Pageable(int page, int size, OrderBy orderBy, DatabaseType databaseType) {
        page = page < 0 ? 1 : page;
        size = size < 0 ? 0 : size;

        if (databaseType == DatabaseType.MYSQL) {
            // limit offset, rows ==> (page-1) * size, size; ==> limit 0, 20
            this.offset = (page - 1) * size;
            this.rows = size;

            // ORDER BY `field1`, `field2` DESC LIMIT offset, rows;
            this.pageable = "ORDER BY " + orderBy.getFields() + " " + orderBy.getDirection() + " LIMIT " + this.offset + ", " + this.rows;
        }

        if (databaseType == DatabaseType.ORACLE) {
            // select * from(select a.*,rownum rn from (select * from t_articles) a where rownum < rownum1) where rn > rownum2
            this.rownum1 = page * size + 1;
            this.rownum2 = (page - 1) * size;
        }

        if (databaseType == DatabaseType.SQL_SERVER) {
            // select top(@pageCount) ID,Name,Age from [dbo].[Student] where ID not in ( select top(@startIndex) t.ID from [dbo].[Student] as t )
            this.pageCount = size;
            this.startIndex = (page * size) - size;
        }

        this.page = page;
        this.size = size;
        this.databaseType = databaseType;
    }

    public Pageable(int page, int size, Object entity) {
        page = page < 0 ? 1 : page;
        size = size < 0 ? 0 : size;

        // limit offset, rows ==> (page-1) * size, size; ==> limit 0, 20
        this.offset = (page - 1) * size;
        this.rows = size;

        // LIMIT offset, rows;
        this.pageable = "LIMIT " + this.offset + ", " + this.rows;

        this.page = page;
        this.size = size;
        this.entity = entity;
    }

    public Pageable(int page, int size, Object entity, DatabaseType databaseType) {
        page = page < 0 ? 1 : page;
        size = size < 0 ? 0 : size;

        if (databaseType == DatabaseType.MYSQL) {
            // limit offset, rows ==> (page-1) * size, size; ==> limit 0, 20
            this.offset = (page - 1) * size;
            this.rows = size;

            // LIMIT offset, rows;
            this.pageable = "LIMIT " + this.offset + ", " + this.rows;
        }

        if (databaseType == DatabaseType.ORACLE) {
            // select * from(select a.*,rownum rn from (select * from t_articles) a where rownum < rownum1) where rn > rownum2
            this.rownum1 = page * size + 1;
            this.rownum2 = (page - 1) * size;
        }

        if (databaseType == DatabaseType.SQL_SERVER) {
            // select top(@pageCount) ID,Name,Age from [dbo].[Student] where ID not in ( select top(@startIndex) t.ID from [dbo].[Student] as t )
            this.pageCount = size;
            this.startIndex = (page * size) - size;
        }

        this.page = page;
        this.size = size;
        this.entity = entity;
        this.databaseType = databaseType;
    }

    public Pageable(int page, int size) {
        page = page < 0 ? 1 : page;
        size = size < 0 ? 0 : size;

        // limit offset, rows ==> (page-1) * size, size; ==> limit 0, 20
        this.offset = (page - 1) * size;
        this.rows = size;

        // LIMIT offset, rows;
        this.pageable = "LIMIT " + this.offset + ", " + this.rows;

        this.page = page;
        this.size = size;
    }

    public Pageable(int page, int size, DatabaseType databaseType) {
        page = page < 0 ? 1 : page;
        size = size < 0 ? 0 : size;

        if (databaseType == DatabaseType.MYSQL) {
            // limit offset, rows ==> (page-1) * size, size; ==> limit 0, 20
            this.offset = (page - 1) * size;
            this.rows = size;

            // LIMIT offset, rows;
            this.pageable = "LIMIT " + this.offset + ", " + this.rows;
        }

        if (databaseType == DatabaseType.ORACLE) {
            // select * from(select a.*,rownum rn from (select * from t_articles) a where rownum < rownum1) where rn > rownum2
            this.rownum1 = page * size + 1;
            this.rownum2 = (page - 1) * size;
        }

        if (databaseType == DatabaseType.SQL_SERVER) {
            // select top(@pageCount) ID,Name,Age from [dbo].[Student] where ID not in ( select top(@startIndex) t.ID from [dbo].[Student] as t )
            this.pageCount = size;
            this.startIndex = (page * size) - size;
        }

        this.page = page;
        this.size = size;
        this.databaseType = databaseType;
    }

    public int getPage() {
        return page;
    }

    public int getSize() {
        return size;
    }

    public int getOffset() {
        return offset;
    }

    public int getRows() {
        return rows;
    }

    public int getRownum1() {
        return rownum1;
    }

    public int getRownum2() {
        return rownum2;
    }

    public int getPageCount() {
        return pageCount;
    }

    public int getStartIndex() {
        return startIndex;
    }

    public Sort getSort() {
        return sort;
    }

    public Object getEntity() {
        return entity;
    }

    public DatabaseType getDatabaseType() {
        return databaseType;
    }

    public String getPageable() {
        return pageable;
    }

    @Override
    public String toString() {
        return getPageable();
    }
}
