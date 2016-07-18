package com.digitalglobe.gbdx.tools.examples.workflow;

import java.io.IOException;

import com.digitalglobe.gbdx.tools.workflow.WorkflowManager;


public class WorkflowHeartbeat {
    public static void main(String[] argv) throws IOException {
        WorkflowManager workflowManager = new WorkflowManager();

        if( workflowManager.isAlive() )
            System.out.println("workflow system is up and running");
        else
            System.out.println("unable to get a heartbeat from the workflow system");
    }
}
