package com.digitalglobe.gbdx.tools.catalog.model;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.google.gson.Gson;
import com.google.gson.annotations.Expose;

/**
 * Represents a Catalog Record
 */
public class Record {
    @Expose
    private String identifier = null;

    @Expose
    private String owner = null;

    @Expose
    private String type = null;

    @Expose
    private Map<String, String> properties = new HashMap<>();

    @Expose
    private Map<String, List<Relationship>> inEdges = new HashMap<>();

    @Expose
    private Map<String, List<Relationship>> outEdges = new HashMap<>();


    public Map<String, String> getProperties() {
        return properties;
    }

    public void setProperties(Map<String, String> properties) {
        this.properties = properties;
    }

    public Map<String, List<Relationship>> getInEdges() {
        return inEdges;
    }

    public void setInEdges(Map<String, List<Relationship>> inEdges) {
        this.inEdges = inEdges;
    }

    public Map<String, List<Relationship>> getOutEdges() {
        return outEdges;
    }

    public void setOutEdges(Map<String, List<Relationship>> outEdges) {
        this.outEdges = outEdges;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public String getIdentifier() {
        return identifier;
    }

    public void setIdentifier(String identifier) {
        this.identifier = identifier;
    }

    public Record withIdentifier(String identifier) {
        this.identifier = identifier;
        return this;
    }

    public Record withOwner(String owner) {
        this.owner = owner;
        return this;
    }

    public Record withType(String type) {
        this.type = type;
        return this;
    }

    public Record withProperties(Map<String, String> properties) {
        this.properties = properties;
        return this;
    }

    public Record withInEdges(Map<String, List<Relationship>> inEdges) {
        this.inEdges = inEdges;
        return this;
    }

    public Record withOutEdges(Map<String, List<Relationship>> outEdges) {
        this.outEdges = outEdges;
        return this;
    }

    @Override
    public String toString() {
        Gson gson = new Gson();

        return gson.toJson(this);
    }
}
