
# Tile
<p align="center">
  <a href="https://github.com/godcheese/tile"><img src="https://img.shields.io/github/last-commit/godcheese/tile.svg" alt="GitHub Last Commit"></a>
  <a href="https://travis-ci.org/godcheese/tile" rel="nofollow"><img src="https://travis-ci.org/godcheese/tile.svg?branch=master" alt="Build Status"></a>
   <a href="https://sonarcloud.io/dashboard?id=godcheese_tile"><img src="https://sonarcloud.io/api/project_badges/measure?project=godcheese_tile&metric=alert_status" alt="Quality Gate Status"/></a>
  <a href="https://www.codacy.com/manual/godcheese/tile?utm_source=github.com&amp;utm_medium=referral&amp;utm_content=godcheese/tile&amp;utm_campaign=Badge_Grade"><img src="https://app.codacy.com/project/badge/Grade/4b8a62d6c2874d84b73520fb29cdedf7" alt="Codacy Badge"/></a>
  <a href="https://github.com/godcheese/tile/blob/master/LICENSE"><img src="https://img.shields.io/github/license/mashape/apistatus.svg" alt="license"></a>
</p>

## 简介 Introduction

Tile 是一款自用的 Java 快速开发工具集依赖包，提供一些常用的实用类。

## 特性 Features

 - DatabaseUtil
   - [SQL 文件生成实体类、MyBatis Mapper、MyBatis Mapper XML 文件](/docs/DatabaseUtil.md)


## Install
- Maven
  - ``` mvn clean install ```

## Usage

- 手动加入依赖包
访问 [Release](https://github.com/godcheese/tile/releases) 页面，下载最新版 Tile jar 包，放入项目根目录中的 ```lib``` 文件夹下，然后在 ```pom.xml```    ```<dependencies>``` 中加入依赖代码，如下： 
```
<dependency>
    <groupId>com.godcheese</groupId>
    <artifactId>tile</artifactId>
    <version>1.0.0</version>
    <scope>system</scope>
    <systemPath>${libPath}/tile-1.0.0.jar</systemPath>
    <type>jar</type>
</dependency>
```

然后在 ```<build> - <resources>``` 下加入打包依赖包代码，如下：
```
<resource>
    <directory>${libPath}</directory>
    <!-- jar 打包引用此属性值 -->
    <targetPath>BOOT-INF/lib</targetPath>
    <!-- war 打包引用此属性值 -->
    <!-- <targetPath>${project.build.directory}/WEB-INF/lib</targetPath> -->
    <includes>
        <include>**/*.jar</include>
    </includes>
</resource>
```





- 从 Maven 仓库中安装依赖包
GitHub Maven Repository: https://github.com/godcheese/maven-repository
Maven：将 tile-1.0.0.jar 拷贝只项目根目录下的 lib 目录下，然后再 pom.xml 文件下添加以下依赖代码：

```
<dependency>
    <groupId>com.godcheese</groupId>
    <artifactId>tile</artifactId>
    <version>1.0.0</version>
    <type>jar</type>
</dependency>
```

## 捐赠 Donation

If you find Tile useful, you can buy us a cup of coffee

[Paypal Me](https://www.paypal.me/godcheese)
