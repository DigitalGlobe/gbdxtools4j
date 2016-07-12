======================================
gbdxtools4j: Java tools for using GBDX
======================================

gbdxtools4j is a package for ordering imagery and launching workflows on DigitalGlobe's GBDX platform.

In order to use gbdxtools4j, you need GBDX credentials. Email GBDX-Support@digitalglobe.com to get these.

Example scripts can be found under the src/test/java directory of this repo.

See the license file for license rights and limitations (MIT).

**IMPORTANT SAFTEY NOTE**: gbdxtools4j is very much in development right now.  There is much more work to
do but this is a start at getting a Java version of the excellent <a href="https://github.com/DigitalGlobe/gbdxtools">gbdxtools</a>
for the Python world.


## Installation

gbdxtools4j *requires* Java 8 at this time.  Maven 3 is also a prerequsite.  Once you have
that installed you can build gbdxtools4j

Clone this repository and do a

    mvn clean install

Keep in mind that the master branch is constantly under development. 

## Setup

gbdxtools4j can read a configuration file in your home directory to help with authentication
to the GBDX system.  The file is named ".gbdx-config".  It is a very simple INI style
 file that looks like:
<pre>
    [gbdx]
        client_id = &lt;client id from the GBDX web application&gt;
        client_secret = &lt;client secret from the GBDX web application&gt;
        user_name = &lt;your GBDX user name&gt;
        user_password = &lt;your GBDX password&gt;
</pre>

All of these values come from the GBDX web application.

If the configuration file does not exist then the parameters can also come from
the operating system environment (i.e. <code>setenv</code>) and/or from Java system
parameters (i.e. -D parameters on the Java command line).  Even if the file exists
the parameters in it can be overridden on the command line or with environment variables.
The priority is system property, then environment variable and then the configuration file.

## Simple Examples

### Listing all available tasks:
```java
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
```

### Getting the definition of a single task
```java
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
```

### Creating your own task
A GBDX task can take a good number of parameters but this is a very simple one.

```java
package com.digitalglobe.gbdx.tools.examples.workflow;

import java.io.IOException;

import com.digitalglobe.gbdx.tools.workflow.WorkflowManager;
import com.digitalglobe.gbdx.tools.workflow.model.task.ContainerDescriptor;
import com.digitalglobe.gbdx.tools.workflow.model.task.ContainerProperties;
import com.digitalglobe.gbdx.tools.workflow.model.task.InputPortDescriptor;
import com.digitalglobe.gbdx.tools.workflow.model.task.OutputPortDescriptor;
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

        sampleTask.getInputPortDescriptors().add( new InputPortDescriptor()
                                                    .withDescription("this is the description")
                                                    .withRequired(true)
                                                    .withName("inputparameter1")
                                                    .withType("string") );

        sampleTask.getOutputPortDescriptors().add( new OutputPortDescriptor()
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
```

It is important to note a stylistic issue in the code above.  The gbdxtools4j library allows for
two different programming styles.  The one shown above is known as a <a href="https://en.wikipedia.org/wiki/Fluent_interface">Fluent interface</a>
and allows chaining of methods.  Additionally, gbdxtools4j allows for a somewhat more standard "set/get" interface
too:

```java
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
```

These two examples for creating tasks work exactly the same.  The implementation style is up to
you.

The full list of methods available to the workflow system is defined in src/main/java/com/digitalglobe/gbdx/tools/workflow/WorkflowManager.
