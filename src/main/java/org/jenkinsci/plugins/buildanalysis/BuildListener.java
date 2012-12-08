package org.jenkinsci.plugins.buildanalysis;

import hudson.Extension;
import hudson.model.TaskListener;
import hudson.model.AbstractBuild;
import hudson.model.AbstractProject;
import hudson.model.listeners.RunListener;

import java.net.UnknownHostException;
import java.util.Date;

import jenkins.model.Jenkins;

import org.jenkinsci.plugins.buildanalysis.BuildAnalysis.BuildAnalysisDescriptor;
import org.jenkinsci.plugins.buildanalysis.dao.BuildDAO;
import org.jenkinsci.plugins.buildanalysis.dao.DAOFactory;
import org.jenkinsci.plugins.buildanalysis.dao.DbConfig;
import org.jenkinsci.plugins.buildanalysis.model.BuildInfo;
import org.jenkinsci.plugins.buildanalysis.utils.BuildUtils;
import org.jenkinsci.plugins.buildanalysis.utils.MonitorUtils;

@Extension
public class BuildListener extends RunListener<AbstractBuild<?,?>> {
	
    private BuildDAO buildDAO;
    
    public BuildListener() throws UnknownHostException {
    	load();
    }
    
    public void onStarted(AbstractBuild<?,?> build, TaskListener listener) {
        if(buildDAO == null) {
            //unregister(); // db is not set up, cannot record anything
            return;
        }
        
        BuildInfo buildInfo = new BuildInfo(BuildUtils.getJobName(build), build.number);
        buildInfo.setClassName(build.getParent().getClass().getName());
        buildInfo.setStartedTime(build.getTime());
        buildInfo.setJdkName(BuildUtils.getJdkName(build));
        buildInfo.setLabel(((AbstractProject<?,?>)build.getParent()).getAssignedLabelString());
        buildInfo.setBuildOn(BuildUtils.getBuildOn(build));
        buildInfo.setTriggerCauses(build.getCauses());
        buildInfo.setParameters(BuildUtils.getParameters(build));
        buildDAO.updateOnStarted(buildInfo);
    }
    
    public void onFinalized(AbstractBuild<?,?> build) {
        if(buildDAO == null) {
            //unregister(); // db is not set up, cannot record anything
            return;
        }
        
        BuildInfo buildInfo = new BuildInfo(BuildUtils.getJobName(build), build.number);
        buildInfo.setFinishedTime(new Date(System.currentTimeMillis()));
        buildInfo.setResult(build.getResult());
        buildDAO.updateOnFinalized(buildInfo);
    }
    
    public void enable() {
        MonitorUtils.enable(this, all());
    }
    
    public void disable() {
        MonitorUtils.disable(this, all());
    }
    
    public void load() throws UnknownHostException {
        DbConfig dbConfig = ((BuildAnalysisDescriptor)Jenkins.getInstance().getDescriptor(BuildAnalysis.class)).getDbConfig();
        if(dbConfig == null)
            return;
        this.buildDAO = DAOFactory.getDAOFactory(dbConfig).getBuildDAO();
    }

}
