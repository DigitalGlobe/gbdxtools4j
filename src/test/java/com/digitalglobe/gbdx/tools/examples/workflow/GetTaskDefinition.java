package com.digitalglobe.gbdx.tools.examples.workflow;

import java.io.IOException;

import com.digitalglobe.gbdx.tools.workflow.WorkflowManager;
import com.digitalglobe.gbdx.tools.workflow.model.task.Task;


public class GetTaskDefinition {
    public static void main(String[] argv) throws IOException {
        WorkflowManager workflowManager = new WorkflowManager();

        Task task = workflowManager.getTaskDefinition("AComp_1.0");
        System.out.print( "test-file-passthrough task is " + task.toString() );
    }
}
