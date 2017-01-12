package com.digitalglobe.gbdx.tools.catalogv2.model;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.annotations.Expose;

/**
 * Represents a Catalog Record
 */
public class RecordV2 {
    @Expose
    private String identifier;

    @Expose
    private List<String> type;

    @Expose
    private Map<String, String> properties;


    public Map<String, String> getProperties() {
        return properties;
    }

    public void setProperties(Map<String, String> properties) {
        this.properties = properties;
    }

    public synchronized void setProperty(String key, String value) {
        synchronized (this) {
            if (properties == null)
                properties = new HashMap<>();

            properties.put(key, value);
        }
    }

    public List<String> getType() {
        return type;
    }

    public void setType(List<String> type) {
        this.type = type;
    }


    public String getIdentifier() {
        return identifier;
    }

    public void setIdentifier(String identifier) {
        this.identifier = identifier;
    }

    public RecordV2 withIdentifier(String identifier) {
        this.identifier = identifier;
        return this;
    }

    public RecordV2 withType(List<String> type) {
        this.type = type;
        return this;
    }

    public RecordV2 withProperties(Map<String, String> properties) {
        this.properties = properties;
        return this;
    }

    public RecordV2 withProperty(String key, String value) {
        synchronized (this) {
            if (properties == null)
                properties = new HashMap<>();

            properties.put(key, value);
        }
        return this;
    }

    @Override
    public String toString() {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();

        return gson.toJson(this);
    }
}
