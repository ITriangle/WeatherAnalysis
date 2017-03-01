package com.seentech.spiderInfo.spider;


import com.seentech.kafkaIn.KafkaProducer;
import com.seentech.kafkaIn.KafkaProperties;
import com.seentech.spiderInfo.model.WeatherInfo;
import kafka.javaapi.producer.Producer;
import kafka.producer.KeyedMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import us.codecraft.webmagic.ResultItems;
import us.codecraft.webmagic.Task;
import us.codecraft.webmagic.pipeline.Pipeline;

import java.util.Iterator;
import java.util.Map;

/**
 * Created by seentech on 2017/2/22.
 */
@Repository
public class CustomPipeline implements Pipeline {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());


    public CustomPipeline() {
    }

    public void process(ResultItems resultItems, Task task) {

        logger.info("get page: " + resultItems.getRequest().getUrl());

        Iterator var3 = resultItems.getAll().entrySet().iterator();

        while(var3.hasNext()) {
            Map.Entry entry = (Map.Entry)var3.next();

            WeatherInfo weatherInfo = (WeatherInfo) entry.getValue();
            logger.info("WeatherInfo :\t" + weatherInfo.toString());

            /**
             * Kafka 发送消息
             */
//            KafkaProducer kafkaProducer = new KafkaProducer(KafkaProperties.topic);
//            Producer<Integer, String> producer = kafkaProducer.getProducer();
//            producer.send(new KeyedMessage<Integer, String>(KafkaProperties.topic, weatherInfo.toString()));

            Producer<String, String> producerWI = KafkaProducer.getProducerString(KafkaProperties.topic);
            producerWI.send(new KeyedMessage<String, String>(KafkaProperties.topic,weatherInfo.toString()));

        }

    }
}
