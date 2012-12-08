package org.jenkinsci.plugins.buildanalysis.model;

import hudson.model.Result;
import hudson.model.Cause;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Information about one single build
 * 
 * @author vjuranek
 *
 */
public class BuildInfo {
    
    private String id;
    
    /**
     * Build number
     */
    private int number;
    
    /**
     * Build name
     */
    private String name;
    
    /**
     * Type of the job (clas name of job - e.g. freestyle, matrix etc.)
     */
    private String className;
    
    /**
     * JDK required by this build
     */
    private String jdkName;
    
    /**
     * Label required by this build
     */
    private String label;
    
    /**
     * Time when the build was scheduled (i.e. put in the queue)
     */
    private Date scheduledTime;
    
    /**
     * Time when the build was actually started 
     */
    private Date startedTime;
    
    /**
     * Time when the build finished
     */
    private Date finishedTime;
    
    /**
     * Result of the build (success, unstable, fail)
     */
    private Result result;
    
    /**
     * Name of the slave where the build run
     */
    private String buildOn;
    
    /**
     * Who/what started the build
     */
    private List<Cause> triggerCauses;
    
    /**
     * Parameters the build was started with
     */
    private Map<String,String> parameters;
    
    public BuildInfo(String name, int number){
        this.name = name;
        this.number = number;
    }
    
    public BuildInfo(int number, String name, Date scheduledTime, Date startedTime){
        this.number = number;
        this.name = name;
        this.scheduledTime = scheduledTime;
        this.startedTime = startedTime;
    }
    
    
    public String getId() {
    	return id;
    }
    
    public void setId(String id) {
    	this.id = id;
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
