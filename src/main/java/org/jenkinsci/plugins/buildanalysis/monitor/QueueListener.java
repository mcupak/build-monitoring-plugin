package org.jenkinsci.plugins.buildanalysis.monitor;

import hudson.Extension;
import hudson.model.Action;
import hudson.model.AbstractProject;
import hudson.model.Queue.QueueDecisionHandler;
import hudson.model.Queue.Task;

import java.util.Date;
import java.util.List;
import java.util.logging.Logger;

import org.jenkinsci.plugins.buildanalysis.dao.BuildDAO;
import org.jenkinsci.plugins.buildanalysis.dao.DAOFactory;
import org.jenkinsci.plugins.buildanalysis.model.BuildInfo;
import org.jenkinsci.plugins.buildanalysis.utils.BuildUtils;
import org.jenkinsci.plugins.buildanalysis.utils.MonitorUtils;

@Extension
public class QueueListener extends QueueDecisionHandler implements Monitor {
	
    private BuildDAO buildDAO;
    private MonitorStatus status;
    
    public QueueListener() {
        init();
    }
    
    public boolean shouldSchedule(Task p, List<Action> actions) {
        if(this.buildDAO == null && status == MonitorStatus.RUNNING) {
            init();
            if(status == MonitorStatus.RUNNING) {
                LOGGER.warning("Disabling Queue listener monitor, check other log recored for details");
                disable();
                status = MonitorStatus.FAILED;
                return true;
            }
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
    
    private void init() {
        try {
            this.buildDAO = MonitorUtils.getDaoFactory().getBuildDAO();
            this.status = MonitorStatus.RUNNING;
        } catch(Exception e) {
            this.buildDAO = null;
            this.status = MonitorStatus.FAILED;
        }
    }
    
    private static final Logger LOGGER = Logger.getLogger(DAOFactory.class.getName());
}
