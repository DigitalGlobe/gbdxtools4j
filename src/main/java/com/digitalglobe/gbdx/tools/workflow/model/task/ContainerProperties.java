package com.digitalglobe.gbdx.tools.workflow.model.task;

import javax.annotation.Generated;

import com.google.gson.Gson;
import com.google.gson.annotations.Expose;

/**
 * Container descriptor properties
 *
 */
@Generated("org.jsonschema2pojo")
public class ContainerProperties {

    /**
     * An image name that uniquely identifies the container within the GBD system.
     * (Required)
     *
     */
    @Expose
    private String image;
    /**
     * Identifies the worker domain/group/collective under which to run.
     *
     */
    @Expose
    private String domain;

    /**
     * An image name that uniquely identifies the container within the GBD system.
     * (Required)
     *
     * @return
     *     The image
     */
    public String getImage() {
        return image;
    }

    /**
     * An image name that uniquely identifies the container within the GBD system.
     * (Required)
     *
     * @param image
     *     The image
     */
    public void setImage(String image) {
        this.image = image;
    }

    /**
     * An image name that uniquely identifies the container within the GBD system.
     * (Required)
     *
     * @param image
     *     The image
     *
     * @return Returns a reference to this object so that method calls can be chained together.
     */
    public ContainerProperties withImage(String image) {
        this.image = image;
        return this;
    }

    /**
     * Identifies the worker domain/group/collective under which to run.
     *
     * @return
     *     The domain
     */
    public String getDomain() {
        return domain;
    }

    /**
     * Identifies the worker domain/group/collective under which to run.
     *
     * @param domain
     *     The domain
     */
    public void setDomain(String domain) {
        this.domain = domain;
    }

    /**
     * Identifies the worker domain/group/collective under which to run.
     *
     * @param domain
     *     The domain
     *
     * @return Returns a reference to this object so that method calls can be chained together.
     */
    public ContainerProperties withDomain(String domain) {
        this.domain = domain;
        return this;
    }

    @Override
    public String toString() {
        Gson gson = new Gson();

        return gson.toJson(this);
    }

}