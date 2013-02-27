package org.jenkinsci.plugins.buildanalysis.monitor;

import hudson.Extension;
import hudson.model.Action;
import hudson.model.AbstractProject;
import hudson.model.Queue.QueueDecisionHandler;
import hudson.model.Queue.Task;

import java.net.UnknownHostException;
import java.util.Date;
import java.util.List;

import jenkins.model.Jenkins;

import org.jenkinsci.plugins.buildanalysis.BuildAnalysis;
import org.jenkinsci.plugins.buildanalysis.BuildAnalysis.BuildAnalysisDescriptor;
import org.jenkinsci.plugins.buildanalysis.dao.BuildDAO;
import org.jenkinsci.plugins.buildanalysis.dao.DAOFactory;
import org.jenkinsci.plugins.buildanalysis.dao.DbConfig;
import org.jenkinsci.plugins.buildanalysis.dao.GlobalDAO;
import org.jenkinsci.plugins.buildanalysis.dao.MonitorDAO;
import org.jenkinsci.plugins.buildanalysis.model.BuildInfo;
import org.jenkinsci.plugins.buildanalysis.utils.BuildUtils;
import org.jenkinsci.plugins.buildanalysis.utils.MonitorUtils;

@Extension
public class QueueListener extends QueueDecisionHandler implements Monitor {
	
    private BuildDAO buildDAO;
    
    public QueueListener() throws UnknownHostException {
        this.buildDAO = MonitorUtils.getDaoFactory().getBuildDAO();
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
    
}
