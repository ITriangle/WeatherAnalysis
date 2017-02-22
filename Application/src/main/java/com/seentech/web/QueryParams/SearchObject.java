package com.seentech.web.QueryParams;

/**
 * Created by seentech on 2017/2/13.
 */
public class SearchObject {
    private QueryObject query;

    public QueryObject getQuery() {
        return query;
    }

    public void setQuery(QueryObject query) {
        this.query = query;
    }

    @Override
    public String toString() {
        return "SearchObject{" +
                "query=" + query +
                '}';
    }
}
