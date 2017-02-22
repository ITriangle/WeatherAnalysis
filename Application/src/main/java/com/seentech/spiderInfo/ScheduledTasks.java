package com.seentech.spiderInfo;

import com.seentech.spiderInfo.spider.CustomPipeline;
import com.seentech.spiderInfo.spider.WeatherSpider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import us.codecraft.webmagic.Spider;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Administrator on 2017/2/22.
 */
@Component
public class ScheduledTasks {
    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");

    @Scheduled(fixedRate = 5000)
    public void reportCurrentTime() {
        System.out.println("现在时间：" + dateFormat.format(new Date()));


    }

    @Autowired
    private CustomPipeline customPipeline;

    @Scheduled(cron = "0 0 0/2 * * ? ")//从0点开始,每2个小时执行一次
    public void spiderInfo(){

        /***
         * 间隔性的执行爬取任务
         */

        System.out.println("----开始执行简书定时任务");
        Spider spider = Spider.create(new WeatherSpider())
                .addUrl("http://www.weather.com.cn/weather/101060101.shtml")
                .addPipeline(new CustomPipeline())
                .thread(5)
                .setExitWhenComplete(true);

        spider.start();
        spider.stop();
    }
}
