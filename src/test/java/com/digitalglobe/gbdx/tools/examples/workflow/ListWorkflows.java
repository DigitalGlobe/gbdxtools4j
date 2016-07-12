package com.digitalglobe.gbdx.tools.examples.workflow;

import java.io.IOException;
import java.util.List;

import com.digitalglobe.gbdx.tools.workflow.WorkflowManager;


public class ListWorkflows {
    public static void main(String[] argv) throws IOException {
        WorkflowManager workflowManager = new WorkflowManager();


        List<String> workflows = workflowManager.listWorkflows();

        if( workflows != null ) {
            for (String nextWorkflowName : workflows) {
                System.out.println("next workflow id is \"" + nextWorkflowName + "\"");
            }
        }
        else {
            System.out.println("you have no workflows that are accessible to you");
        }
    }
}
