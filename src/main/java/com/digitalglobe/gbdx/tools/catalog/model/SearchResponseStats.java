package com.digitalglobe.gbdx.tools.catalog.model;

import java.util.HashMap;
import java.util.Map;

import com.google.gson.Gson;
import com.google.gson.annotations.Expose;


/**
 * Holds the statistics for a search response.
 */
public class SearchResponseStats {

    @Expose
    private int totalRecords = 0;

    @Expose
    private int recordsReturned = 0;

    @Expose
    private Map<String, Integer> typeCounts = new HashMap<>();


    public Map<String, Integer> getTypeCounts() {
        return typeCounts;
    }

    public void setTypeCounts(Map<String, Integer> typeCounts) {
        this.typeCounts = typeCounts;
    }

    public int getTotalRecords() {
        return totalRecords;
    }

    public void setTotalRecords(int totalRecords) {
        this.totalRecords = totalRecords;
    }

    public int getRecordsReturned() {
        return recordsReturned;
    }

    public void setRecordsReturned(int recordsReturned) {
        this.recordsReturned = recordsReturned;
    }

    public SearchResponseStats withTotalRecords(int totalRecords) {
        this.totalRecords = totalRecords;
        return this;
    }

    public SearchResponseStats withRecordsReturned(int recordsReturned) {
        this.recordsReturned = recordsReturned;
        return this;
    }

    public SearchResponseStats withTypeCounts(Map<String, Integer> typeCounts) {
        this.typeCounts = typeCounts;
        return this;
    }

    @Override
    public String toString() {
        Gson gson = new Gson();

        return gson.toJson(this);
    }
}
