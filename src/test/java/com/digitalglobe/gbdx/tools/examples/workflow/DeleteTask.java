package com.digitalglobe.gbdx.tools.examples.workflow;

import java.io.IOException;

import com.digitalglobe.gbdx.tools.workflow.WorkflowManager;


public class DeleteTask {
    public static void main(String[] argv) throws IOException {
        WorkflowManager workflowManager = new WorkflowManager();

        workflowManager.deleteTask("sdtask3");
    }
}
