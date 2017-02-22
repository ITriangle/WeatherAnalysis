package com.seentech.service;

import com.seentech.domain.ESClient;
import com.seentech.domain.GetObject;
import com.seentech.domain.ScrollGet;
import com.seentech.web.QueryParams.SearchObject;

/**
 * Created by seentech on 2017/2/10.
 */
public class ExecRequest {

    /**
     * 执行 get API 请求
     * @param index
     * @param type
     * @param id
     * @return
     */
    public String ExecIndexGet(String index, String type, String id){

        GetObject getObject = new GetObject();

        ESClient esClient = new ESClient();

        String json =  getObject.getMacLogStr(esClient, index, type, id);


        if (json == null){
            return "No data: index : " + index + " ,type : " + type + " ,id :" + id ;
        }
        else {

            //拼装写入文件的字符串
            String indexTypeId = "{\"index\": {\"_type\": \""+ type + "\", \"_id\": \""+ id + "\"}}";

            StringBuilder stringBuilder = new StringBuilder(FileOperation.structeBulkStr( type,  id, json));

            String filePath ="./" + index + "_" + type + ".json";
            FileOperation.writeStringToFile(filePath, stringBuilder);

            return json;
        }
    }


    /**
     * 执行 search controller的业务逻辑
     * @param index
     * @param type
     * @param searchObject
     * @return
     */
    public Integer ExecSearchQeustion(String index, String type, SearchObject searchObject){

        ScrollGet scrollGet = new ScrollGet();

        ESClient esClient = new ESClient();

//        List<String> stringList = scrollGet.scrollMacLog(esClient, index, type, searchObject);
//
//
//        if(stringList == null){
//            return null;
//        }else {
//            /**
//             * 获取结果,写入到 JSON 文件中
//             */
//
//            String filePath ="./" + index + "_" + type + ".json";
//
//            StringBuilder stringBuilder = new StringBuilder();
//
//            for (String jsonData: stringList) {
//                stringBuilder.append(jsonData);
//            }
//
//            FileOperation.writeStringToFile(filePath, stringBuilder);
//        }
//        return stringList;

        Integer num = scrollGet.scrollMacLogNum(esClient, index, type, searchObject);

        return num;


    }





}
