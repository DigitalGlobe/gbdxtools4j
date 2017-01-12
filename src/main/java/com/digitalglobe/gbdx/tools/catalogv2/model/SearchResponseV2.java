package com.digitalglobe.gbdx.tools.catalogv2.model;

import java.util.ArrayList;
import java.util.List;

import com.digitalglobe.gbdx.tools.catalog.model.SearchResponseStats;
import com.google.gson.Gson;
import com.google.gson.annotations.Expose;

/**
 * Represents the response to a search in catalog v2
 */
public class SearchResponseV2 {


    @Expose
    private List<RecordV2> results = new ArrayList<>();

    @Expose
    private SearchResponseStats stats = new SearchResponseStats();



    public List<RecordV2> getResults() {
        return results;
    }

    public void setResults(List<RecordV2> results) {
        this.results = results;
    }

    public SearchResponseStats getStats() {
        return stats;
    }

    public void setStats(SearchResponseStats stats) {
        this.stats = stats;
    }


    public SearchResponseV2 withResults(List<RecordV2> results) {
        this.results = results;
        return this;
    }

    public SearchResponseV2 withStats(SearchResponseStats stats) {
        this.stats = stats;
        return this;
    }

    @Override
    public String toString() {
        Gson gson = new Gson();

        return gson.toJson(this);
    }
}
