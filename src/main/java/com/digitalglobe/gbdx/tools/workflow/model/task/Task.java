
package com.digitalglobe.gbdx.tools.workflow.model.task;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Generated;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.annotations.Expose;

/**
 * The definition of a GBDX Task
 */
@Generated("org.jsonschema2pojo")
public class Task {

    /**
     * The input ports for the task.
     * (Required)
     * 
     */
    @Expose
    private List<IOPortDescriptor> inputPortDescriptors = new ArrayList<>();
    /**
     * The ports that are output by the task.
     * 
     */
    @Expose
    private List<IOPortDescriptor> outputPortDescriptors = new ArrayList<>();
    /**
     * A human-readable description of the function performed by this task.
     * 
     */
    @Expose
    private String description;
    /**
     * The containers that are capable of running the task.
     * (Required)
     * 
     */
    @Expose
    private List<ContainerDescriptor> containerDescriptors = new ArrayList<>();
    /**
     * A name that uniquely identifies the task descriptor.
     * (Required)
     * 
     */
    @Expose
    private String name;
    /**
     * A callback that will be called when the task is done.
     * (Optional)
     *
     */
    @Expose
    private String callback;
    /**
     * Additional properties of the task.
     * 
     */
    @Expose
    private TaskProperties properties;

    /**
     * The task type
     */
    @Expose
    private String taskType;

    /**
     * The input ports for the task.
     * (Required)
     * 
     * @return
     *     The inputPortDescriptors
     */
    public List<IOPortDescriptor> getInputPortDescriptors() {
        return inputPortDescriptors;
    }

    /**
     * The input ports for the task.
     * (Required)
     * 
     * @param inputPortDescriptors
     *     The inputPortDescriptors
     */
    public void setInputPortDescriptors(List<IOPortDescriptor> inputPortDescriptors) {
        this.inputPortDescriptors = inputPortDescriptors;
    }

    /**
     * The input ports for the task.
     * (Required)
     *
     * @param inputPortDescriptors
     *     The inputPortDescriptors
     *
     * @return Returns a reference to this object so that method calls can be chained together.
     */
    public Task withInputPortDescriptors(List<IOPortDescriptor> inputPortDescriptors) {
        this.inputPortDescriptors = inputPortDescriptors;
        return this;
    }

    /**
     * The ports that are output by the task.
     * 
     * @return
     *     The outputPortDescriptors
     */
    public List<IOPortDescriptor> getOutputPortDescriptors() {
        return outputPortDescriptors;
    }

    /**
     * The ports that are output by the task.
     * 
     * @param outputPortDescriptors
     *     The outputPortDescriptors
     */
    public void setOutputPortDescriptors(List<IOPortDescriptor> outputPortDescriptors) {
        this.outputPortDescriptors = outputPortDescriptors;
    }

    /**
     * The ports that are output by the task.
     *
     * @param outputPortDescriptors
     *     The outputPortDescriptors
     *
     * @return Returns a reference to this object so that method calls can be chained together.
     */
    public Task withOutputPortDescriptors(List<IOPortDescriptor> outputPortDescriptors) {
        this.outputPortDescriptors = outputPortDescriptors;
        return this;
    }

    /**
     * A human-readable description of the function performed by this task.
     * 
     * @return
     *     The description
     */
    public String getDescription() {
        return description;
    }

    /**
     * A human-readable description of the function performed by this task.
     * 
     * @param description
     *     The description
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * A human-readable description of the function performed by this task.
     *
     * @param description
     *     The description
     *
     * @return Returns a reference to this object so that method calls can be chained together.
     */
    public Task withDescription(String description) {
        this.description = description;
        return this;
    }

    /**
     * The containers that are capable of running the task.
     * (Required)
     * 
     * @return
     *     The containerDescriptors
     */
    public List<ContainerDescriptor> getContainerDescriptors() {
        return containerDescriptors;
    }

    /**
     * The containers that are capable of running the task.
     * (Required)
     * 
     * @param containerDescriptors
     *     The containerDescriptors
     */
    public void setContainerDescriptors(List<ContainerDescriptor> containerDescriptors) {
        this.containerDescriptors = containerDescriptors;
    }

    /**
     * The containers that are capable of running the task.
     * (Required)
     *
     * @param containerDescriptors
     *     The containerDescriptors
     * @return Returns a reference to this object so that method calls can be chained together.
     *
     */
    public Task withContainerDescriptors(List<ContainerDescriptor> containerDescriptors) {
        this.containerDescriptors = containerDescriptors;
        return this;
    }

    /**
     * A name that uniquely identifies the task descriptor.
     * (Required)
     * 
     * @return
     *     The name
     */
    public String getName() {
        return name;
    }

    /**
     * A name that uniquely identifies the task descriptor.
     * (Required)
     * 
     * @param name
     *     The name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * A name that uniquely identifies the task descriptor.
     * (Required)
     *
     * @param name
     *     The name
     * @return Returns a reference to this object so that method calls can be chained together.
     */
    public Task withName(String name) {
        this.name = name;
        return this;
    }

    /**
     * Additional properties of the task.
     * 
     * @return
     *     The properties
     */
    public TaskProperties getProperties() {
        return properties;
    }

    /**
     * Additional properties of the task.
     * 
     * @param properties
     *     The properties
     */
    public void setTaskProperties(TaskProperties properties) {
        this.properties = properties;
    }

    /**
     * Additional properties of the task.
     *
     * @param properties
     *     The properties
     * @return Returns a reference to this object so that method calls can be chained together.
     */
    public Task withTaskProperties(TaskProperties properties) {
        this.properties = properties;
        return this;
    }

    /**
     * Get the runtime task type.
     *
     * @return
     *     The task type
     */
    public String getTaskType() {
        return taskType;
    }

    /**
     * Set the runtime task type.
     *
     * @param taskType the task type
     */
    public void setTaskType(String taskType) {
        this.taskType = taskType;
    }

    /**
     * Set the runtime task type.
     *
     * @param taskType the task type
     * @return Returns a reference to this object so that method calls can be chained together.
     */
    public Task withTaskType(String taskType) {
        this.taskType = taskType;
        return this;
    }


    /**
     * A callback URL that will be called when the task is complete or failed.
     * (Optional)
     *
     * @return
     *     The callback
     */
    public String getCallback() {
        return callback;
    }

    /**
     * A callback URL that will be called when the task is complete or failed.
     * (Optional)
     *
     * @param callback
     *     The callback
     */
    public void setCallback(String callback) {
        this.callback = callback;
    }

    /**
     * A callback URL that will be called when the task is complete or failed.
     * (Optional)
     *
     * @param callback
     *     The callback
     * @return Returns a reference to this object so that method calls can be chained together.
     */
    public Task withCallback(String callback) {
        this.callback = callback;
        return this;
    }


    @Override
    public String toString() {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();

        return gson.toJson(this);
    }
}
