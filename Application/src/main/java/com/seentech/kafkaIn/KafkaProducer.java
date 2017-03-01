package com.seentech.kafkaIn;

import kafka.javaapi.producer.Producer;
import kafka.producer.KeyedMessage;
import kafka.producer.ProducerConfig;

import java.util.Properties;

/**
 * Created by seentech on 2017/2/17.
 */
public class KafkaProducer extends Thread {

    private final Producer<Integer, String> producer ;
    private final Producer<String, String> producerString;
    private final String topic ;
    private final Properties props = new Properties();



    public KafkaProducer(String topic)
    {
        props.put("serializer.class", "kafka.serializer.StringEncoder");
        props.put("metadata.broker.list", KafkaProperties.kafkaConnect);
        producer = new Producer<Integer, String>(new ProducerConfig(props));
        producerString = new Producer<String, String>(new ProducerConfig(props));
        this.topic = topic;
    }

    public Producer<String, String> getProducerString() {
        return producerString;
    }

    public static Producer<String, String> getProducerString(String topic){

        return new KafkaProducer(topic).getProducerString();

    }



    public Producer<Integer, String> getProducer() {
        return producer;
    }

    @Override
    public void run() {
        int messageNo = 1;

        while (true)
        {
            String messageStr = new String("Message_" + messageNo);
            System.out.println("Send:" + messageStr);
            producer.send(new KeyedMessage<Integer, String>(topic, messageStr));
            messageNo++;
            try {
                sleep(3000);
            } catch (InterruptedException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
    }
}
