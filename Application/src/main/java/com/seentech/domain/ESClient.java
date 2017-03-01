package com.seentech.domain;

import com.seentech.ElasticsearchInfo.ESProperties;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.transport.InetSocketTransportAddress;

import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 * Created by seentech on 2017/2/10.
 */
public class ESClient {


    public static TransportClient getClient() {

        TransportClient client = null;

        try {
            client = TransportClient.builder().build()
                    .addTransportAddress(new InetSocketTransportAddress(InetAddress.getByName(ESProperties.ip), ESProperties.prot));
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }

        return client;
    }


}
