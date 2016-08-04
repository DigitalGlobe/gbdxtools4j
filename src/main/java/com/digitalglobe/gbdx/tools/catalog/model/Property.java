package com.digitalglobe.gbdx.tools.catalog.model;

/**
 * Type property
 */
public class Property {
    private String name;
    private String type;
    private String identifier;

    public Property(String name, String type, String identifier) {
        this.name = name;
        this.type = type;
        this.identifier = identifier;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getIdentifier() {
        return identifier;
    }

    public void setIdentifier(String identifier) {
        this.identifier = identifier;
    }

    public Property withName(String name) {
        this.name = name;
        return this;
    }

    public Property withType(String type) {
        this.type = type;
        return this;
    }

    public Property withIdentifier(String identifier) {
        this.identifier = identifier;
        return this;
    }
}
