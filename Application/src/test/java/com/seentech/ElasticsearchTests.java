package com.seentech;

import com.seentech.ElasticsearchInfo.ESMapping;
import com.seentech.domain.ESClient;
import com.seentech.domain.GetObject;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.client.transport.TransportClient;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static org.apache.kafka.common.utils.Utils.sleep;


/**
 * Created by seentech on 2017/2/28.
 */
//@RunWith(SpringRunner.class)
//@SpringBootTest
public class ElasticsearchTests {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Test
    public void test3(){

        TransportClient client = ESClient.getClient();

        if(client == null){
            System.out.println("Client is null");

            return ;
        }

        GetResponse responseGet = client.prepareGet("mac_2020_01_01_01", "type", "3815027").execute().actionGet();

        //输出结果
        System.out.println(responseGet.getSourceAsString());

    }

    @Test
    public void test2() {

        GetObject getObject = new GetObject();

        getObject.getMacLogStr("mac_2020_01_01_01", "type", "3815027");

        getObject.getMacLogObj();

    }

    @Test
    public void test1(){
        ESMapping.createWeather_index();

        /**
         * 需要等待一段时间,让索引创建成功
         */
        sleep(1000);

        ESMapping.createWeather_mapping();
    }

}
