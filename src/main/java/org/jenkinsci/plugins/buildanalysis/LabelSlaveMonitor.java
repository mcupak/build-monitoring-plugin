package org.jenkinsci.plugins.buildanalysis;

import jenkins.model.Jenkins;
import hudson.model.PeriodicWork;

import org.jenkinsci.plugins.buildanalysis.BuildAnalysis.BuildAnalysisDescriptor;
import org.jenkinsci.plugins.buildanalysis.dao.DAOFactory;
import org.jenkinsci.plugins.buildanalysis.dao.DbConfig;
import org.jenkinsci.plugins.buildanalysis.dao.LabelDAO;
import org.jenkinsci.plugins.buildanalysis.dao.SlaveDAO;

public class LabelSlaveMonitor extends PeriodicWork {

	// TODO make it configurable via Aperiodic work?
	private static final int PERIOD_MINUTES = 1;

	private final LabelDAO labelDAO;
	private final SlaveDAO slaveDAO;
	
	public LabelSlaveMonitor() throws Exception {
		DbConfig dbConfig = ((BuildAnalysisDescriptor)Jenkins.getInstance().getDescriptor(BuildAnalysis.class)).getDbConfig();
        this.labelDAO = DAOFactory.getDAOFactory(dbConfig).getLabelDAO();
        this.slaveDAO = DAOFactory.getDAOFactory(dbConfig).getSlaveDAO();
	}
	
	public long getRecurrencePeriod() {
        return PERIOD_MINUTES * MIN;
    }
    
    protected void doRun() throws Exception {
    	
    }

}
