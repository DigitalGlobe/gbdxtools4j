package com.digitalglobe.gbdx.tools.workflow;

import java.io.IOException;
import java.util.List;

import com.digitalglobe.gbdx.tools.communication.CommunicationBase;
import com.digitalglobe.gbdx.tools.communication.ErrorMessage;
import com.digitalglobe.gbdx.tools.config.ConfigurationManager;
import com.digitalglobe.gbdx.tools.workflow.model.TaskList;
import com.digitalglobe.gbdx.tools.workflow.model.Workflow;
import com.digitalglobe.gbdx.tools.workflow.model.WorkflowList;
import com.digitalglobe.gbdx.tools.workflow.model.task.Task;
import com.google.gson.Gson;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * Manages the interface with the workflow system.
 */
public class WorkflowManager extends CommunicationBase {
    private static final Logger log = LoggerFactory.getLogger(WorkflowManager.class);

    private static String baseUrl;

    public WorkflowManager() {
        ConfigurationManager configurationManager = new ConfigurationManager();

        baseUrl = configurationManager.getBaseAPIUrl() + "/workflows/v1";
    }

    /**
     * Calls /workflows/v1/heartbeat to check if the workflow system is alive
     *
     * @return true if we get an "ok", false otherwise.
     *
     * @throws IOException if there is an error communicating
     *
     */
    public boolean isAlive() throws IOException {
        String okString = getData(baseUrl + "/heartbeat", false);

        if (okString != null) {
            if ((okString.trim().toLowerCase().contains("ok")))
                return true;
        }

        return false;
    }

    /**
     * Get a list of all task names that the user has access to.
     *
     * @return the List of names
     *
     * @throws IOException if there is an error communicating
     */
    public List<String> listTasks() throws IOException {
        String tasksString = getData(baseUrl + "/tasks", true);
        Gson gson = new Gson();

        TaskList taskList = gson.fromJson(tasksString, TaskList.class);

        return taskList.getTasks();
    }

    /**
     * Register a new task.
     *
     * @param task the task to register
     *
     * @throws IOException if there is an error communicating
     */
    public void registerTask(Task task) throws IOException {
        Gson gson = new Gson();
        String registerTaskResult = postData(baseUrl + "/tasks", gson.toJson(task), true);

        log.debug( "register task result is \"" + registerTaskResult + "\"");
    }

    /**
     * Return a task for a given task name.
     *
     * @param taskName the name of the task to retrieve
     *
     * @return the task
     *
     * @throws IOException if there is an error communicating
     */
    public Task getTaskDefinition(String taskName) throws IOException {
        String taskDefinitionString = getData(baseUrl + "/tasks/" + taskName, true);

        Gson gson = new Gson();

        return gson.fromJson(taskDefinitionString, Task.class);
    }

    /**
     * Delete a task.
     *
     * @param taskName the task to delete
     *
     * @throws IOException if there is an error communicating
     */
    public void deleteTask(String taskName) throws IOException {
        String deleteUrl = baseUrl + "/tasks/" + taskName;

        ErrorMessage errorMessage = delete(deleteUrl, true);

        if( errorMessage != null )
            log.debug( "delete task error is \"" + errorMessage.toString() + "\"");
    }

    /**
     * Get a list of workflow ids
     *
     * @return the workflow id's as strings
     *
     * @throws IOException if there is an error communicating
     */
    public List<String> listWorkflows() throws IOException {
        String workflowsString = getData(baseUrl + "/workflows", true);
        Gson gson = new Gson();

        WorkflowList workflowList = gson.fromJson(workflowsString, WorkflowList.class);

        return workflowList.getWorkflows();
    }

    /**
     * Create and submit a workflow
     *
     * @param workflow the workflow to submit
     *
     * @throws IOException if there is an error communicating
     */
    public void submitWorkflow(Workflow workflow) throws IOException {
        Gson gson = new Gson();
        String submitWorkflowResult = postData(baseUrl + "/workflows", gson.toJson(workflow), true);

        log.debug( "submit workflow result is \"" + submitWorkflowResult + "\"");
    }

    /**
     * Get the status of a workflow
     *
     * @param workflowId the workflow to submit
     *
     * @throws IOException if there is an error communicating
     */
    public Workflow getWorkflowStatus(String workflowId) throws IOException {
        String getUrl = baseUrl + "/workflows/" + workflowId;

        String workflowStatusResult = getData(getUrl, true);

        Gson gson = new Gson();

        return gson.fromJson(workflowStatusResult, Workflow.class);
    }


}
