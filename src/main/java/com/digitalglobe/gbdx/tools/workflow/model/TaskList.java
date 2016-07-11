package com.digitalglobe.gbdx.tools.workflow.model;

import java.util.List;

/**
 * Simple definition of a task list
 */
public class TaskList {
    private List<String> tasks;

    public List<String> getTasks() {
        return tasks;
    }

    public void setTasks(List<String> tasks) {
        this.tasks = tasks;
    }

    public TaskList withTasks(List<String> tasks) {
        this.tasks = tasks;
        return this;
    }
}
