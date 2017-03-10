# WeatherAnalysis
汇集全国的天气信息并持久化,在需要时进行细化分析.前期的分析需求可依赖于 Elasticsearch 提供.对于 Elasticsearch 缺少的 join 功能,打算用 Spark 填补.

## 一.环境

- zookeeper : zookeeper-3.4.9
- Kafka : kafka_2.11-0.9.0.1
- Elasticsearch: 2.4.1

## 二.Maven依赖

```xml
<parent>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-parent</artifactId>
        <version>1.4.3.RELEASE</version>
        <relativePath/> <!-- lookup parent from repository -->
    </parent>

    <properties>
        <project.build.sourceEncoding>UTF-8</project.build.sourceEncoding>
        <project.reporting.outputEncoding>UTF-8</project.reporting.outputEncoding>
        <java.version>1.8</java.version>
    </properties>

    <dependencies>
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-web</artifactId>
        </dependency>

        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-test</artifactId>
            <scope>test</scope>
        </dependency>

        <dependency>
            <groupId>org.elasticsearch</groupId>
            <artifactId>elasticsearch</artifactId>
            <version>2.4.1</version>
        </dependency>

        <dependency>
            <groupId>com.google.code.gson</groupId>
            <artifactId>gson</artifactId>
            <version>2.4</version>
        </dependency>


        <!--去掉springboot本身日志依赖-->
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter</artifactId>
            <exclusions>
                <exclusion>
                    <groupId>org.springframework.boot</groupId>
                    <artifactId>spring-boot-starter-logging</artifactId>
                </exclusion>
            </exclusions>
        </dependency>

        <!--log4j2-->
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-log4j2</artifactId>
        </dependency>

        <!-- https://mvnrepository.com/artifact/org.apache.kafka/kafka_2.11 -->
        <dependency>
            <groupId>org.apache.kafka</groupId>
            <artifactId>kafka_2.11</artifactId>
            <version>0.9.0.1</version>
        </dependency>

        <!--Webmagic-->
        <dependency>
            <groupId>us.codecraft</groupId>
            <artifactId>webmagic-core</artifactId>
            <version>0.6.1</version>
        </dependency>
        <dependency>
            <groupId>us.codecraft</groupId>
            <artifactId>webmagic-extension</artifactId>
            <version>0.6.1</version>
        </dependency>

    </dependencies>

    <build>
        <plugins>
            <plugin>
                <groupId>org.springframework.boot</groupId>
                <artifactId>spring-boot-maven-plugin</artifactId>
            </plugin>
        </plugins>
    </build>
```

## 3.Elasticsearch 数据表设计

### 1.索引
每天建一个索引,索引格式为:weather_XXXX_XX_XX

### 2.类型
只有一个类型:weather_type:

字段名|类型
---|---
address_code    | Integer
next24hours_detailed    | String
today_brief |   String
next1day_brief |   String 
next2day_brief |   String
next3day_brief |   String
next4day_brief |   String
next5day_brief |   String
next6day_brief |   String

所有的String 类型都分词.


### 3.记录编号
记录编号采取自增的方式