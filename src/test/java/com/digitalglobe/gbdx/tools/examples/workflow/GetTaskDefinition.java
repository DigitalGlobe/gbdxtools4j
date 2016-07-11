package com.digitalglobe.gbdx.tools.examples.workflow;

import java.io.IOException;

import com.digitalglobe.gbdx.tools.workflow.WorkflowManager;
import com.digitalglobe.gbdx.tools.workflow.model.task.Task;


public class GetTaskDefinition {
    public static void main(String[] argv) throws IOException {
        WorkflowManager workflowManager = new WorkflowManager();

        Task task = workflowManager.getTaskDefinition("AOP_Strip_Processor");
        System.out.print( "AOP_Strip_Processor task is " + task.toString() );
    }
}
