package com.godcheese.tile.database;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Map;

/**
 * @author godcheese [godcheese@outlook.com]
 * @date 2018-04-21
 */
public class SqlGenerateProperties {

    /**
     * 数据库类型，用语解析sql语句
     */
    private DatabaseType databaseType = DatabaseType.MYSQL;
    /**
     * sql 文件目录
     */
    private String sqlDirectory;
    /**
     * sql 文件后缀，一般为 .sql
     */
    private String sqlFileSuffix = ".sql";
    /**
     * 实体文件生成后保存目录
     */
    private String entityDirectory;
    /**
     * 实体文件前缀，一般为 Entity.java
     */
    private String entityFileSuffix = "Entity.java";
    /**
     * 实体所在的类包
     */
    private String entityPackage;
    /**
     * 实体代码的缩进空格，默认为四个空格
     */
    private String indentWhiteSpace = "    ";
    /**
     * 附加 sql 字段 comment 为 javadoc
     */
    private boolean appendComment = true;
    /**
     * 字段转成 java 实体属性 type 的规则
     */
    private Hashtable<String, Class<?>> fieldConvertRuleTable = new Hashtable<>();
    /**
     * sql 字段类型转成 jdbcType 映射表
     */
    private Hashtable<String, String> jdbcTypeTable = new Hashtable<>();
    /**
     * MyBatis Mapper XML 文件后缀，一般为 Mapper.xml
     */
    private String myBatisMapperXmlFileSuffix = "Mapper.xml";


    /**
     * 生成 MyBatis 相关文件，
     */
    /**
     * MyBatis Mapper XML 文件生成后保存目录
     */
    private String myBatisMapperXmlDirectory;
    /**
     * mapper package
     */
    private String myBatisMapperPackage;
    /**
     * MyBatis Mapper 文件后缀，一般为 Mapper.java
     */
    private String myBatisMapperFileSuffix = "Mapper.java";
    /**
     * MyBatis Mapper 文件生成后保存目录
     */
    private String myBatisMapperDirectory;

    public SqlGenerateProperties() {
        Map<String, Class<?>> fieldConvertRule = new Hashtable<>(5);
        Map<String, String> jdbcType = new HashMap<>();
        switch (this.databaseType) {
            case MYSQL:

                // 数据库字段类型 => Java 属性类型
                fieldConvertRule.put("BIGINT", Long.class);
                fieldConvertRule.put("INT", Integer.class);
                fieldConvertRule.put("TINYINT", Integer.class);
                fieldConvertRule.put("VARCHAR", String.class);
                fieldConvertRule.put("DATETIME", Date.class);
                fieldConvertRule.put("DOUBLE", Double.class);
                fieldConvertRule.put("TEXT", String.class);
                fieldConvertRule.put("DECIMAL", BigDecimal.class);

                // 数据库字段类型 => jdbcType
                jdbcType.put("BIGINT", "BIGINT");
                jdbcType.put("INT", "INT");
                jdbcType.put("TINYINT", "TINYINT");
                jdbcType.put("VARCHAR", "VARCHAR");
                jdbcType.put("DATE", "DATE");
                jdbcType.put("DATETIME", "TIMESTAMP");
                jdbcType.put("DOUBLE", "DOUBLE");
                jdbcType.put("TEXT", "LONGVARCHAR");
                jdbcType.put("DECIMAL", "DECIMAL");

                break;
            case ORACLE:
                fieldConvertRule.put("NCLOB", String.class);
                break;
            default:
                break;
        }
        this.fieldConvertRuleTable.putAll(fieldConvertRule);
        this.jdbcTypeTable.putAll(jdbcType);
    }

    public DatabaseType getDatabaseType() {
        return databaseType;
    }

    public void setDatabaseType(DatabaseType databaseType) {
        this.databaseType = databaseType;
    }

    public String getSqlDirectory() {
        return sqlDirectory;
    }

    public void setSqlDirectory(String sqlDirectory) {
        this.sqlDirectory = sqlDirectory;
    }

    public String getSqlFileSuffix() {
        return sqlFileSuffix;
    }

    public void setSqlFileSuffix(String sqlFileSuffix) {
        this.sqlFileSuffix = sqlFileSuffix;
    }

    public String getEntityDirectory() {
        return entityDirectory;
    }

    public void setEntityDirectory(String entityDirectory) {
        this.entityDirectory = entityDirectory;
    }

    public String getEntityFileSuffix() {
        return entityFileSuffix;
    }

    public void setEntityFileSuffix(String entityFileSuffix) {
        this.entityFileSuffix = entityFileSuffix;
    }

    public String getEntityPackage() {
        return entityPackage;
    }

    public void setEntityPackage(String entityPackage) {
        this.entityPackage = entityPackage;
    }

    public String getIndentWhiteSpace() {
        return indentWhiteSpace;
    }

    public void setIndentWhiteSpace(String indentWhiteSpace) {
        this.indentWhiteSpace = indentWhiteSpace;
    }

    public boolean getAppendComment() {
        return appendComment;
    }

    public void setAppendComment(boolean appendComment) {
        this.appendComment = appendComment;
    }

    public Hashtable<String, Class<?>> getFieldConvertRuleTable() {
        return fieldConvertRuleTable;
    }

    public void setFieldConvertRuleTable(Hashtable<String, Class<?>> fieldConvertRuleTable) {
        this.fieldConvertRuleTable = fieldConvertRuleTable;
    }

    public Hashtable<String, String> getJdbcTypeTable() {
        return jdbcTypeTable;
    }

    public void setJdbcTypeTable(Hashtable<String, String> jdbcTypeTable) {
        this.jdbcTypeTable = jdbcTypeTable;
    }

    public String getMyBatisMapperXmlFileSuffix() {
        return myBatisMapperXmlFileSuffix;
    }

    public void setMyBatisMapperXmlFileSuffix(String myBatisMapperXmlFileSuffix) {
        this.myBatisMapperXmlFileSuffix = myBatisMapperXmlFileSuffix;
    }

    public String getMyBatisMapperXmlDirectory() {
        return myBatisMapperXmlDirectory;
    }

    public void setMyBatisMapperXmlDirectory(String myBatisMapperXmlDirectory) {
        this.myBatisMapperXmlDirectory = myBatisMapperXmlDirectory;
    }

    public String getMyBatisMapperPackage() {
        return myBatisMapperPackage;
    }

    public void setMyBatisMapperPackage(String myBatisMapperPackage) {
        this.myBatisMapperPackage = myBatisMapperPackage;
    }

    public String getMyBatisMapperFileSuffix() {
        return myBatisMapperFileSuffix;
    }

    public void setMyBatisMapperFileSuffix(String myBatisMapperFileSuffix) {
        this.myBatisMapperFileSuffix = myBatisMapperFileSuffix;
    }

    public String getMyBatisMapperDirectory() {
        return myBatisMapperDirectory;
    }

    public void setMyBatisMapperDirectory(String myBatisMapperDirectory) {
        this.myBatisMapperDirectory = myBatisMapperDirectory;
    }

    public enum DatabaseType {

        /**
         * MySQL
         */
        MYSQL("mysql"),

        /**
         * Oracle
         */
        ORACLE("oracle"),
        ;
        private String value;

        DatabaseType(String value) {
            this.value = value;
        }

        public String getValue() {
            return value;
        }

        public void setValue(String value) {
            this.value = value;
        }
    }

    public enum MySqlFieldType {

        /**
         * bigint
         */
        BIGINT("bigint"),

        /**
         * varchar
         */
        VARCHAR("varchar"),

        /**
         * double
         */
        DOUBLE("double"),

        /**
         * int
         */
        INT("int"),

        /**
         * text
         */
        TEXT("text"),

        /**
         * datetime
         */
        DATETIME("datetime"),

        /**
         * timestamp
         */
        TIMESTAMP("timestamp");

        private String value;

        MySqlFieldType(String value) {
            this.value = value;
        }

        public String getValue() {
            return value;
        }

        public void setValue(String value) {
            this.value = value;
        }
    }
}
