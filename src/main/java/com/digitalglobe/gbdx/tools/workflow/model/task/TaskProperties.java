package com.digitalglobe.gbdx.tools.workflow.model.task;

import javax.annotation.Generated;

import com.google.gson.Gson;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Additional properties of the task.
 *
 */
@Generated("org.jsonschema2pojo")
public class TaskProperties {

    /**
     * The integer of the default timeout to this task (in seconds).
     * (Required)
     *
     */
    @SerializedName("timeout")
    @Expose
    private Integer timeout;
    /**
     * Visibility of the task to other users. Defaults to false.
     *
     */
    @SerializedName("isPublic")
    @Expose
    private Boolean isPublic;
    /**
     * Indicator that the task logic will require GBDX authorization from the running
     * user. If this is true and during workflow invocation 'impersonation_allowed' flag
     * is not set on the task workflow will fail. Defaults to false.
     *
     */
    @SerializedName("authorizationRequired")
    @Expose
    private Boolean authorizationRequired;

    /**
     * The integer of the default timeout to this task (in seconds).
     * (Required)
     *
     * @return
     *     The timeout
     */
    public Integer getTimeout() {
        return timeout;
    }

    /**
     * The integer of the default timeout to this task (in seconds).
     * (Required)
     *
     * @param timeout
     *     The timeout
     */
    public void setTimeout(Integer timeout) {
        this.timeout = timeout;
    }

    /**
     * The integer of the default timeout to this task (in seconds).
     * (Required)
     *
     * @param timeout
     *     The timeout
     * @return Returns a reference to this object so that method calls can be chained together.
     */
    public TaskProperties withTimeout(Integer timeout) {
        this.timeout = timeout;
        return this;
    }

    /**
     * Visibility of the task to other users. Defaults to false.
     *
     * @return
     *     The isPublic
     */
    public Boolean getIsPublic() {
        return isPublic;
    }

    /**
     * Visibility of the task to other users. Defaults to false.
     *
     * @param isPublic
     *     The isPublic
     */
    public void setIsPublic(Boolean isPublic) {
        this.isPublic = isPublic;
    }

    /**
     * Visibility of the task to other users. Defaults to false.
     *
     * @param isPublic
     *     The isPublic
     * @return Returns a reference to this object so that method calls can be chained together.
     */
    public TaskProperties withIsPublic(Boolean isPublic) {
        this.isPublic = isPublic;
        return this;
    }

    /**
     * Indicator that the task logic will require GBDX authorization from the running
     * user. If this is true and during workflow invocation 'impersonation_allowed' flag
     * is not set on the task workflow will fail. Defaults to false.
     *
     * @return
     *     The authorizationRequired
     */
    public Boolean getAuthorizationRequired() {
        return authorizationRequired;
    }

    /**
     * Indicator that the task logic will require GBDX authorization from the running
     * user. If this is true and during workflow invocation 'impersonation_allowed' flag
     * is not set on the task workflow will fail. Defaults to false.
     *
     * @param authorizationRequired
     *     The authorizationRequired
     */
    public void setAuthorizationRequired(Boolean authorizationRequired) {
        this.authorizationRequired = authorizationRequired;
    }

    /**
     * Indicator that the task logic will require GBDX authorization from the running
     * user. If this is true and during workflow invocation 'impersonation_allowed' flag
     * is not set on the task workflow will fail. Defaults to false.
     *
     * @param authorizationRequired
     *     The authorizationRequired
     * @return Returns a reference to this object so that method calls can be chained together.
     */
    public TaskProperties withAuthorizationRequired(Boolean authorizationRequired) {
        this.authorizationRequired = authorizationRequired;
        return this;
    }

    @Override
    public String toString() {
        Gson gson = new Gson();

        return gson.toJson(this);
    }

}