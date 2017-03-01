# WeatherAnalysis

## 1.环境

- zookeeper : zookeeper-3.4.9
- Kafka : kafka_2.11-0.9.0.1

## 2.
Webmagic+Kafka+Elasticsearch

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