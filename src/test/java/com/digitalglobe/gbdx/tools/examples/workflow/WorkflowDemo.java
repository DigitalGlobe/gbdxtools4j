package com.digitalglobe.gbdx.tools.examples.workflow;

import java.io.IOException;
import java.util.List;

import com.digitalglobe.gbdx.tools.workflow.WorkflowManager;
import com.digitalglobe.gbdx.tools.workflow.model.Workflow;
import com.digitalglobe.gbdx.tools.workflow.model.task.ContainerDescriptor;
import com.digitalglobe.gbdx.tools.workflow.model.task.ContainerProperties;
import com.digitalglobe.gbdx.tools.workflow.model.task.IOPortDescriptor;
import com.digitalglobe.gbdx.tools.workflow.model.task.Task;


public class WorkflowDemo {
    public static void main(String[] argv) throws IOException {
        WorkflowManager workflowManager = new WorkflowManager();

        System.out.println("heartbeat is " + workflowManager.isAlive());

        List<String> tasks = workflowManager.listTasks();

        for (String nextTaskName : tasks) {
            System.out.println("next task name is \"" + nextTaskName + "\"");
        }

        Task task = workflowManager.getTaskDefinition("AOP_Strip_Processor");
        System.out.print( "AOP_Strip_Processor task is " + task.toString() );

        Task sdTask = new Task();
        sdTask.getContainerDescriptors().add(new ContainerDescriptor()
                                            .withType(ContainerDescriptor.Type.DOCKER)
                                            .withCommand( "/bin/bash echo 'hello world'")
                                            .withContainerProperties( new ContainerProperties()
                                                                     .withDomain("raid")
                                                                     .withImage( "tdgp/aop:latest")));

        sdTask.getInputPortDescriptors().add( new IOPortDescriptor()
                                                    .withDescription("this is the description")
                                                    .withRequired(true)
                                                    .withName("sdinputparameter")
                                                    .withType("string") );

        sdTask.getOutputPortDescriptors().add( new IOPortDescriptor()
                                                    .withType("string")
                                                    .withName("sdoutputparameter")
                                                    .withRequired(true)
                                                    .withDescription("this is the output port"));
        sdTask.withCallback( "http://httpbin.org/post")
                .withName("sdtask")
                .withDescription("task description");

        workflowManager.registerTask(sdTask);

        Task returnedSdTask = workflowManager.getTaskDefinition("sdtask");
        System.out.print( "returned test task is " + returnedSdTask.toString() );

        workflowManager.deleteTask("sdtask");


        List<String> workflows = workflowManager.listWorkflows();

        if( workflows != null ) {
            for (String nextWorkflowName : workflows) {
                System.out.println("next workflow id is \"" + nextWorkflowName + "\"");
            }
        }
        else {
            System.out.println("you have no workflows that are accessible to you");
        }

        Workflow workflow = new Workflow();
        workflow.setName("sdworkflow");
        workflow.getTasks().add(task.withTaskType("sdtask"));

        workflowManager.submitWorkflow(workflow);

    }
}
