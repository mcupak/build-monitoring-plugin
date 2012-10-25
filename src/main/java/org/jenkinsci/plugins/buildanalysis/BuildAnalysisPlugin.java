package org.jenkinsci.plugins.buildanalysis;

import hudson.Plugin;

import java.net.UnknownHostException;

import jenkins.model.Jenkins;
import net.sf.json.JSONObject;

import org.jenkinsci.plugins.buildanalysis.BuildAnalysis.BuildAnalysisDescriptor;
import org.jenkinsci.plugins.buildanalysis.dao.BuildDAO;
import org.jenkinsci.plugins.buildanalysis.dao.DAOFactory;
import org.jenkinsci.plugins.buildanalysis.dao.DbConfig;
import org.jenkinsci.plugins.buildanalysis.dao.GlobalDAO;
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
	
	
	public JSONObject getTestSeries() throws UnknownHostException {
		DbConfig dbConfig = ((BuildAnalysisDescriptor)Jenkins.getInstance().getDescriptor(BuildAnalysis.class)).getDbConfig();
		GlobalDAO globalDAO = DAOFactory.getDAOFactory(dbConfig).getGlobalDAO();
        return globalDAO.getSeries();
	}

}
