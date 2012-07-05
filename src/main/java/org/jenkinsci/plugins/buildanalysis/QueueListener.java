package org.jenkinsci.plugins.buildanalysis;

import hudson.Extension;
import hudson.model.Action;
import hudson.model.AbstractProject;
import hudson.model.Queue.QueueDecisionHandler;
import hudson.model.Queue.Task;

import java.util.Date;
import java.util.List;

import org.jenkinsci.plugins.buildanalysis.dao.BuildDAO;
import org.jenkinsci.plugins.buildanalysis.dao.DAOFactory;

@Extension
public class QueueListener extends QueueDecisionHandler {

    private final BuildDAO buildDAO;
    
    public QueueListener() throws Exception {
        this.buildDAO = DAOFactory.getDAOFactory().getBuildDAO();
    }
    
    public boolean shouldSchedule(Task p, List<Action> actions) {
        BuildInfo buildInfo = new BuildInfo(((AbstractProject)p).getNextBuildNumber(), p.getDisplayName());
        buildInfo.setScheduledTime(new Date(System.currentTimeMillis()));
        buildDAO.create(buildInfo);
        return true;
    }
}
