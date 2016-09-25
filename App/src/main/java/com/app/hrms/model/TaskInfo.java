package com.app.hrms.model;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;

public class TaskInfo implements Serializable{
    public String id;
    public int count;
    public int rows;
    public int skip;
    public String completeState;
    public String excuteDetail;
    public String excuteMember;
    public String excuteName;
    public String excutePlanState;
    public String excuteState;
    public String fromMember;
    public String fromName;
    public String taskCompleteDate;
    public String taskDetails;
    public String taskId;
    public String taskRegulationDate;
    public String taskStartDate;
    public String taskTheme;

    public TaskInfo(JSONObject json)throws JSONException{
        this.id = json.getString("id");
        this.count = json.getInt("count");
        this.rows = json.getInt("rows");
        this.skip = json.getInt("skip");
        this.fromMember = json.getString("fromMember");
        this.fromName = json.getString("fromName");
        this.excuteMember = json.getString("excuteMember");
        this.excuteName = json.getString("excuteName");
        this.excuteDetail = json.getString("excuteDetail");
        this.excutePlanState = json.getString("excutePlanState");
        this.excuteState = json.getString("excuteState");
        this.completeState = json.getString("completeState");
        this.taskCompleteDate = json.getString("taskCompleteDate");
        this.taskDetails = json.getString("taskDetails");
        this.taskId = json.getString("taskId");
        this.taskRegulationDate = json.getString("taskRegulationDate");
        this.taskStartDate = json.getString("taskStartDate");
        this.taskTheme = json.getString("taskTheme");
    }
}
