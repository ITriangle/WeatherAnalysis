package com.seentech.domain;

import com.google.gson.Gson;
import com.seentech.service.FileOperation;
import com.seentech.web.QueryParams.ExpressionObject;
import com.seentech.web.QueryParams.RangeObject;
import com.seentech.web.QueryParams.SearchObject;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.unit.TimeValue;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.sort.SortOrder;
import org.elasticsearch.search.sort.SortParseElement;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Created by seentech on 2017/2/15.
 */
public class ScrollGet {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    public BoolQueryBuilder getConditonsBuilder(BoolQueryBuilder boolQueryBuilder, Map<String, Object> conditions, String chooseCondition) {

        Gson gson = new Gson();
        QueryBuilder qb = null;

        for (String key : conditions.keySet()) {
            logger.debug("key: " + key);
            logger.debug(conditions.get(key).toString());

            if (key.equals("range")) {
                RangeObject rangeObject = gson.fromJson(conditions.get(key).toString(), RangeObject.class);
                logger.debug(rangeObject.toString());


                qb = rangeQuery(rangeObject.getField())
                        .from(rangeObject.getGt())
                        .to(rangeObject.getLt())
                        .includeLower(Boolean.valueOf(rangeObject.getGe()))
                        .includeUpper(Boolean.valueOf(rangeObject.getLe()));

            } else {
                ExpressionObject expressionObject = gson.fromJson(conditions.get(key).toString(), ExpressionObject.class);
                logger.debug(expressionObject.toString());

                qb = termQuery(expressionObject.getField(), expressionObject.getValue());
            }

            if (qb != null) {

                switch (chooseCondition) {
                    case "must":
                        boolQueryBuilder = boolQueryBuilder.must(qb);
                        break;
                    case "must_not":
                        boolQueryBuilder = boolQueryBuilder.mustNot(qb);
                        break;
                    case "should":
                        boolQueryBuilder = boolQueryBuilder.should(qb);
                        break;
                    default:

                        break;
                }

            }

        }

        return boolQueryBuilder;
    }

    /**
     * 获取查询条件,通过参数
     *
     * @param searchObject
     * @return
     */
    public BoolQueryBuilder getBoolQueryBuilder(SearchObject searchObject) {

        BoolQueryBuilder boolQueryBuilder = boolQuery().must(matchAllQuery());


        /**
         * 获取条件
         */
        Gson gson = new Gson();

        logger.debug(searchObject.toString());

        Map<String, Object> must = searchObject.getQuery().getMust();

        if (!must.isEmpty()){
            boolQueryBuilder = getConditonsBuilder(boolQueryBuilder, must, "must");
        }



        Map<String, Object> must_not = searchObject.getQuery().getMust_not();

        if (!must_not.isEmpty()){
            boolQueryBuilder = getConditonsBuilder(boolQueryBuilder, must_not, "must_not");
        }

        Map<String, Object> should = searchObject.getQuery().getShould();

        if (!should.isEmpty()){
            boolQueryBuilder = getConditonsBuilder(boolQueryBuilder, should, "should");
        }

        return boolQueryBuilder;

    }

    /**
     * 从 ES 中获取数据
     *
     * @param esClient
     * @param index
     * @param type
     * @param searchObject
     * @return
     */
    public List<String> scrollMacLog(ESClient esClient, String index, String type, SearchObject searchObject) {

        TransportClient client = esClient.getClient();

        if (client == null) {

            logger.error("Client is null");

            return null;
        }


        /**
         * 获取条件
         */
        QueryBuilder conditions = getBoolQueryBuilder(searchObject);

        SearchResponse scrollResp = null;
        if (type != null) {
            scrollResp = client.prepareSearch(index)
                    .setTypes(type)
                    .addSort(SortParseElement.DOC_FIELD_NAME, SortOrder.ASC)
                    .setScroll(new TimeValue(60000))
                    .setQuery(conditions)
                    .setSize(10).execute().actionGet(); //100 hits per shard will be returned for each scroll
        } else {
            scrollResp = client.prepareSearch(index)
                    .addSort(SortParseElement.DOC_FIELD_NAME, SortOrder.ASC)
                    .setScroll(new TimeValue(60000))
                    .setQuery(conditions)
                    .setSize(10).execute().actionGet(); //100 hits per shard will be returned for each scroll
        }

        /**
         * 获取结果
         */
        List<String> stringList = new ArrayList<>();

        while (true) {

            logger.debug("显示结果:");

            for (SearchHit hit : scrollResp.getHits().getHits()) {
                //Handle the hit...

                /**
                 * 在这里获取返回的结果
                 */
                //直接返回结果
//                stringList.add(hit.getSourceAsString());

                //拼装后返回
                stringList.add(FileOperation.structeBulkStr(hit.getType(), hit.getId(), hit.getSourceAsString()));


            }
            scrollResp = client.prepareSearchScroll(scrollResp.getScrollId()).setScroll(new TimeValue(60000)).execute().actionGet();
            //Break condition: No hits are returned
            if (scrollResp.getHits().getHits().length == 0) {


                logger.debug("显示完毕!");

                break;
            }

        }

        return stringList;
    }

    /**
     * 查询结果直接写入文件,返回查询到记录的条数
     * 每个文件最大的记录数为5000
     *
     * @param esClient
     * @param index
     * @param type
     * @param searchObject
     * @return
     */
    public Integer scrollMacLogNum(ESClient esClient, String index, String type, SearchObject searchObject) {

        TransportClient client = esClient.getClient();

        if (client == null) {

            logger.error("Client is null");

            return null;
        }

        /**
         * 获取条件
         */
        QueryBuilder conditions = getBoolQueryBuilder(searchObject);

        SearchResponse scrollResp = null;
        if (type != null) {
            scrollResp = client.prepareSearch(index)
                    .setTypes(type)
                    .addSort(SortParseElement.DOC_FIELD_NAME, SortOrder.ASC)
                    .setScroll(new TimeValue(60000))
                    .setQuery(conditions)
                    .setSize(10).execute().actionGet(); //100 hits per shard will be returned for each scroll
        } else {
            scrollResp = client.prepareSearch(index)
                    .addSort(SortParseElement.DOC_FIELD_NAME, SortOrder.ASC)
                    .setScroll(new TimeValue(60000))
                    .setQuery(conditions)
                    .setSize(10).execute().actionGet(); //100 hits per shard will be returned for each scroll
        }

        /**
         * 获取结果
         */
        List<String> stringList = new ArrayList<>();
        int recordNum = 0;

        while (true) {

            logger.debug("显示结果:");

            for (SearchHit hit : scrollResp.getHits().getHits()) {

                recordNum++;

                logger.debug("Num: " + recordNum);

                //拼装后返回
                stringList.add(FileOperation.structeBulkStr(hit.getType(), hit.getId(), hit.getSourceAsString()));

                if (stringList.size() >= 5000) {

                    String filePath = "./" + index + "-" + type + "-" + recordNum + ".json";

                    StringBuilder stringBuilder = new StringBuilder();

                    for (String jsonData : stringList) {
                        stringBuilder.append(jsonData);
                    }

                    FileOperation.writeStringToFile(filePath, stringBuilder);

                    stringList.clear();
                }


            }
            scrollResp = client.prepareSearchScroll(scrollResp.getScrollId()).setScroll(new TimeValue(60000)).execute().actionGet();
            //Break condition: No hits are returned
            if (scrollResp.getHits().getHits().length == 0) {


                logger.debug("显示完毕!");

                break;
            }

        }

        return recordNum;
    }

}
