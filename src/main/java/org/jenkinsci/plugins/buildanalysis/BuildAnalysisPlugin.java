package org.jenkinsci.plugins.buildanalysis;

import hudson.Plugin;

import java.net.UnknownHostException;

import jenkins.model.Jenkins;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.jenkinsci.plugins.buildanalysis.BuildAnalysis.BuildAnalysisDescriptor;
import org.jenkinsci.plugins.buildanalysis.dao.BuildDAO;
import org.jenkinsci.plugins.buildanalysis.dao.DAOFactory;
import org.jenkinsci.plugins.buildanalysis.dao.DbConfig;
import org.jenkinsci.plugins.buildanalysis.dao.GlobalDAO;
import org.jenkinsci.plugins.buildanalysis.dao.QueueDAO;
import org.kohsuke.stapler.StaplerRequest;
import org.kohsuke.stapler.StaplerResponse;

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

}
