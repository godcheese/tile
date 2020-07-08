package com.godcheese.tile.database;

import com.godcheese.tile.util.FileUtil;
import com.godcheese.tile.util.StringUtil;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author godcheese [godcheese@outlook.com]
 * @date 2018-04-18
 */
public class DatabaseUtil {

    /**
     * 未测试
     *
     * @param host
     * @param port
     * @param name
     * @param charset
     * @param username
     * @param password
     * @param filename
     * @param targetPath
     * @return
     */
    private static boolean mysqlExportDatabase(String host, String port, String name, String charset, String username, String password, String filename, String targetPath) {
        File saveFile = new File(targetPath);
        if (!saveFile.exists()) {
            saveFile.mkdirs();
        }
        if (!targetPath.endsWith(File.separator)) {
            targetPath = targetPath + File.separator;
        }

        StringBuilder stringBuilder = new StringBuilder();
        String mysqldumpFilename = FileUtil.getCurrentRootPath() + File.separator + "data" + File.separator + "mysqldump.exe";

        stringBuilder.append(mysqldumpFilename);
        stringBuilder.append(" --opt").append(" -h").append(host).append(" -P").append(port);
        stringBuilder.append(" --user=").append(username).append(" --password=").append(password).append(" --lock-all-tables=true");
        stringBuilder.append(" --result-file=").append(targetPath).append(filename).append(" --default-character-set=").append(charset).append(" ").append(name);
        try {
            Process process = Runtime.getRuntime().exec(stringBuilder.toString());
            // 0 表示线程正常终止。
            if (process.waitFor() == 0) {
                return true;
            }
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
        return false;
    }


    /**
     * 分析 sql 文件的文本内容
     *
     * @param databaseType
     * @param content
     * @return
     */
    private static DatabaseTable<? extends DatabaseField> analysisSqlContent(SqlGenerateProperties.DatabaseType databaseType, String content) {

        switch (databaseType) {
            case MYSQL:
                MySqlTable mySqlTable = new MySqlTable();

                /**
                 * 取出 table name、table field
                 */
                String regex = "create *table *`?([a-z0-9_-]*`?) *\\(([\\D\\W\\S]*)\\)";
                Pattern pattern = Pattern.compile(regex, Pattern.CASE_INSENSITIVE | Pattern.MULTILINE);
                Matcher matcher = pattern.matcher(content);

                if (matcher.find()) {
                    String tableName = matcher.group(1).replaceAll("`", "");
                    mySqlTable.setName(tableName);
                    String field = matcher.group(2);
//                    System.out.println(field);

                    if (field.indexOf(",") > 0) {
                        String[] fields = field.split("\\,\\s");

                        if (fields.length > 0) {
                            StringBuilder regexAppend = new StringBuilder();
                            for (SqlGenerateProperties.MySqlFieldType ft : SqlGenerateProperties.MySqlFieldType.values()) {
                                regexAppend.append("(").append(ft.getValue()).append("\\([0-9],?*\\))|");
                            }
                            regexAppend.delete(regexAppend.length() - 2, regexAppend.length());

                            // 单行的字段信息
                            String fieldRegex = "`?([a-z0-9_-]*)`? *([" + regexAppend + "]{3,})";


                            // 匹配 primary key 方法1
                            String fieldPrimaryKeyRegex = "primary +key";
                            Pattern fieldPrimaryKeyPattern = Pattern.compile(fieldPrimaryKeyRegex, Pattern.CASE_INSENSITIVE);
                            // 匹配主键（primary key）方法2
                            String primaryKeyRegex = "primary +key +`?[a-z0-9_-]*`? *\\({1}`?([a-z0-9_-]*)`?\\){1}";
                            Pattern primaryKeyPattern = Pattern.compile(primaryKeyRegex, Pattern.CASE_INSENSITIVE);

                            ArrayList<MySqlField> mySqlFieldList = new ArrayList<>();
                            for (String f : fields) {

//                                System.out.println(f);

                                // 匹配主键（primary key）方法2
                                Matcher primaryKeyMatcher = primaryKeyPattern.matcher(f);
                                if (primaryKeyMatcher.find()) {
                                    mySqlTable.setPrimaryKey(primaryKeyMatcher.group(1));
                                }

                                Pattern fieldPattern = Pattern.compile(fieldRegex, Pattern.CASE_INSENSITIVE);
                                Matcher fieldMatcher = fieldPattern.matcher(f);

//                                System.out.println(fieldMatcher);

                                if (fieldMatcher.find()) {

                                    // 字段名
                                    String fieldName = fieldMatcher.group(1);
                                    fieldName = fieldName.replaceAll("`", "");
                                    if (!"".equals(fieldName)) {

                                        // 判断此行是否被表主键（primary key）标记
                                        Matcher fieldPrimaryKeyMatcher = fieldPrimaryKeyPattern.matcher(f);
                                        if (fieldPrimaryKeyMatcher.find()) {
                                            mySqlTable.setPrimaryKey(fieldName);
                                        }

                                        MySqlField mySqlField = new MySqlField();

                                        // 字段名
                                        mySqlField.setName(fieldName);

                                        // 字段类型（type）
                                        String fieldType = fieldMatcher.group(2);

//                                        System.out.println(fieldType);

                                        // 移除字段类型后面的长度，如：BIGINT(20)，移除 (20)
                                        fieldType = fieldType.replaceAll(" *\\( *([0-9]+) *\\)* *", "");
//                                        System.out.println(fieldType);

                                        try {

                                            // 字段类型
                                            mySqlField.setType(SqlGenerateProperties.MySqlFieldType.valueOf(fieldType.toUpperCase()));
                                        } catch (IllegalArgumentException e) {
//                                            LOGGER.error("java.lang.IllegalArgumentException: No enum constant com.godcheese.tile.database.GenerateEntityProperties.MySqlFieldType.)VALUES={}",fieldType);
                                        }

                                        // 识别 字段注释（comment）
                                        String regexComment = "comment *(\\S* * \\S*)";
                                        Pattern pattern2 = Pattern.compile(regexComment, Pattern.CASE_INSENSITIVE);
                                        Matcher matcher2 = pattern2.matcher(f);
                                        if (matcher2.find()) {
                                            String comment = matcher2.group(1).replaceAll("'|\"", "");
                                            mySqlField.setComment(comment);
                                        }


                                        if (mySqlField.getName() != null && mySqlField.getType() != null) {

                                            mySqlFieldList.add(mySqlField);
                                        }

//                                        System.out.println(mySqlField.toString());

                                    }
                                }
                            }
                            mySqlTable.setFieldList(mySqlFieldList);

                        }
                    }
                }

                return mySqlTable;
            case ORACLE:
                break;
            default:
                break;
        }

        return null;
    }

    /**
     * 指定 sql 文件目录，批量生成实体文件
     *
     * @param properties
     */
    public static void generateEntity(SqlGenerateProperties properties) throws IOException {
        File sqlDirectory = new File(properties.getSqlDirectory());
        File[] files = sqlDirectory.listFiles();
        if (files != null) {
            for (File sqlFile : files) {
                String suffix = properties.getSqlFileSuffix() != null ? properties.getSqlFileSuffix() : "";
                String regex = "(\\S\\D*)" + suffix;
                if (sqlFile.getName().matches(regex)) {
                    generateEntity(sqlFile.getPath(), properties.getEntityDirectory(), properties);
                }
            }
        }
    }

    /**
     * 指定单个 sql 文件，单个实体文件生成
     *
     * @param sqlFilename
     * @param entityDirectory
     * @param properties
     */
    public static void generateEntity(String sqlFilename, String entityDirectory, SqlGenerateProperties properties) throws IOException {
        File sqlFile = new File(sqlFilename);
        if (sqlFile.exists()) {
            File entityDir = new File(entityDirectory);
            if (!entityDir.exists()) {
                if (!entityDir.mkdirs()) {
                    throw new IOException("make dir fail.");
                }
            }

            FileInputStream sqlFileInputStream = null;
            InputStreamReader inputStreamReader = null;
            BufferedReader bufferedReader = null;

            FileOutputStream entityFileOutputStream = null;
            OutputStreamWriter outputStreamWriter = null;
            BufferedWriter bufferedWriter = null;

            try {
                sqlFileInputStream = new FileInputStream(sqlFile);
                inputStreamReader = new InputStreamReader(sqlFileInputStream);
                bufferedReader = new BufferedReader(inputStreamReader);

                StringBuilder stringBuilder = new StringBuilder();
                String line;
                while ((line = bufferedReader.readLine()) != null) {
                    stringBuilder.append(line);
                }

                String suffix = properties.getEntityFileSuffix() != null ? properties.getEntityFileSuffix() : "";
                String content = null;
                String filename = null;
                switch (properties.getDatabaseType()) {
                    case MYSQL:
                        MySqlTable mySqlTable = (MySqlTable) analysisSqlContent(properties.getDatabaseType(), stringBuilder.toString());
                        content = generateEntityContent(properties, mySqlTable);
                        filename = entityDirectory + File.separator + StringUtil.firstUpperCase("_", mySqlTable.getName()) + suffix;
                        break;
                    case ORACLE:
                        break;
                    default:
                        break;
                }

                File entityFile = new File(filename);
                entityFileOutputStream = new FileOutputStream(entityFile);
                outputStreamWriter = new OutputStreamWriter(entityFileOutputStream);
                bufferedWriter = new BufferedWriter(outputStreamWriter);
                if (content != null) {
                    bufferedWriter.write(content);
                }
                bufferedWriter.newLine();
                bufferedWriter.flush();

            } catch (IOException e) {
                e.printStackTrace();
                try {
                    if (sqlFileInputStream != null) {
                        sqlFileInputStream.close();
                    }
                    if (inputStreamReader != null) {
                        inputStreamReader.close();
                    }
                    if (bufferedReader != null) {
                        bufferedReader.close();
                    }
                    if (entityFileOutputStream != null) {
                        entityFileOutputStream.close();
                    }
                    if (outputStreamWriter != null) {
                        outputStreamWriter.close();
                    }
                    if (bufferedWriter != null) {
                        bufferedWriter.close();
                    }
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
        }
    }

    /**
     * 生成实体文件的代码文本
     *
     * @param properties
     * @param table
     * @return
     */
    private static String generateEntityContent(SqlGenerateProperties properties, DatabaseTable<? extends DatabaseField> table) {
        String newLine = "\n";
        String newLineEnter = "\n\r";

        StringBuilder head = new StringBuilder();
        StringBuilder wrap = new StringBuilder();
        StringBuilder property = new StringBuilder();
        StringBuilder getterAndSetter = new StringBuilder().append(newLineEnter);

        String entityPackage = properties.getEntityPackage() != null ? properties.getEntityPackage() : "";

        head.append("package ").append(entityPackage).append(";").append(newLineEnter);
        head.append("import java.io.Serializable;").append(newLine);

        wrap.append(newLine).append("/**").append(newLine).append(" * ").append("@author godcheese [godcheese@outlook.com]").append(newLine).append(" * @date ").append(new SimpleDateFormat("yyyy/M/d HH:mm").format(new Date())).append(newLine).append(" */").append(newLine);

//        System.out.println(table.getName());
        String fileName = FileUtil.getName(StringUtil.firstUpperCase("_", table.getName()) + (properties.getEntityFileSuffix() != null ? properties.getEntityFileSuffix() : ""));

        wrap.append("public class ")
                .append(fileName)
                .append(" implements Serializable {")
                .append(newLineEnter);

        Map<String, Class<?>> fieldRuleMap = properties.getFieldConvertRuleTable();
        String fieldTypeSimpleName = "";
        String fieldTypePackageName = "";

        // 生成 sql 字段 相关的代码
        switch (properties.getDatabaseType()) {
            case MYSQL:
                if (table instanceof MySqlTable) {
                    MySqlTable mySqlTable = (MySqlTable) table;
                    for (MySqlField field : mySqlTable.getFieldList()) {
                        String fieldName = field.getName();
                        fieldName = StringUtil.firstUpperCase("_", fieldName);
                        String typeValue = field.getType().getValue().toUpperCase();
                        if (fieldRuleMap.containsKey(typeValue)) {
                            fieldTypeSimpleName = fieldRuleMap.get(typeValue).getSimpleName();
                            fieldTypePackageName = fieldRuleMap.get(typeValue).getCanonicalName();
                        }

                        // 私有属性上添加javadoc为字段 comment
                        if (properties.getAppendComment()) {
                            property.append(newLine).append(properties.getIndentWhiteSpace()).append("/**").append(newLine).append(properties.getIndentWhiteSpace()).append(" * ").append(field.getComment()).append(newLine).append(properties.getIndentWhiteSpace()).append(" */").append(newLine);
                        }

                        // 声明私有属性
                        property.append(properties.getIndentWhiteSpace()).append("private ").append(fieldTypeSimpleName).append(" ").append(StringUtil.firstLowerCase(fieldName)).append(";").append(newLine);

                        // 导入 私有属性类型package
                        String importPackage = "import " + fieldTypePackageName + ";";
                        if (head.indexOf(importPackage) == -1) {
                            head.append(importPackage).append(newLine);
                        }

                        // 生成 getter 方法
                        getterAndSetter.append(properties.getIndentWhiteSpace()).append("public ").append(fieldTypeSimpleName).append(" ").append("get").append(fieldName).append("() {").append(newLine).append(properties.getIndentWhiteSpace()).append(properties.getIndentWhiteSpace()).append("return ").append(StringUtil.firstLowerCase(fieldName)).append(";").append(newLine).append(properties.getIndentWhiteSpace()).append("}").append(newLineEnter);

                        // 生成 setter 方法
                        getterAndSetter.append(properties.getIndentWhiteSpace()).append("public void set").append(fieldName).append("(").append(fieldTypeSimpleName).append(" ").append(StringUtil.firstLowerCase(fieldName)).append(") {").append(newLine).append(properties.getIndentWhiteSpace()).append(properties.getIndentWhiteSpace()).append("this.").append(StringUtil.firstLowerCase(fieldName)).append(" = ").append(StringUtil.firstLowerCase(fieldName)).append(";").append(newLine).append(properties.getIndentWhiteSpace()).append("}").append(newLineEnter);
                    }
                }
                break;
            case ORACLE:
                break;
            default:
                break;
        }

        return head.toString() + wrap.toString() + property.toString() + getterAndSetter.append(newLine).append("}");
    }

    /**
     * 指定 sql 文件目录，批量生成 MyBatis Mapper 文件
     *
     * @param properties
     */
    public static void generateMyBatisMapper(SqlGenerateProperties properties) throws IOException {
        File sqlDirectory = new File(properties.getSqlDirectory());
        File[] files = sqlDirectory.listFiles();
        if (files != null) {
            for (File sqlFile : files) {
                String suffix = properties.getSqlFileSuffix() != null ? properties.getSqlFileSuffix() : "";
                String regex = "(\\S\\D*)" + suffix;
                if (sqlFile.getName().matches(regex)) {
                    generateMyBatisMapper(sqlFile.getPath(), properties.getMyBatisMapperDirectory(), properties);
                }
            }
        }
    }

    /**
     * 生成 MyBatis Mapper 文件
     *
     * @param sqlFilename
     * @param mybatisMapperDirectory
     * @param properties
     */
    public static void generateMyBatisMapper(String sqlFilename, String mybatisMapperDirectory, SqlGenerateProperties properties) throws IOException {

        File sqlFile = new File(sqlFilename);
        if (sqlFile.exists()) {
            File entityDir = new File(mybatisMapperDirectory);
            if (!entityDir.exists()) {
                if (!entityDir.mkdirs()) {
                    throw new IOException("make dir fail.");
                }
            }

            FileInputStream sqlFileInputStream = null;
            InputStreamReader inputStreamReader = null;
            BufferedReader bufferedReader = null;

            FileOutputStream entityFileOutputStream = null;
            OutputStreamWriter outputStreamWriter = null;
            BufferedWriter bufferedWriter = null;

            try {
                sqlFileInputStream = new FileInputStream(sqlFile);
                inputStreamReader = new InputStreamReader(sqlFileInputStream);
                bufferedReader = new BufferedReader(inputStreamReader);

                StringBuilder stringBuilder = new StringBuilder();
                String line;
                while ((line = bufferedReader.readLine()) != null) {
                    stringBuilder.append(line);
                }

                String suffix = properties.getMyBatisMapperFileSuffix() != null ? properties.getMyBatisMapperFileSuffix() : "";
                String content = null;
                String filename = null;
                switch (properties.getDatabaseType()) {
                    case MYSQL:
                        MySqlTable mySqlTable = (MySqlTable) analysisSqlContent(properties.getDatabaseType(), stringBuilder.toString());
                        content = generateMyBatisMapperContent(properties, mySqlTable);
                        filename = mybatisMapperDirectory + File.separator + StringUtil.firstUpperCase("_", mySqlTable.getName()) + suffix;
                        break;
                    case ORACLE:
                        break;
                    default:
                        break;
                }

                File entityFile = new File(filename);

                entityFileOutputStream = new FileOutputStream(entityFile);
                outputStreamWriter = new OutputStreamWriter(entityFileOutputStream);
                bufferedWriter = new BufferedWriter(outputStreamWriter);
                bufferedWriter.write(content);
                bufferedWriter.newLine();
                bufferedWriter.flush();

            } catch (IOException e) {
                e.printStackTrace();
                try {
                    if (sqlFileInputStream != null) {
                        sqlFileInputStream.close();
                    }
                    if (inputStreamReader != null) {
                        inputStreamReader.close();
                    }
                    if (bufferedReader != null) {
                        bufferedReader.close();
                    }
                    if (entityFileOutputStream != null) {
                        entityFileOutputStream.close();
                    }
                    if (outputStreamWriter != null) {
                        outputStreamWriter.close();
                    }
                    if (bufferedWriter != null) {
                        bufferedWriter.close();
                    }
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
        }

    }

    /**
     * 生成实体文件的代码文本
     *
     * @param properties
     * @param table
     * @return
     */
    public static String generateMyBatisMapperContent(SqlGenerateProperties properties, DatabaseTable<? extends DatabaseField> table) {
        String newLine = "\n";

        StringBuilder head = new StringBuilder();
        StringBuilder wrap = new StringBuilder();
        StringBuilder property = new StringBuilder();
        StringBuilder method = new StringBuilder().append(newLine).append(newLine);

        String myBatisPackage = properties.getMyBatisMapperPackage() != null ? properties.getMyBatisMapperPackage() : "";

        head.append("package ").append(myBatisPackage).append(";").append(newLine).append(newLine);

        head.append("import org.apache.ibatis.annotations.Mapper;").append(newLine);

        wrap.append(newLine).append("/**").append(newLine).append(" * ").append("@author godcheese [godcheese@outlook.com]").append(newLine).append(" * @date ").append(new SimpleDateFormat("yyyy/M/d HH:mm").format(new Date())).append(newLine).append(" */").append(newLine).append("@Mapper").append(newLine);

        String fileName = FileUtil.getName(StringUtil.firstUpperCase("_", table.getName()) + (properties.getMyBatisMapperFileSuffix() != null ? properties.getMyBatisMapperFileSuffix() : ""));

        wrap.append("public interface ")
                .append(fileName).append(" {");

        Map<String, Class<?>> fieldRuleMap = properties.getFieldConvertRuleTable();
        String fieldTypeSimpleName = "";
        String fieldTypePackageName = "";

        // 生成 sql 字段 相关的代码
        switch (properties.getDatabaseType()) {
            case MYSQL:
                if (table instanceof MySqlTable) {
                    MySqlTable mySqlTable = (MySqlTable) table;

                    String entityName = StringUtil.firstUpperCase("_", FileUtil.getName(mySqlTable.getName() + properties.getEntityFileSuffix()));
                    String entityNamespace = (properties.getEntityPackage() != null ? properties.getEntityPackage() + "." : "") + entityName;
                    head.append("import ").append(entityNamespace).append(";").append(newLine);

                    StringUtil.firstLowerCase(StringUtil.firstUpperCase("_", mySqlTable.getPrimaryKey()));
                    method.append(properties.getIndentWhiteSpace()).append("int insertOne(").append(entityName).append(" ").append(StringUtil.firstLowerCase(entityName)).append(");").append(newLine).append(newLine);

                    StringUtil.firstLowerCase(StringUtil.firstUpperCase("_", mySqlTable.getPrimaryKey()));
                    method.append(properties.getIndentWhiteSpace()).append("int updateOne(").append(entityName).append(" ").append(StringUtil.firstLowerCase(entityName)).append(");").append(newLine).append(newLine);

                    String primaryKey = null;
                    if ((primaryKey = mySqlTable.getPrimaryKey()) != null) {
                        MySqlField field = null;
                        if ((field = mySqlTable.getField(primaryKey)) != null) {

                            String typeValue = field.getType().getValue().toUpperCase();
                            if (fieldRuleMap.containsKey(typeValue)) {
                                fieldTypeSimpleName = fieldRuleMap.get(typeValue).getSimpleName();
                                fieldTypePackageName = fieldRuleMap.get(typeValue).getCanonicalName();
                            }

                            /**
                             * 生成 int deleteOne(Long id);
                             */
                            StringUtil.firstLowerCase(StringUtil.firstUpperCase("_", mySqlTable.getPrimaryKey()));
                            method.append(properties.getIndentWhiteSpace()).append("int deleteOne(").append(fieldTypeSimpleName).append(" ").append(mySqlTable.getPrimaryKey()).append(");").append(newLine).append(newLine);

                            /**
                             * 生成 ApiEntity getOne(Long id);
                             */
                            StringUtil.firstLowerCase(StringUtil.firstUpperCase("_", mySqlTable.getPrimaryKey()));
                            method.append(properties.getIndentWhiteSpace()).append(entityName).append(" getOne(").append(fieldTypeSimpleName).append(" ").append(mySqlTable.getPrimaryKey()).append(");").append(newLine).append(newLine);

                            // 导入主键的类型包，如：Long
                            String importPackage = "import " + fieldTypePackageName + ";";
                            if (head.indexOf(importPackage) == -1) {
                                head.append(importPackage).append(newLine);
                            }


                        }
                    }


                    // 导入类型包，如：List
                    String importPackage = "import java.util.List;";
                    if (head.indexOf(importPackage) == -1) {
                        head.append(importPackage).append(newLine);
                    }

                    method.append(properties.getIndentWhiteSpace()).append("List<").append(entityName).append("> listAll();");
                }

                break;
            case ORACLE:
                break;
            default:
                break;
        }

        return head.toString() + wrap.toString() + property.toString() + method.append(newLine).append("}");
    }

    /**
     * 指定 sql 文件目录，批量生成实体
     *
     * @param properties
     */
    public static void generateMyBatisMapperXml(SqlGenerateProperties properties) throws IOException {
        File sqlDirectory = new File(properties.getSqlDirectory());
        File[] files = sqlDirectory.listFiles();
        if (files != null) {
            for (File sqlFile : files) {
                String suffix = properties.getSqlFileSuffix() != null ? properties.getSqlFileSuffix() : "";
                String regex = "(\\S\\D*)" + suffix;
                if (sqlFile.getName().matches(regex)) {
                    generateMyBatisMapperXml(sqlFile.getPath(), properties.getMyBatisMapperXmlDirectory(), properties);
                }
            }
        }
    }

    /**
     * 生成 MyBatis Mapper XML 文件
     *
     * @param sqlFilename
     * @param mybatisMapperXmlDirectory
     * @param properties
     */
    public static void generateMyBatisMapperXml(String sqlFilename, String mybatisMapperXmlDirectory, SqlGenerateProperties properties) throws IOException {
        File sqlFile = new File(sqlFilename);
        if (sqlFile.exists()) {
            File entityDir = new File(mybatisMapperXmlDirectory);
            if (!entityDir.exists()) {
                if (!entityDir.mkdirs()) {
                    throw new IOException("make dir fail.");
                }
            }

            FileInputStream sqlFileInputStream = null;
            InputStreamReader inputStreamReader = null;
            BufferedReader bufferedReader = null;

            FileOutputStream entityFileOutputStream = null;
            OutputStreamWriter outputStreamWriter = null;
            BufferedWriter bufferedWriter = null;

            try {
                sqlFileInputStream = new FileInputStream(sqlFile);
                inputStreamReader = new InputStreamReader(sqlFileInputStream);
                bufferedReader = new BufferedReader(inputStreamReader);

                StringBuilder stringBuilder = new StringBuilder();
                String line;
                while ((line = bufferedReader.readLine()) != null) {
                    stringBuilder.append(line);
                }

                String suffix = properties.getMyBatisMapperXmlFileSuffix() != null ? properties.getMyBatisMapperXmlFileSuffix() : "";
                String content = null;
                String filename = null;
                switch (properties.getDatabaseType()) {
                    case MYSQL:
                        MySqlTable mySqlTable = (MySqlTable) analysisSqlContent(properties.getDatabaseType(), stringBuilder.toString());
                        content = generateMyBatisMapperXmlContent(properties, mySqlTable);
                        filename = mybatisMapperXmlDirectory + File.separator + StringUtil.firstUpperCase("_", mySqlTable.getName()) + suffix;
                        break;
                    case ORACLE:
                        break;
                    default:
                        break;
                }

                File entityFile = new File(filename);

                entityFileOutputStream = new FileOutputStream(entityFile);
                outputStreamWriter = new OutputStreamWriter(entityFileOutputStream);
                bufferedWriter = new BufferedWriter(outputStreamWriter);
                bufferedWriter.write(content);
                bufferedWriter.newLine();
                bufferedWriter.flush();

            } catch (IOException e) {
                e.printStackTrace();

                try {
                    sqlFileInputStream.close();
                    inputStreamReader.close();
                    bufferedReader.close();

                    entityFileOutputStream.close();
                    outputStreamWriter.close();
                    bufferedWriter.close();
                } catch (IOException e1) {
                    e1.printStackTrace();
                }

            }
        }

    }

    /**
     * 生成 MyBatis Mapper XML 文件的代码文本
     *
     * @param properties
     * @param table
     * @return
     */
    private static String generateMyBatisMapperXmlContent(SqlGenerateProperties properties, DatabaseTable table) {
        String newLine = "\n";

        StringBuilder head = new StringBuilder();
        StringBuilder mapperWrap = new StringBuilder();
        StringBuilder resultMap = new StringBuilder();
        StringBuilder method = new StringBuilder();

        String mapperName = StringUtil.firstUpperCase("_", FileUtil.getName(table.getName() + properties.getMyBatisMapperFileSuffix()));
        String mapperNamespace = properties.getMyBatisMapperPackage() != null ? properties.getMyBatisMapperPackage() + "." + mapperName : mapperName;

        head.append("<?xml version=\"1.0\" encoding=\"UTF-8\" ?>").append(newLine)
                .append("<!DOCTYPE mapper PUBLIC \"-//mybatis.org//DTD Config 3.0//EN\" \"http://mybatis.org/dtd/mybatis-3-mapper.dtd\">").append(newLine);

        mapperWrap.append("<mapper namespace=\"").append(mapperNamespace).append("\">").append(newLine);


        /**
         * <resultMap id="BaseResultMap" type="com.godcheese.demo.entity.ApiEntity">
         */
        String entityName = StringUtil.firstUpperCase("_", FileUtil.getName(table.getName() + properties.getEntityFileSuffix()));
        String entityNamespace = properties.getEntityPackage() != null ? properties.getEntityPackage() + "." + entityName : entityName;
        resultMap.append(properties.getIndentWhiteSpace())
                .append("<resultMap id=\"BaseResultMap\" type=\"").append(entityNamespace).append("\">").append(newLine);

        // 生成 sql 字段 相关的代码
        switch (properties.getDatabaseType()) {
            case MYSQL:
                if (table instanceof MySqlTable) {

                    MySqlTable mySqlTable = (MySqlTable) table;

                    /**
                     * 生成
                     * <sql id="TableName">
                     *     ` table_name `
                     * </sql>
                     */
                    method.append(properties.getIndentWhiteSpace()).append("<sql id=\"TableName\">").append(newLine)
                            .append(properties.getIndentWhiteSpace()).append(properties.getIndentWhiteSpace()).append("`").append(table.getName()).append("`").append(newLine)
                            .append(properties.getIndentWhiteSpace()).append("</sql>").append(newLine).append(newLine);

                    /**
                     * 生成
                     * <sql id="BaseColumnList">
                     *     `id`, `field2`, `field2`, `field3`
                     * </sql>
                     */
                    method.append(properties.getIndentWhiteSpace()).append("<sql id=\"BaseColumnList\">").append(newLine)
                            .append(properties.getIndentWhiteSpace()).append(properties.getIndentWhiteSpace());

                    /**
                     * 生成
                     * <id column="id" property="id" jdbcType="BIGINT" /> <!-- id -->
                     * <result column="name" property="name" jdbcType="VARCHAR" /> <!-- api 名称 -->
                     * <result column="uri" property="uri" jdbcType="null" /> <!-- 请求地址 -->
                     * <result column="api_category_id" property="apiCategoryId" jdbcType="BIGINT" /> <!-- api -->
                     */
                    for (MySqlField field : mySqlTable.getFieldList()) {
                        String typeValue = field.getType().getValue().toUpperCase();
                        if (field.getName().equals(mySqlTable.getPrimaryKey())) {
                            /**
                             * <id column="id" property="id" jdbcType="BIGINT"/> <!-- id -->
                             */
                            resultMap.append(properties.getIndentWhiteSpace()).append(properties.getIndentWhiteSpace())
                                    .append("<id column=\"")
                                    .append(field.getName()).append("\" property=\"")
                                    .append(StringUtil.firstLowerCase(StringUtil.firstUpperCase("_", field.getName()))).append("\" jdbcType=\"")
                                    .append(properties.getJdbcTypeTable().get(typeValue))
                                    .append("\"/> <!-- ").append(field.getComment()).append(" -->").append(newLine);

                        } else {

                            /**
                             * 生成
                             * <result column="name" property="name" jdbcType="VARCHAR"/> <!-- api 名称 -->
                             */
                            resultMap.append(properties.getIndentWhiteSpace()).append(properties.getIndentWhiteSpace())
                                    .append("<result column=\"")
                                    .append(field.getName())
                                    .append("\" property=\"")
                                    .append(StringUtil.firstLowerCase(StringUtil.firstUpperCase("_", field.getName())))
                                    .append("\" jdbcType=\"")
                                    .append(properties.getJdbcTypeTable().get(typeValue)).append("\"/> <!-- ")
                                    .append(field.getComment()).append(" -->")
                                    .append(newLine);

                        }

                        method.append("`").append(field.getName()).append("`").append(", ");
                    }
                    resultMap.append(properties.getIndentWhiteSpace()).append("</resultMap>").append(newLine).append(newLine);

                    method.delete(method.length() - 2, method.length());
                    method.append(newLine).append(properties.getIndentWhiteSpace()).append("</sql>").append(newLine).append(newLine);

                    // 单个获取
                    if (mySqlTable.getPrimaryKey() != null) {
                        // 单个新增
                        method.append(properties.getIndentWhiteSpace()).append("<insert id=\"insertOne\" useGeneratedKeys=\"true\" keyProperty=\"").append(mySqlTable.getPrimaryKey()).append("\"").append(" parameterType=\"").append(entityNamespace).append("\">").append(newLine).append(properties.getIndentWhiteSpace()).append(properties.getIndentWhiteSpace()).append("INSERT INTO").append(newLine).append(properties.getIndentWhiteSpace()).append(properties.getIndentWhiteSpace()).append("<include refid=\"TableName\"/>").append(newLine);
                        StringBuilder stringBuilder1 = new StringBuilder();
                        StringBuilder stringBuilder2 = new StringBuilder();
                        for (MySqlField field : mySqlTable.getFieldList()) {
                            stringBuilder1.append("`").append(field.getName()).append("`, ");
                            stringBuilder2.append("#{").append(StringUtil.firstLowerCase(StringUtil.firstUpperCase("_", field.getName()))).append("}, ");
                        }
                        stringBuilder1.delete(stringBuilder1.length() - 2, stringBuilder1.length());
                        stringBuilder2.delete(stringBuilder2.length() - 2, stringBuilder2.length());
                        method.append(properties.getIndentWhiteSpace()).append(properties.getIndentWhiteSpace()).append("(").append(stringBuilder1).append(")").append(newLine)
                                .append(properties.getIndentWhiteSpace()).append(properties.getIndentWhiteSpace()).append("VALUES (").append(stringBuilder2).append(")")
                                .append(properties.getIndentWhiteSpace()).append("</insert>").append(newLine).append(newLine);


                        // 单个修改
                        method.append(properties.getIndentWhiteSpace()).append("<update id=\"updateOne\" keyProperty=\"").append(mySqlTable.getPrimaryKey()).append("\"").append(" parameterType=\"").append(entityNamespace).append("\">").append(newLine).append(properties.getIndentWhiteSpace()).append(properties.getIndentWhiteSpace()).append("UPDATE").append(newLine).append(properties.getIndentWhiteSpace()).append(properties.getIndentWhiteSpace()).append("<include refid=\"TableName\"/>").append(newLine);
                        StringBuilder updateStringBuilder = new StringBuilder();
                        for (MySqlField field : mySqlTable.getFieldList()) {
                            if (!field.getName().equals(mySqlTable.getPrimaryKey())) {
                                updateStringBuilder.append("`").append(field.getName()).append("`").append(" = #{").append(StringUtil.firstLowerCase(StringUtil.firstUpperCase("_", field.getName()))).append("}, ");
                            }
                        }
                        updateStringBuilder.delete(updateStringBuilder.length() - 2, updateStringBuilder.length());
                        method.append(properties.getIndentWhiteSpace()).append(properties.getIndentWhiteSpace()).append("SET ").append(updateStringBuilder).append(newLine).append(properties.getIndentWhiteSpace()).append(properties.getIndentWhiteSpace()).append("WHERE ").append("`").append(mySqlTable.getPrimaryKey()).append("`= #{").append(StringUtil.firstLowerCase(StringUtil.firstUpperCase("_", mySqlTable.getPrimaryKey()))).append("}").append(newLine)
                                .append(properties.getIndentWhiteSpace()).append("</update>").append(newLine).append(newLine);


                        // 单个删除
                        method.append(properties.getIndentWhiteSpace()).append("<delete id=\"deleteOne\" parameterType=\"long\">")
                                .append(newLine).append(properties.getIndentWhiteSpace()).append(properties.getIndentWhiteSpace()).append("DELETE FROM").append(newLine).append(properties.getIndentWhiteSpace()).append(properties.getIndentWhiteSpace()).append("<include refid=\"TableName\"/>")
                                .append(newLine).append(properties.getIndentWhiteSpace()).append(properties.getIndentWhiteSpace()).append("WHERE `").append(mySqlTable.getPrimaryKey()).append("` = #{").append(StringUtil.firstLowerCase(StringUtil.firstUpperCase("_", mySqlTable.getPrimaryKey()))).append("}").append(newLine)
                                .append(properties.getIndentWhiteSpace()).append("</delete>").append(newLine).append(newLine);

                        // 单个获取
                        method.append(properties.getIndentWhiteSpace()).append("<select id=\"getOne\" resultMap=\"BaseResultMap\">")
                                .append(newLine).append(properties.getIndentWhiteSpace()).append(properties.getIndentWhiteSpace()).append("SELECT").append(newLine).append(properties.getIndentWhiteSpace()).append(properties.getIndentWhiteSpace()).append("<include refid=\"BaseColumnList\"/>")
                                .append(newLine).append(properties.getIndentWhiteSpace()).append(properties.getIndentWhiteSpace()).append("FROM <include refid=\"TableName\"/> WHERE `").append(mySqlTable.getPrimaryKey()).append("` = #{").append(StringUtil.firstLowerCase(StringUtil.firstUpperCase("_", mySqlTable.getPrimaryKey()))).append("}").append(newLine)
                                .append(properties.getIndentWhiteSpace()).append("</select>").append(newLine).append(newLine);
                    }

                    // 批量获取
                    method.append(properties.getIndentWhiteSpace()).append("<select id=\"listAll\" resultMap=\"BaseResultMap\">")
                            .append(newLine).append(properties.getIndentWhiteSpace()).append(properties.getIndentWhiteSpace()).append("SELECT").append(newLine).append(properties.getIndentWhiteSpace()).append(properties.getIndentWhiteSpace()).append("<include refid=\"BaseColumnList\"/>")
                            .append(newLine).append(properties.getIndentWhiteSpace()).append(properties.getIndentWhiteSpace()).append("FROM <include refid=\"TableName\"/>").append(newLine)
                            .append(properties.getIndentWhiteSpace()).append("</select>").append(newLine).append(newLine);

                }

                break;
            case ORACLE:
                break;
            default:
                break;
        }

        String end = head.toString() + mapperWrap.toString() + resultMap.toString() + method.toString() + "</mapper>";
        return end;
    }
}