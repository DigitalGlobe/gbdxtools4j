package com.digitalglobe.gbdx.tools.examples.workflow;

import java.io.IOException;

import com.digitalglobe.gbdx.tools.workflow.WorkflowManager;
import com.digitalglobe.gbdx.tools.workflow.model.task.ContainerDescriptor;
import com.digitalglobe.gbdx.tools.workflow.model.task.ContainerProperties;
import com.digitalglobe.gbdx.tools.workflow.model.task.IOPortDescriptor;
import com.digitalglobe.gbdx.tools.workflow.model.task.Task;


public class CreateTaskFluent {
    public static void main(String[] argv) throws IOException {
        WorkflowManager workflowManager = new WorkflowManager();

        Task sampleTask = new Task();
        sampleTask.getContainerDescriptors().add(new ContainerDescriptor()
                                            .withType(ContainerDescriptor.Type.DOCKER)
                                            .withCommand( "/bin/bash echo 'hello world'")
                                            .withContainerProperties( new ContainerProperties()
                                                                     .withDomain("something")
                                                                     .withImage( "mydocker/myimage")));

        sampleTask.getInputPortDescriptors().add( new IOPortDescriptor()
                                                    .withDescription("this is the description")
                                                    .withRequired(true)
                                                    .withName("inputparameter1")
                                                    .withType("string") );

        sampleTask.getOutputPortDescriptors().add( new IOPortDescriptor()
                                                    .withType("string")
                                                    .withName("outputparameter1")
                                                    .withRequired(true)
                                                    .withDescription("this is the output port"));
        sampleTask.withCallback( "http://myhost.tld/post")
                .withName("thetaskname")
                .withDescription("task description");

        workflowManager.registerTask(sampleTask);
    }
}
