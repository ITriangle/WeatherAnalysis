package com.seentech.web;

import com.google.gson.Gson;
import com.seentech.service.ExecRequest;
import com.seentech.web.QueryParams.SearchObject;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by seentech on 2017/2/15.
 */
@RestController
@RequestMapping(value = "/")
public class SearchController {

    /**
     http://localhost:8080/mac_2020_01_01_01/type/_search
     {
     "query": {
     "must": {
     "range": {
     "field": "signal",
     "gt": "-90",
     "gte": "2",
     "lt": "0",
     "lte": "4"
     },
     "expression1": {
     "operation": "term",
     "field": "id",
     "value": "3815027"
     },
     "expression2": {
     "operation": "term",
     "field": "area_code",
     "value": "120105"
     }
     },
     "must_not": {},
     "should": {}
     }
     }
     *
     * @param index
     * @param type
     * @param jsonParam
     * @return
     */
    @RequestMapping(value = "/{index}/{type}/_search")
    public Object searchIndexType(@PathVariable String index, @PathVariable String type, @RequestBody String jsonParam) {

        Gson gson = new Gson();

        SearchObject searchObject = gson.fromJson(jsonParam, SearchObject.class);


        ExecRequest execRequest = new ExecRequest();

        return execRequest.ExecSearchQeustion(index, type, searchObject);
    }


    /**
     * 不指名type,搜索index下所有的type
     * @param index
     * @param jsonParam
     * @return
     */
    @RequestMapping(value = "/{index}/_search")
    public Object searchIndexType(@PathVariable String index, @RequestBody String jsonParam) {

        Gson gson = new Gson();

        SearchObject searchObject = gson.fromJson(jsonParam, SearchObject.class);


        ExecRequest execRequest = new ExecRequest();

        return execRequest.ExecSearchQeustion(index, null, searchObject);
    }
}
