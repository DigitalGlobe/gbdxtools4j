package com.digitalglobe.gbdx.tools.examples.workflow;

import java.io.IOException;
import java.util.List;

import com.digitalglobe.gbdx.tools.workflow.WorkflowManager;


public class ListTasks {
    public static void main(String[] argv) throws IOException {
        WorkflowManager workflowManager = new WorkflowManager();

        List<String> tasks = workflowManager.listTasks();

        for (String nextTaskName : tasks) {
            System.out.println("next task name is \"" + nextTaskName + "\"");
        }
    }
}
