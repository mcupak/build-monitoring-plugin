package org.jenkinsci.plugins.buildanalysis;

import hudson.Plugin;

import java.net.UnknownHostException;
import java.util.HashMap;
import java.util.Map;

import jenkins.model.Jenkins;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.jenkinsci.plugins.buildanalysis.BuildAnalysis.BuildAnalysisDescriptor;
import org.jenkinsci.plugins.buildanalysis.dao.BuildDAO;
import org.jenkinsci.plugins.buildanalysis.dao.DAOFactory;
import org.jenkinsci.plugins.buildanalysis.dao.DbConfig;
import org.jenkinsci.plugins.buildanalysis.dao.GlobalDAO;
import org.jenkinsci.plugins.buildanalysis.dao.QueueDAO;
import org.jenkinsci.plugins.buildanalysis.monitor.BuildListener;
import org.jenkinsci.plugins.buildanalysis.monitor.GlobalMonitor;
import org.jenkinsci.plugins.buildanalysis.monitor.LabelSlaveMonitor;
import org.jenkinsci.plugins.buildanalysis.monitor.MonitorStatus;
import org.jenkinsci.plugins.buildanalysis.monitor.QueueListener;
import org.jenkinsci.plugins.buildanalysis.monitor.QueueMonitor;
import org.jenkinsci.plugins.buildanalysis.utils.MonitorUtils;
import org.kohsuke.stapler.StaplerRequest;
import org.kohsuke.stapler.StaplerResponse;
import org.kohsuke.stapler.bind.JavaScriptMethod;

/**
 * So far dummy class which server static content
 * 
 * @author vjuranek
 *
 */
public class BuildAnalysisPlugin extends Plugin {
	
	public void doQuery(StaplerRequest req, StaplerResponse res) throws UnknownHostException {
        DbConfig dbConfig = ((BuildAnalysisDescriptor)Jenkins.getInstance().getDescriptor(BuildAnalysis.class)).getDbConfig();
        BuildDAO buildDAO = DAOFactory.getDAOFactory(dbConfig).getBuildDAO();
        buildDAO.getBuilds("test-matrix");
	}
	
	
	public JSONObject getGlobalSeries() throws UnknownHostException {
		DbConfig dbConfig = ((BuildAnalysisDescriptor)Jenkins.getInstance().getDescriptor(BuildAnalysis.class)).getDbConfig();
		
		//global statistics
		GlobalDAO globalDAO = DAOFactory.getDAOFactory(dbConfig).getGlobalDAO();
        JSONObject series = globalDAO.getSeries();
        
        //queue length
        QueueDAO queueDAO = DAOFactory.getDAOFactory(dbConfig).getQueueDAO();
        series.put("queueSize", queueDAO.getQueueSizeSerie());
        
        return series;
	}
	
	public JSONArray getBuildTypes() throws UnknownHostException {
		DbConfig dbConfig = ((BuildAnalysisDescriptor)Jenkins.getInstance().getDescriptor(BuildAnalysis.class)).getDbConfig();
		BuildDAO buildDAO = DAOFactory.getDAOFactory(dbConfig).getBuildDAO();
        return buildDAO.getBuildTypes();
	}
	
	public Map<String, MonitorStatus> getMonitorStatus() {
	    Map<String, MonitorStatus> statuses = new HashMap<String, MonitorStatus>();
	    statuses.put(BuildListener.class.getName(), MonitorUtils.isEnabled(BuildListener.class));
	    statuses.put(GlobalMonitor.class.getName(), MonitorUtils.isEnabled(GlobalMonitor.class));
	    statuses.put(LabelSlaveMonitor.class.getName(), MonitorUtils.isEnabled(LabelSlaveMonitor.class));
	    statuses.put(QueueListener.class.getName(), MonitorUtils.isEnabled(QueueListener.class));
	    statuses.put(QueueMonitor.class.getName(), MonitorUtils.isEnabled(QueueMonitor.class));
	    for(Map.Entry<String, MonitorStatus> entry : statuses.entrySet()) {
	        System.out.println("Status of " + entry.getKey() + " is " + entry.getValue());
	    }
	    return statuses;
	}

	@JavaScriptMethod
	public void enableMonitor(String monitorClassStr) {
	    System.out.println("Enableing monitor " + monitorClassStr);
	    MonitorUtils.enable(monitorClassStr);
	}

	@JavaScriptMethod
	public void disableMonitor(String monitorClassStr) {
        System.out.println("Disableing monitor " + monitorClassStr);
        MonitorUtils.disable(monitorClassStr);
    }

}
