package com.digitalglobe.gbdx.tools.catalog.model;

import java.util.ArrayList;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.annotations.Expose;

/**
 * Represents the response to a search
 */
public class SearchResponse {

    @Expose
    private String searchTag = null;

    @Expose
    private List<Record> results = new ArrayList<>();

    @Expose
    private SearchResponseStats stats = new SearchResponseStats();

    public String getSearchTag() {
        return searchTag;
    }

    public void setSearchTag(String searchTag) {
        this.searchTag = searchTag;
    }

    public List<Record> getResults() {
        return results;
    }

    public void setResults(List<Record> results) {
        this.results = results;
    }

    public SearchResponseStats getStats() {
        return stats;
    }

    public void setStats(SearchResponseStats stats) {
        this.stats = stats;
    }

    public SearchResponse withSearchTag(String searchTag) {
        this.searchTag = searchTag;
        return this;
    }

    public SearchResponse withResults(List<Record> results) {
        this.results = results;
        return this;
    }

    public SearchResponse withStats(SearchResponseStats stats) {
        this.stats = stats;
        return this;
    }

    @Override
    public String toString() {
        Gson gson = new Gson();

        return gson.toJson(this);
    }
}
