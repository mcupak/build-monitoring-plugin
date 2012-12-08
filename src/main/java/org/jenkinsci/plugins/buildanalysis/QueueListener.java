package org.jenkinsci.plugins.buildanalysis;

import hudson.Extension;
import hudson.model.Action;
import hudson.model.AbstractProject;
import hudson.model.Queue.QueueDecisionHandler;
import hudson.model.Queue.Task;

import java.net.UnknownHostException;
import java.util.Date;
import java.util.List;

import jenkins.model.Jenkins;

import org.jenkinsci.plugins.buildanalysis.BuildAnalysis.BuildAnalysisDescriptor;
import org.jenkinsci.plugins.buildanalysis.dao.BuildDAO;
import org.jenkinsci.plugins.buildanalysis.dao.DAOFactory;
import org.jenkinsci.plugins.buildanalysis.dao.DbConfig;
import org.jenkinsci.plugins.buildanalysis.model.BuildInfo;
import org.jenkinsci.plugins.buildanalysis.utils.BuildUtils;
import org.jenkinsci.plugins.buildanalysis.utils.MonitorUtils;

@Extension
public class QueueListener extends QueueDecisionHandler {
	
    private BuildDAO buildDAO;
    
    public QueueListener() throws UnknownHostException {
    	load();
    }
    
    public boolean shouldSchedule(Task p, List<Action> actions) {
        if(buildDAO == null) {
            //all().remove(this); // db is not set up, cannot record anything
            return true;
        }
        BuildInfo buildInfo = new BuildInfo(BuildUtils.getProjectName((AbstractProject<?,?>)p), ((AbstractProject<?,?>)p).getNextBuildNumber());
        buildInfo.setScheduledTime(new Date(System.currentTimeMillis()));
        buildDAO.create(buildInfo);
        return true;
    }
    
    public void enable() {
        MonitorUtils.enable(this, all());
    }
    
    public void disable() {
        MonitorUtils.disable(this, all());
    }
    
    public void load() throws UnknownHostException {
        DbConfig dbConfig = ((BuildAnalysisDescriptor)Jenkins.getInstance().getDescriptor(BuildAnalysis.class)).getDbConfig();
        if(dbConfig == null) {
            this.buildDAO = null;
            return;
        }
        this.buildDAO = DAOFactory.getDAOFactory(dbConfig).getBuildDAO();
    }
 
}
