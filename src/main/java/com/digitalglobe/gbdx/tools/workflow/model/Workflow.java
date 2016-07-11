
package com.digitalglobe.gbdx.tools.workflow.model;

import java.util.LinkedHashSet;
import java.util.Set;

import javax.annotation.Generated;

import com.digitalglobe.gbdx.tools.workflow.model.task.Task;
import com.google.gson.Gson;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

@Generated("org.jsonschema2pojo")
public class Workflow {

    /**
     * 
     * (Required)
     * 
     */
    @SerializedName("name")
    @Expose
    private String name;
    /**
     * 
     * (Required)
     * 
     */
    @SerializedName("tasks")
    @Expose
    private Set<Task> tasks = new LinkedHashSet<>();

    /**
     * 
     * (Required)
     * 
     * @return
     *     The name
     */
    public String getName() {
        return name;
    }

    /**
     * 
     * (Required)
     * 
     * @param name
     *     The name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     *
     * (Required)
     *
     * @param name
     *     The name
     * @return Returns a reference to this object so that method calls can be chained together.
     */
    public Workflow withName(String name) {
        this.name = name;
        return this;
    }

    /**
     * 
     * (Required)
     * 
     * @return
     *     The tasks
     */
    public Set<Task> getTasks() {
        return tasks;
    }

    /**
     * 
     * (Required)
     * 
     * @param tasks
     *     The tasks
     */
    public void setTasks(Set<Task> tasks) {
        this.tasks = tasks;
    }

    /**
     *
     * (Required)
     *
     * @param tasks
     *     The tasks
     * @return Returns a reference to this object so that method calls can be chained together.
     */
    public Workflow withTasks(Set<Task> tasks) {
        this.tasks = tasks;
        return this;
    }

    @Override
    public String toString() {
        Gson gson = new Gson();

        return gson.toJson(this);
    }

}
