package com.seentech;

import com.seentech.kafkaIn.KafkaConsumer;
import com.seentech.kafkaIn.KafkaProducer;
import com.seentech.kafkaIn.KafkaProperties;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * Created by Administrator on 2017/2/22.
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class KafkaTests {

    @Test
    public void test1(){
        KafkaProducer producerThread = new KafkaProducer(KafkaProperties.topic);
        producerThread.start();
        KafkaConsumer consumerThread = new KafkaConsumer(KafkaProperties.topic);
        consumerThread.start();

        try {
            Thread.sleep(100000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
