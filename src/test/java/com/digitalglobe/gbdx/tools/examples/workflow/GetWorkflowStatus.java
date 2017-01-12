package com.digitalglobe.gbdx.tools.examples.workflow;

import java.io.IOException;

import com.digitalglobe.gbdx.tools.workflow.WorkflowManager;
import com.digitalglobe.gbdx.tools.workflow.model.Workflow;


public class GetWorkflowStatus {
    public static void main(String[] argv) throws IOException {
        WorkflowManager workflowManager = new WorkflowManager();


        Workflow workflow = workflowManager.getWorkflowStatus(argv[0]);

        if( workflow != null ) {
            System.out.println("workflow is \"" + workflow + "\"");
        }
        else {
            System.out.println("you have no workflows that are accessible to you");
        }
    }
}
