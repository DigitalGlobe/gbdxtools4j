
package com.digitalglobe.gbdx.tools.workflow.model.task;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Generated;

import com.google.gson.Gson;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Describes a container for a GBDX task
 */
@Generated("org.jsonschema2pojo")
public class ContainerDescriptor {

    /**
     * The command used to launch the container.
     * 
     */
    @Expose
    private String command;
    /**
     * Container descriptor containerProperties
     * (Required)
     * 
     */
    @Expose
    private ContainerProperties containerProperties;
    /**
     * The container type.
     * (Required)
     * 
     */
    @Expose
    private ContainerDescriptor.Type type;

    /**
     * The command used to launch the container.
     * 
     * @return
     *     The command
     */
    public String getCommand() {
        return command;
    }

    /**
     * The command used to launch the container.
     * 
     * @param command
     *     The command
     */
    public void setCommand(String command) {
        this.command = command;
    }

    /**
     * The command used to launch the container.
     *
     * @param command
     *     The command
     *
     * @return Returns a reference to this object so that method calls can be chained together.
     */
    public ContainerDescriptor withCommand(String command) {
        this.command = command;
        return this;
    }

    /**
     * Container descriptor containerProperties
     * (Required)
     * 
     * @return
     *     The containerProperties
     */
    public ContainerProperties getContainerProperties() {
        return containerProperties;
    }

    /**
     * Container descriptor containerProperties
     * (Required)
     * 
     * @param containerProperties
     *     The containerProperties
     */
    public void setContainerProperties(ContainerProperties containerProperties) {
        this.containerProperties = containerProperties;
    }

    /**
     * Container descriptor containerProperties
     * (Required)
     *
     * @param containerProperties
     *     The containerProperties
     *
     * @return Returns a reference to this object so that method calls can be chained together.
     */
    public ContainerDescriptor withContainerProperties(ContainerProperties containerProperties) {
        this.containerProperties = containerProperties;
        return this;
    }

    /**
     * The container type.
     * (Required)
     * 
     * @return
     *     The type
     */
    public ContainerDescriptor.Type getType() {
        return type;
    }

    /**
     * The container type.
     * (Required)
     * 
     * @param type
     *     The type
     */
    public void setType(ContainerDescriptor.Type type) {
        this.type = type;
    }

    /**
     * The container type.
     * (Required)
     *
     * @param type
     *     The type
     *
     * @return Returns a reference to this object so that method calls can be chained together.
     */
    public ContainerDescriptor withType(ContainerDescriptor.Type type) {
        this.type = type;
        return this;
    }

    @Generated("org.jsonschema2pojo")
    public enum Type {

        @SerializedName("DOCKER")
        DOCKER("DOCKER"),
        @SerializedName("GPUDOCKER")
        GPUDOCKER("GPUDOCKER"),
        @SerializedName("AMI")
        AMI("AMI"),
        @SerializedName("TASK_DRIVER")
        TASK_DRIVER("TASK_DRIVER");
        private final String value;
        private final static Map<String, ContainerDescriptor.Type> CONSTANTS = new HashMap<String, ContainerDescriptor.Type>();

        static {
            for (ContainerDescriptor.Type c: values()) {
                CONSTANTS.put(c.value, c);
            }
        }

        private Type(String value) {
            this.value = value;
        }

        @Override
        public String toString() {
            return this.value;
        }

        public static ContainerDescriptor.Type fromValue(String value) {
            ContainerDescriptor.Type constant = CONSTANTS.get(value);
            if (constant == null) {
                throw new IllegalArgumentException(value);
            } else {
                return constant;
            }
        }

    }

    @Override
    public String toString() {
        Gson gson = new Gson();

        return gson.toJson(this);
    }
}
