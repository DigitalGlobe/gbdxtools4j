package com.digitalglobe.gbdx.tools.catalog.model;

import java.util.List;

import com.google.gson.Gson;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * POJO for handling a simple search request
 */
public class SearchRequest {
    @Expose
    private String searchAreaWkt = null;

    @Expose
    private List<String> types = null;

    @Expose
    @SerializedName("startDate")
    private String startDate = null;

    @Expose
    @SerializedName("endDate")
    private String endDate = null;

    @Expose
    private Integer limit = 1000000;     // how many results to return

    @Expose
    private List<String> filters = null;

    @Expose
    private Boolean tagResults = Boolean.FALSE;

    public String getSearchAreaWkt() {
        return searchAreaWkt;
    }

    public void setSearchAreaWkt(String searchAreaWkt) {
        this.searchAreaWkt = searchAreaWkt;
    }

    public List<String> getTypes() {
        return types;
    }

    public void setTypes(List<String> types) {
        this.types = types;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public Integer getLimit() {
        return limit;
    }

    public void setLimit(Integer limit) {
        this.limit = limit;
    }

    public List<String> getFilters() {
        return filters;
    }

    public void setFilters(List<String> filters) {
        this.filters = filters;
    }

    public Boolean getTagResults() {
        return tagResults;
    }

    public void setTagResults(Boolean tagResults) {
        this.tagResults = tagResults;
    }

    public SearchRequest withSearchAreaWkt(String searchAreaWkt) {
        this.searchAreaWkt = searchAreaWkt;
        return this;
    }

    public SearchRequest withTypes(List<String> types) {
        this.types = types;
        return this;
    }

    public SearchRequest withStartDate(String startDate) {
        this.startDate = startDate;
        return this;
    }

    public SearchRequest withEndDate(String endDate) {
        this.endDate = endDate;
        return this;
    }

    public SearchRequest withLimit(Integer limit) {
        this.limit = limit;
        return this;
    }

    public SearchRequest withFilters(List<String> filters) {
        this.filters = filters;
        return this;
    }

    public SearchRequest withTagResults(Boolean tagResults) {
        this.tagResults = tagResults;
        return this;
    }

    @Override
    public String toString() {
        Gson gson = new Gson();

        return gson.toJson(this);
    }
}
