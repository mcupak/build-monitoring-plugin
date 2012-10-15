package org.jenkinsci.plugins.buildanalysis.model;

import hudson.model.ParameterValue;
import hudson.model.Result;
import hudson.model.Cause;

import java.util.Date;
import java.util.List;
import java.util.Map;

public class BuildInfo {
    
    private int number;
    private String name;
    private String className;
    private String jdkName;
    private String label;
    private Date scheduledTime;
    private Date startedTime;
    private Date finishedTime;
    private Result result;
    private String buildOn;
    private List<Cause> triggerCauses;
    private Map<String,String> parameters;
    
    public BuildInfo(int number, String name){
        this.number = number;
        this.name = name;
    }
    
    public BuildInfo(int number, String name, Date scheduledTime, Date startedTime){
        this.number = number;
        this.name = name;
        this.scheduledTime = scheduledTime;
        this.startedTime = startedTime;
    }
    
    
    public int getNumber() {
        return number;
    }

    public String getName() {
        return name;
    }
    
    public String getJdkName() {
        return jdkName;
    }
    
    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public void setJdkName(String jdkName) {
        this.jdkName = jdkName;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public Date getScheduledTime() {
        return scheduledTime;
    }

    public void setScheduledTime(Date scheduledTime) {
        this.scheduledTime = scheduledTime;
    }

    public Date getStartedTime() {
        return startedTime;
    }

    public void setStartedTime(Date startedTime) {
        this.startedTime = startedTime;
    }

    public Date getFinishedTime() {
        return finishedTime;
    }

    public void setFinishedTime(Date finishedTime) {
        this.finishedTime = finishedTime;
    }

    public Result getResult() {
        return result;
    }

    public void setResult(Result result) {
        this.result = result;
    }

    public String getBuildOn() {
        return buildOn;
    }

    public void setBuildOn(String buildOn) {
        this.buildOn = buildOn;
    }

    public List<Cause> getTriggerCauses() {
        return triggerCauses;
    }

    public void setTriggerCauses(List<Cause> triggerCauses) {
        this.triggerCauses = triggerCauses;
    }

    public Map<String,String> getParameters() {
        return parameters;
    }

    public void setParameters(Map<String,String> parameters) {
        this.parameters = parameters;
    }

    
    
}