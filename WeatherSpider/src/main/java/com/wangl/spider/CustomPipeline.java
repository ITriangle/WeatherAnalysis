package com.wangl.spider;

import com.wangl.model.WeatherWeather;
import us.codecraft.webmagic.ResultItems;
import us.codecraft.webmagic.Task;
import us.codecraft.webmagic.pipeline.Pipeline;

/**
 * Created by seentech on 2017/2/22.
 */
public class CustomPipeline implements Pipeline {
    @Override
    public void process(ResultItems resultItems, Task task) {
        WeatherWeather weather = resultItems.get("weather");

        Long stationId = resultItems.get("stationCode");

        System.out.println("Pipline" + weather.toString());
        System.out.println("Pipline" + stationId);
    }
}
