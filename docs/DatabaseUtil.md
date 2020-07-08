
### DatabaseUtil
[> SQL 文件生成实体类、Mybatis Mapper、MyBatis Mapper XML 文件](/docs/DatabaseUtil.md)

- 项目目录 ```D:\demo```
- ```D:\demo\tables``` 目录下的```api_table.sql```文件
- 目前仅支持 MySQL数据库，Oracle 之后再适配

#### SQL 文件：api_table.sql
``` 
DROP TABLE
IF EXISTS `api`;

CREATE TABLE `api` (
  `id`        BIGINT(20) UNSIGNED AUTO_INCREMENT COMMENT 'id',
  `name`      VARCHAR(255) NOT NULL COMMENT 'API 名称',
  `uri` TEXT COMMENT '请求地址',
  `api_category_id` BIGINT(20) UNSIGNED NOT NULL COMMENT 'API 目录 id',
  `sort`      BIGINT(20) UNSIGNED DEFAULT 0 COMMENT '排序',
  `remark`    VARCHAR(255) DEFAULT '' COMMENT '备注',
  `authority` VARCHAR(255) NOT NULL COMMENT '权限（authority）',
  `gmt_create` DATETIME DEFAULT NOW() COMMENT '创建时间',
  `gmt_modified` DATETIME DEFAULT NOW() COMMENT '更新时间',
  PRIMARY KEY `pk_id` (`id`),
  UNIQUE KEY `uk_authority` (`authority`)
)
  ENGINE = INNODB
  DEFAULT CHARACTER
  SET = utf8mb4
  COLLATE = utf8mb4_general_ci
  AUTO_INCREMENT = 1
  ROW_FORMAT = DYNAMIC
  COMMENT = 'api 表';
```

#### 批量生成实体类、MyBatis Mapper、MyBatis Mapper XML 文件
```
   // 批量生成实体类、MyBatis Mapper、MyBatis Mapper XML 文件
        SqlGenerateProperties properties = new SqlGenerateProperties();
        properties.setSqlFileSuffix("_table.sql");
        properties.setMyBatisMapperPackage("com.godcheese.demo.mapper");
        properties.setEntityPackage("com.godcheese.demo.entity");
        properties.setSqlDirectory("D:\\tables");
        properties.setEntityDirectory("D:\\demo\\src\\main\\java\\com\\godcheese\\demo\\entity");
        properties.setMyBatisMapperDirectory("D:\\demo\\src\\main\\java\\com\\godcheese\\demo\\mapper");
        properties.setMyBatisMapperXmlDirectory("D:\\demo\\src\\main\\java\\com\\godcheese\\demo\\mapper");

        DatabaseUtil.generateEntity( properties);
        DatabaseUtil.generateMyBatisMapperXml( properties);
        DatabaseUtil.generateMyBatisMapper(properties);
```

#### 单个生成实体类、MyBatis Mapper、MyBatis Mapper XML 文件
```
   // 指定单个 SQL 文件生成对应的实体类、MyBatis Mapper、MyBatis Mapper XML 文件
        SqlGenerateProperties properties = new SqlGenerateProperties();
        properties.setMyBatisMapperPackage("com.godcheese.demo.mapper");
        properties.setEntityPackage("com.godcheese.demo.entity");

        DatabaseUtil.generateEntity("D:\\demo\\tables\\api_table.sql","D:\\demo\\src\\main\\java\\com\\godcheese\\demo\\entity", properties);
        DatabaseUtil.generateMyBatisMapper("D:\\demo\\tables\\api_table.sql","D:\\demo\\src\\main\\java\\com\\godcheese\\demo\\mapper", properties);
        DatabaseUtil.generateMyBatisMapperXml("D:\\demo\\tables\\api_table.sql","D:\\demo\\src\\main\\java\\com\\godcheese\\demo\\mapper", properties);
```

#### 生成的实体类文件：ApiEntity.java
```
package com.godcheese.demo.entity;

import java.io.Serializable;
import java.lang.Long;
import java.lang.String;
import java.util.Date;

/**
 * @author godcheese [godcheese@outlook.com]
 * @date 2018/4/23 15:58
 */
public class ApiEntity implements Serializable {

    /**
     * id
     */
    private Long id;

    /**
     * API 名称
     */
    private String name;

    /**
     * 请求地址
     */
    private String uri;

    /**
     * API 目录 id
     */
    private Long apiCategoryId;

    /**
     * 排序
     */
    private Long sort;

    /**
     * 备注
     */
    private String remark;

    /**
     * 权限（authority）
     */
    private String authority;

    /**
     * 创建时间
     */
    private Date gmtCreate;

    /**
     * 更新时间
     */
    private Date gmtModified;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUri() {
        return uri;
    }

    public void setUri(String uri) {
        this.uri = uri;
    }

    public Long getApiCategoryId() {
        return apiCategoryId;
    }

    public void setApiCategoryId(Long apiCategoryId) {
        this.apiCategoryId = apiCategoryId;
    }

    public Long getSort() {
        return sort;
    }

    public void setSort(Long sort) {
        this.sort = sort;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getAuthority() {
        return authority;
    }

    public void setAuthority(String authority) {
        this.authority = authority;
    }

    public Date getGmtCreate() {
        return gmtCreate;
    }

    public void setGmtCreate(Date gmtCreate) {
        this.gmtCreate = gmtCreate;
    }

    public Date getGmtModified() {
        return gmtModified;
    }

    public void setGmtModified(Date gmtModified) {
        this.gmtModified = gmtModified;
    }

}
```

#### 生成的 MyBatis Mapper 文件：ApiMapper.java
```
package com.godcheese.demo.mapper;

import org.apache.ibatis.annotations.Mapper;
import com.godcheese.demo.entity.ApiEntity;
import java.lang.Long;
import java.util.List;

/**
 * @author godcheese [godcheese@outlook.com]
 * @date 2018/4/23 15:58
 */
@Mapper
public interface ApiMapper {

    int insertOne(ApiEntity apiEntity);

    int updateOne(ApiEntity apiEntity);

    int deleteOne(Long id);

    ApiEntity getOne(Long id);

    List<ApiEntity> listAll();
}
```

#### 生成的 MyBatis Mapper XML 文件：ApiMapper.xml
```
<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE mapper PUBLIC "-//mybatis.org//DTD Config 3.0//EN" "http://mybatis.org/dtd/mybatis-3-mapper.dtd">
<mapper namespace="com.godcheese.demo.mapper.ApiMapper">
    <resultMap id="BaseResultMap" type="com.godcheese.demo.entity.ApiEntity">
        <id column="id" property="id" jdbcType="BIGINT"/> <!-- id -->
        <result column="name" property="name" jdbcType="VARCHAR"/> <!-- API 名称 -->
        <result column="uri" property="uri" jdbcType="LONGVARCHAR"/> <!-- 请求地址 -->
        <result column="api_category_id" property="apiCategoryId" jdbcType="BIGINT"/> <!-- API 目录 id -->
        <result column="sort" property="sort" jdbcType="BIGINT"/> <!-- 排序 -->
        <result column="remark" property="remark" jdbcType="VARCHAR"/> <!-- 备注 -->
        <result column="authority" property="authority" jdbcType="VARCHAR"/> <!-- 权限（API:UUID） -->
        <result column="gmt_create" property="gmtCreate" jdbcType="DATE"/> <!-- 创建时间 -->
        <result column="gmt_modified" property="gmtModified" jdbcType="DATE"/> <!-- 更新时间 -->
    </resultMap>

    <sql id="TableName">
        `api`
    </sql>

    <sql id="BaseColumnList">
        `id`, `name`, `uri`, `api_category_id`, `sort`, `remark`, `authority`, `gmt_create`, `gmt_modified`
    </sql>

    <insert id="insertOne" useGeneratedKeys="true" keyProperty="id" parameterType="com.godcheese.demo.entity.ApiEntity">
        INSERT INTO
        <include refid="TableName"/>
        (`id`, `name`, `uri`, `api_category_id`, `sort`, `remark`, `authority`, `gmt_create`, `gmt_modified`)
        VALUES (#{id}, #{name}, #{uri}, #{apiCategoryId}, #{sort}, #{remark}, #{authority}, #{gmtCreate}, #{gmtModified})    </insert>

    <update id="updateOne" keyProperty="id" parameterType="com.godcheese.demo.entity.ApiEntity">
        UPDATE
        <include refid="TableName"/>
        SET `name` = #{name}, `uri` = #{uri}, `api_category_id` = #{apiCategoryId}, `sort` = #{sort}, `remark` = #{remark}, `authority` = #{authority}, `gmt_create` = #{gmtCreate}, `gmt_modified` = #{gmtModified}
        WHERE `id`= #{id}
    </update>

    <delete id="deleteOne" parameterType="long">
        DELETE FROM
        <include refid="TableName"/>
        WHERE id = #{id}
    </delete>

    <select id="getOne" resultMap="BaseResultMap">
        SELECT
        <include refid="BaseColumnList"/>
        FROM <include refid="TableName"/> WHERE id = #{id}
    </select>

    <select id="listAll" resultMap="BaseResultMap">
        SELECT
        <include refid="BaseColumnList"/>
        FROM <include refid="TableName"/>
    </select>

</mapper>
```
