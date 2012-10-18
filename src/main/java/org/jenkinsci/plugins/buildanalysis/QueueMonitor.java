package org.jenkinsci.plugins.buildanalysis;

import hudson.Extension;
import hudson.model.PeriodicWork;
import hudson.model.AbstractProject;
import hudson.model.Queue;
import hudson.model.Queue.Item;
import hudson.model.Queue.Task;

import java.util.ArrayList;
import java.util.List;

import jenkins.model.Jenkins;

import org.jenkinsci.plugins.buildanalysis.BuildAnalysis.BuildAnalysisDescriptor;
import org.jenkinsci.plugins.buildanalysis.dao.DAOFactory;
import org.jenkinsci.plugins.buildanalysis.dao.DbConfig;
import org.jenkinsci.plugins.buildanalysis.dao.QueueDAO;
import org.jenkinsci.plugins.buildanalysis.model.QueueInfo;
import org.jenkinsci.plugins.buildanalysis.model.QueueItemInfo;
import org.jenkinsci.plugins.buildanalysis.utils.BuildUtils;

@Extension
public class QueueMonitor extends PeriodicWork {

	//TODO make it configurable via Aperiodic work?
    private static final int PERIOD_MINUTES = 1;
    
    private final QueueDAO queueDAO;
    
    public QueueMonitor() throws Exception {
    	DbConfig dbConfig = ((BuildAnalysisDescriptor)Jenkins.getInstance().getDescriptor(BuildAnalysis.class)).getDbConfig();
        this.queueDAO = DAOFactory.getDAOFactory(dbConfig).getQueueDAO();
    }
    
    public long getRecurrencePeriod() {
        return PERIOD_MINUTES * MIN;
    }
    
    protected void doRun() throws Exception {
    	QueueInfo qi = new QueueInfo();
    	Queue q = Jenkins.getInstance().getQueue();
    	qi.setQueuesSize(q.getItems().length);
    	qi.setBuildableSize(q.getBuildableItems().size());
    	qi.setPendingSize(q.getPendingItems().size());
    	//TODO blockedProjects
    	//TODO add waiting list
    	//q.getBuildableItems(c)
    	List<QueueItemInfo> qItems = new ArrayList<QueueItemInfo>();
    	for(Item item : q.getItems()) {
    		QueueItemInfo qItem = new QueueItemInfo();
    		Task t = item.task;
    		qItem.setId(item.id);
    		qItem.setName(BuildUtils.getProjectName((AbstractProject<?,?>)t));
    		qItem.setNumber(((AbstractProject<?,?>)t).getNextBuildNumber());
    		qItem.setClassName(((AbstractProject<?,?>)t).getClass().getName());
    		qItem.setParams(item.getParams());
    		qItem.setBlockageCauses(item.getCauseOfBlockage());
    		qItem.setConcurentBuild(t.isConcurrentBuild());
    		qItem.setBuildable(item.isBuildable());
    		qItem.setBlocked(item.isBlocked());
    		qItem.setStucked(item.isStuck());
    	}
    	qi.setQueueList(qItems);
    }
}
