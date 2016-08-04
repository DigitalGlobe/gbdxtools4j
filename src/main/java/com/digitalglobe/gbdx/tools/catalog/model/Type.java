package com.digitalglobe.gbdx.tools.catalog.model;

import java.util.ArrayList;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.annotations.Expose;

/**
 * A catalog type
 */
public class Type {
    @Expose
    private String superClass;

    @Expose
    private String description;

    @Expose
    private Boolean publicRead = false;

    @Expose
    private Boolean publicCreate = false;

    @Expose
    private List<Property> properties;

    @Expose
    private String identifier;

    @Expose
    private String owner;

    public String getSuperClass() {
        return superClass;
    }

    public void setSuperClass(String superClass) {
        this.superClass = superClass;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Boolean getPublicRead() {
        return publicRead;
    }

    public void setPublicRead(Boolean publicRead) {
        this.publicRead = publicRead;
    }

    public Boolean getPublicCreate() {
        return publicCreate;
    }

    public void setPublicCreate(Boolean publicCreate) {
        this.publicCreate = publicCreate;
    }

    public List<Property> getProperties() {
        return properties;
    }

    public void setProperties(List<Property> properties) {
        this.properties = properties;
    }

    public void addProperty(Property property) {
        synchronized (this) {
            if (properties == null)
                properties = new ArrayList<>();

            properties.add(property);
        }
    }

    public String getIdentifier() {
        return identifier;
    }

    public void setIdentifier(String identifier) {
        this.identifier = identifier;
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public Type withSuperClass(String superClass) {
        this.superClass = superClass;
        return this;
    }

    public Type withDescription(String description) {
        this.description = description;
        return this;
    }

    public Type withPublicRead(Boolean publicRead) {
        this.publicRead = publicRead;
        return this;
    }

    public Type withPublicCreate(Boolean publicCreate) {
        this.publicCreate = publicCreate;
        return this;
    }

    public Type withProperty(Property property) {
        synchronized (this) {
            if (properties == null)
                properties = new ArrayList<>();

            properties.add(property);
        }

        return this;
    }

    public Type withProperties(List<Property> properties) {
        this.properties = properties;
        return this;
    }

    public Type withIdentifier(String identifier) {
        this.identifier = identifier;
        return this;
    }

    public Type withOwner(String owner) {
        this.owner = owner;
        return this;
    }

    @Override
    public String toString() {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();

        return gson.toJson(this);
    }
}
