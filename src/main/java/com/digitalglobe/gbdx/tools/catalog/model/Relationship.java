package com.digitalglobe.gbdx.tools.catalog.model;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.annotations.Expose;

/**
 * Defines the relationships between catalog objects.
 */
public class Relationship {

    @Expose
    private String identifier = null;

    @Expose
    private String label = null;

    @Expose
    private String fromObjectId = null;

    @Expose
    private String toObjectId = null;

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public String getFromObjectId() {
        return fromObjectId;
    }

    public void setFromObjectId(String fromObjectId) {
        this.fromObjectId = fromObjectId;
    }

    public String getToObjectId() {
        return toObjectId;
    }

    public void setToObjectId(String toObjectId) {
        this.toObjectId = toObjectId;
    }

    public String getIdentifier() {
        return identifier;
    }

    public void setIdentifier(String identifier) {
        this.identifier = identifier;
    }

    public Relationship withIdentifier(String identifier) {
        this.identifier = identifier;
        return this;
    }

    public Relationship withLabel(String label) {
        this.label = label;
        return this;
    }

    public Relationship withFromObjectId(String fromObjectId) {
        this.fromObjectId = fromObjectId;
        return this;
    }

    public Relationship withToObjectId(String toObjectId) {
        this.toObjectId = toObjectId;
        return this;
    }

    @Override
    public String toString() {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();

        return gson.toJson(this);
    }
}
