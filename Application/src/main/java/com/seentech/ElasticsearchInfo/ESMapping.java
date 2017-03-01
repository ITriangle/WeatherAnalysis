package com.seentech.ElasticsearchInfo;

import com.seentech.domain.ESClient;
import org.elasticsearch.action.admin.indices.create.CreateIndexRequest;
import org.elasticsearch.action.admin.indices.mapping.put.PutMappingRequest;
import org.elasticsearch.client.Requests;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.xcontent.XContentBuilder;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import static org.elasticsearch.common.xcontent.XContentFactory.jsonBuilder;

/**
 * Created by seentech on 2017/2/28.
 */
public class ESMapping {

    private static String getIndex(){

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy_MM_dd");

        return "weather_index_" + simpleDateFormat.format(new Date());

    }


    public static XContentBuilder getWeather_mapping() {
        XContentBuilder weather_mapping = null;

        try {

            weather_mapping = jsonBuilder()
                    .startObject()
                        .startObject("_ttl")
                            .field("enabled", false)
                        .endObject()
                        .startObject("properties")
                            .startObject("address_code")
                                .field("type", "integer")
                            .endObject()
                            .startObject("next24hours_detailed")
                                .field("type","string")
                            .endObject()
                            .startObject("today_brief")
                                .field("type","string")
                            .endObject()
                            .startObject("next1day_brief")
                                .field("type","string")
                            .endObject()
                            .startObject("next2day_brief")
                                .field("type","string")
                            .endObject()
                            .startObject("next3day_brief")
                                .field("type","string")
                            .endObject()
                            .startObject("next4day_brief")
                                .field("type","string")
                            .endObject()
                            .startObject("next5day_brief")
                                .field("type","string")
                            .endObject()
                            .startObject("next6day_brief")
                                .field("type","string")
                            .endObject()

                        .endObject()
                    .endObject();


        } catch (IOException e) {
            e.printStackTrace();
        }


        return weather_mapping;
    }

    public static void createWeather_index(){

        TransportClient client = ESClient.getClient();

        if(client == null){
            System.out.println("Client is null");

            return ;
        }

        CreateIndexRequest request = new CreateIndexRequest(getIndex());

        client.admin().indices().create(request);
    }

    public static void createWeather_mapping(){


        TransportClient client = ESClient.getClient();

        if(client == null){
            System.out.println("Client is null");

            return ;
        }


        PutMappingRequest mapping = Requests.putMappingRequest(getIndex()).type("weather_type").source(ESMapping.getWeather_mapping());

        client.admin().indices().putMapping(mapping).actionGet();
    }


}
