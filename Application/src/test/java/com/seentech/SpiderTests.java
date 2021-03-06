package com.seentech;

import com.seentech.kafkaIn.KafkaConsumer;
import com.seentech.kafkaIn.KafkaProperties;
import com.seentech.spiderInfo.spider.CustomPipeline;
import com.seentech.spiderInfo.spider.WeatherSpider;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import us.codecraft.webmagic.Spider;

/**
 * Created by Administrator on 2017/2/22.
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class SpiderTests {


    @Test
    public void test1() {
        Spider.create(new WeatherSpider())
                .addUrl("http://www.weather.com.cn/weather/101060101.shtml")
                .addPipeline(new CustomPipeline())
//                .addPipeline(new ConsolePipeline())
                .thread(5)

                .run();

        KafkaConsumer consumerThread = new KafkaConsumer(KafkaProperties.topic);
        consumerThread.start();

        try {
            Thread.sleep(100000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
