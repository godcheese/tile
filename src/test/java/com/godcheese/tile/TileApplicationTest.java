package com.godcheese.tile;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 * Unit test for simple App.
 */
public class TileApplicationTest extends TestCase {

    /**
     * Create the test case
     *
     * @param testName name of the test case
     */
    public TileApplicationTest(String testName) {
        super(testName);
    }

    /**
     * @return the suite of tests being tested
     */
    public static Test suite() {
        return new TestSuite(TileApplicationTest.class);
    }

    /**
     * Rigourous Test :-)
     */
    public void testTileApplication() {

//        // 指定单个 SQL 文件生成对应的实体类、Mybatis Mapper、MyBatis Mapper XML 文件
//        SqlGenerateProperties properties = new SqlGenerateProperties();
//        properties.setMyBatisMapperPackage("com.godcheese.demo.mapper");
//        properties.setEntityPackage("com.godcheese.demo.entity");
//
//        DatabaseUtil.generateEntity("D:\\demo\\tables\\api_table.sql","D:\\demo\\src\\main\\java\\com\\godcheese\\demo\\entity", properties);
//        DatabaseUtil.generateMyBatisMapper("D:\\demo\\tables\\api_table.sql","D:\\demo\\src\\main\\java\\com\\godcheese\\demo\\mapper", properties);
//        DatabaseUtil.generateMyBatisMapperXml("D:\\demo\\tables\\api_table.sql","D:\\demo\\src\\main\\java\\com\\godcheese\\demo\\mapper", properties);


//        // 批量生成实体类、Mybatis Mapper、MyBatis Mapper XML 文件
//        SqlGenerateProperties properties = new SqlGenerateProperties();
//        properties.setSqlFileSuffix("_table.sql");
//        properties.setMyBatisMapperPackage("com.godcheese.demo.mapper");
//        properties.setEntityPackage("com.godcheese.demo.entity");
//
//
//        properties.setSqlDirectory("D:\\tables");
//        properties.setEntityDirectory("D:\\demo\\src\\main\\java\\com\\godcheese\\demo\\entity");
//        properties.setMyBatisMapperDirectory("D:\\demo\\src\\main\\java\\com\\godcheese\\demo\\mapper");
//        properties.setMyBatisMapperXmlDirectory("D:\\demo\\src\\main\\java\\com\\godcheese\\demo\\mapper");
//
//        DatabaseUtil.generateEntity(properties);
//        DatabaseUtil.generateMyBatisMapperXml(properties);
//        DatabaseUtil.generateMyBatisMapper(properties);

//        DataSizeUtil.Pretty pretty = DataSizeUtil.pretty(1501295805 , 1000);
//
//        System.out.println(pretty.getPrettySize());
//        System.out.println(pretty.getName());
//        System.out.println(pretty.getUnit());
//        BigDecimal bigDecimal = new BigDecimal(String.valueOf(pretty.getPrettySize()));
//        System.out.println(bigDecimal.setScale(2, RoundingMode.HALF_DOWN));

//        System.out.println(DataSizeUtil.prettySize(20431295805L, 1000));
//        System.out.println(DataSizeUtil.format(20431295805L, DataSizeUtil.SizeEnum.GIGA_BYTE, 1000));

//        System.out.println(DataSizeUtil.pretty(1559295805 , 1000).getPrettySize());

//        System.out.println(StandardCharsets.UTF_8.name());
    }
}

