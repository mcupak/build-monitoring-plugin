package org.jenkinsci.plugins.buildanalysis.monitor;

import hudson.Extension;
import hudson.model.PeriodicWork;
import hudson.model.AbstractProject;
import hudson.model.Queue;
import hudson.model.Queue.Item;
import hudson.model.Queue.Task;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Logger;

import jenkins.model.Jenkins;

import org.jenkinsci.plugins.buildanalysis.dao.DAOFactory;
import org.jenkinsci.plugins.buildanalysis.dao.QueueDAO;
import org.jenkinsci.plugins.buildanalysis.model.QueueInfo;
import org.jenkinsci.plugins.buildanalysis.model.QueueItemInfo;
import org.jenkinsci.plugins.buildanalysis.utils.BuildUtils;
import org.jenkinsci.plugins.buildanalysis.utils.MonitorUtils;

@Extension
public class QueueMonitor extends PeriodicWork implements Monitor {

    //TODO make it configurable via Aperiodic work?
    private static final int PERIOD_MINUTES = 1;

    private QueueDAO queueDAO;
    private MonitorStatus status;

    public QueueMonitor() {
        init();
    }

    public long getRecurrencePeriod() {
        return PERIOD_MINUTES * MIN;
    }

    protected void doRun() throws Exception {
        if (this.queueDAO == null && status == MonitorStatus.RUNNING) {
            init();
            if (status == MonitorStatus.RUNNING) {
                LOGGER.warning("Disabling Queue monitor, check other log recored for details");
                disable();
                status = MonitorStatus.FAILED;
                return;
            }
        }

        QueueInfo qi = new QueueInfo(new Date(System.currentTimeMillis()));
        Queue q = Jenkins.getInstance().getQueue();
        qi.setQueueSize(q.getItems().length);
        qi.setBuildableSize(q.getBuildableItems().size());
        qi.setPendingSize(q.getPendingItems().size());
    	//TODO blockedProjects
        //TODO add waiting list
        //q.getBuildableItems(c)
        List<QueueItemInfo> qItems = new ArrayList<QueueItemInfo>();
        for (Item item : q.getItems()) {
            QueueItemInfo qItem = new QueueItemInfo();
            Task t = item.task;
            qItem.setId(item.id);
            qItem.setName(BuildUtils.getProjectName((AbstractProject<?, ?>) t));
            qItem.setNumber(((AbstractProject<?, ?>) t).getNextBuildNumber());
            qItem.setClassName(((AbstractProject<?, ?>) t).getClass().getName());
            qItem.setParams(item.getParams());
            qItem.setBlockageCause(item.getCauseOfBlockage());
            qItem.setConcurentBuild(t.isConcurrentBuild());
            qItem.setBuildable(item.isBuildable());
            qItem.setBlocked(item.isBlocked());
            qItem.setStucked(item.isStuck());
            qItems.add(qItem);
        }
        qi.setQueueList(qItems);

        try {
            queueDAO.create(qi); //TODO think if better to store new record every time or do some update or store e.g. some delta
        } catch (Exception e) {
            LOGGER.warning("Cannot create a queue info record: " + e.getMessage());
        }
    }

    public void enable() {
        MonitorUtils.enable(this, all());
    }

    public void disable() {
        MonitorUtils.disable(this, all());
    }

    private void init() {
        try {
            this.queueDAO = MonitorUtils.getDaoFactory().getQueueDAO();
            this.status = MonitorStatus.RUNNING;
        } catch (Exception e) {
            this.queueDAO = null;
            this.status = MonitorStatus.FAILED;
        }
    }

    private static final Logger LOGGER = Logger.getLogger(DAOFactory.class.getName());

}
