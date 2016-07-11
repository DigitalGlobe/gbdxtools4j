
package com.digitalglobe.gbdx.tools.workflow.model.task;

import javax.annotation.Generated;

import com.google.gson.Gson;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Contains the information related to an output port for a GBDX task.
 */
@Generated("org.jsonschema2pojo")
public class OutputPortDescriptor {

    /**
     * A description of the port.
     * 
     */
    @SerializedName("description")
    @Expose
    private String description;
    /**
     * The port name.
     * (Required)
     * 
     */
    @SerializedName("name")
    @Expose
    private String name;
    /**
     * Is the port required.  If not required, the system will select a reasonable default value.
     * 
     */
    @SerializedName("required")
    @Expose
    private Boolean required;
    /**
     * The port type.
     * (Required)
     * 
     */
    @SerializedName("type")
    @Expose
    private String type;
    /**
     * Are multiple ports port name as prefix allowed.
     * 
     */
    @SerializedName("multiplex")
    @Expose
    private Boolean multiplex;

    /**
     * A description of the port.
     * 
     * @return
     *     The description
     */
    public String getDescription() {
        return description;
    }

    /**
     * A description of the port.
     * 
     * @param description
     *     The description
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * A description of the port.
     *
     * @param description
     *     The description
     *  @return Returns a reference to this object so that method calls can be chained together.
     */
    public OutputPortDescriptor withDescription(String description) {
        this.description = description;
        return this;
    }

    /**
     * The port name.
     * (Required)
     * 
     * @return
     *     The name
     */
    public String getName() {
        return name;
    }

    /**
     * The port name.
     * (Required)
     * 
     * @param name
     *     The name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * The port name.
     * (Required)
     *
     * @param name
     *     The name
     *
     * @return Returns a reference to this object so that method calls can be chained together.
     */
    public OutputPortDescriptor withName(String name) {
        this.name = name;
        return this;
    }

    /**
     * Is the port required.  If not required, the system will select a reasonable default value.
     * 
     * @return
     *     The required
     */
    public Boolean getRequired() {
        return required;
    }

    /**
     * Is the port required.  If not required, the system will select a reasonable default value.
     * 
     * @param required
     *     The required
     */
    public void setRequired(Boolean required) {
        this.required = required;
    }

    /**
     * Is the port required.  If not required, the system will select a reasonable default value.
     *
     * @param required
     *     The required
     *
     * @return Returns a reference to this object so that method calls can be chained together.
     */
    public OutputPortDescriptor withRequired(Boolean required) {
        this.required = required;
        return this;
    }

    /**
     * The port type.
     * (Required)
     * 
     * @return
     *     The type
     */
    public String getType() {
        return type;
    }

    /**
     * The port type.
     * (Required)
     * 
     * @param type
     *     The type
     */
    public void setType(String type) {
        this.type = type;
    }

    /**
     * The port type.
     * (Required)
     *
     * @param type
     *     The type
     *
     * @return Returns a reference to this object so that method calls can be chained together.
     */
    public OutputPortDescriptor withType(String type) {
        this.type = type;
        return this;
    }

    /**
     * Are multiple ports port name as prefix allowed.
     * 
     * @return
     *     The multiplex
     */
    public Boolean getMultiplex() {
        return multiplex;
    }

    /**
     * Are multiple ports port name as prefix allowed.
     * 
     * @param multiplex
     *     The multiplex
     */
    public void setMultiplex(Boolean multiplex) {
        this.multiplex = multiplex;
    }

    /**
     * Are multiple ports port name as prefix allowed.
     *
     * @param multiplex
     *     The multiplex
     *
     * @return Returns a reference to this object so that method calls can be chained together.
     */
    public OutputPortDescriptor withMultiplex(Boolean multiplex) {
        this.multiplex = multiplex;
        return this;
    }

    @Override
    public String toString() {
        Gson gson = new Gson();

        return gson.toJson(this);
    }

}
