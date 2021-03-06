package com.seentech.domain;

import com.google.gson.Gson;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.client.transport.TransportClient;


/**
 * Created by seentech on 2017/2/10.
 */
public class GetObject {

    /**
     * @param index
     * @param type
     * @param id
     * @return
     */
    public String getMacLogStr( String index, String type, String id) {
        TransportClient client = ESClient.getClient();

        if (client == null) {
            System.out.println("Client is null");

            return null;
        }

        GetResponse responseGet = client.prepareGet(index, type, id).execute().actionGet();

        //获取结果

        return responseGet.getSourceAsString();
    }

    public MacLog getMacLogObj() {
        TransportClient client = ESClient.getClient();

        if (client == null) {
            System.out.println("Client is null");

            return null;
        }

        GetResponse responseGet = client.prepareGet("mac_2020_01_01_01", "type", "3815027").execute().actionGet();

        //输出结果
        Gson gson = new Gson();
        MacLog macLog = gson.fromJson(responseGet.getSourceAsString(), MacLog.class);

        System.out.println(macLog);

        String json = gson.toJson(macLog);
        System.out.println(json);

        return macLog;
    }








}
