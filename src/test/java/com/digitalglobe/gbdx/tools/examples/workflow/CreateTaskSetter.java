package com.digitalglobe.gbdx.tools.examples.workflow;

import java.io.IOException;

import com.digitalglobe.gbdx.tools.workflow.WorkflowManager;
import com.digitalglobe.gbdx.tools.workflow.model.task.ContainerDescriptor;
import com.digitalglobe.gbdx.tools.workflow.model.task.ContainerProperties;
import com.digitalglobe.gbdx.tools.workflow.model.task.InputPortDescriptor;
import com.digitalglobe.gbdx.tools.workflow.model.task.OutputPortDescriptor;
import com.digitalglobe.gbdx.tools.workflow.model.task.Task;


public class CreateTaskSetter {
    public static void main(String[] argv) throws IOException {
        WorkflowManager workflowManager = new WorkflowManager();

        Task sampleTask = new Task();
        ContainerDescriptor containerDescriptor = new ContainerDescriptor();
        containerDescriptor.setType(ContainerDescriptor.Type.DOCKER);
        containerDescriptor.setCommand("/bin/bash echo 'hello world'");

        ContainerProperties containerProperties = new ContainerProperties();
        containerProperties.setDomain("something");
        containerProperties.setImage("mydocker/myimage");

        containerDescriptor.setContainerProperties( containerProperties );
        sampleTask.getContainerDescriptors().add(containerDescriptor);

        InputPortDescriptor inputPortDescriptor = new InputPortDescriptor();
        inputPortDescriptor.setDescription("this is the description");
        inputPortDescriptor.setRequired(true);
        inputPortDescriptor.setName("inputparameter1");
        inputPortDescriptor.setType("string");

        sampleTask.getInputPortDescriptors().add(inputPortDescriptor);

        OutputPortDescriptor outputPortDescriptor = new OutputPortDescriptor();
        outputPortDescriptor.setType("string");
        outputPortDescriptor.setName("outputparameter1");
        outputPortDescriptor.setRequired(true);
        outputPortDescriptor.setDescription("this is the output port");

        sampleTask.getOutputPortDescriptors().add( outputPortDescriptor );

        sampleTask.setCallback("http://myhost.tld/post");
        sampleTask.setName("thetaskname");
        sampleTask.setDescription("task description");

        workflowManager.registerTask(sampleTask);
    }
}
