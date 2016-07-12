package com.digitalglobe.gbdx.tools.catalog.model;

import java.util.List;

import com.google.gson.Gson;
import com.google.gson.annotations.Expose;

public class TraverseRequest {

    @Expose
    private String rootRecordId = null;

    @Expose
    private Integer maxdepth = 0;

    @Expose
    private Integer limit = 100000;

    @Expose
    private String direction = null;

    @Expose
    private List<String> labels = null;

    public Integer getMaxdepth() {
        return maxdepth;
    }

    public void setMaxdepth(Integer maxdepth) {
        this.maxdepth = maxdepth;
    }

    public String getDirection() {
        return direction;
    }

    public void setDirection(String direction) {
        this.direction = direction;
    }

    public List<String> getLabels() {
        return labels;
    }

    public void setLabels(List<String> labels) {
        this.labels = labels;
    }

    public String getRootRecordId() {
        return rootRecordId;
    }

    public void setRootRecordId(String rootRecordId) {
        this.rootRecordId = rootRecordId;
    }

    public int getLimit() {
        return limit;
    }

    public void setLimit(int limit) {
        this.limit = limit;
    }

    public TraverseRequest withRootRecordId(String rootRecordId) {
        this.rootRecordId = rootRecordId;
        return this;
    }

    public TraverseRequest withMaxdepth(Integer maxdepth) {
        this.maxdepth = maxdepth;
        return this;
    }

    public TraverseRequest withLimit(Integer limit) {
        this.limit = limit;
        return this;
    }

    public TraverseRequest withDirection(String direction) {
        this.direction = direction;
        return this;
    }

    public TraverseRequest withLabels(List<String> labels) {
        this.labels = labels;
        return this;
    }

    @Override
    public String toString() {
        Gson gson = new Gson();

        return gson.toJson(this);
    }
}
