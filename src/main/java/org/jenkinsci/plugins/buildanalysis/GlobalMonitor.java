package org.jenkinsci.plugins.buildanalysis;

import hudson.Extension;
import hudson.model.Item;
import hudson.model.PeriodicWork;
import hudson.model.AbstractProject;
import hudson.model.Node;
import hudson.model.Slave;

import java.net.UnknownHostException;
import java.util.Date;

import jenkins.model.Jenkins;

import org.jenkinsci.plugins.buildanalysis.BuildAnalysis.BuildAnalysisDescriptor;
import org.jenkinsci.plugins.buildanalysis.dao.DAOFactory;
import org.jenkinsci.plugins.buildanalysis.dao.DbConfig;
import org.jenkinsci.plugins.buildanalysis.dao.GlobalDAO;
import org.jenkinsci.plugins.buildanalysis.model.GlobalInfo;

@Extension
public class GlobalMonitor extends PeriodicWork {

    private static final int PERIOD_MINUTES = 1;
    
    private final GlobalDAO globalDao;
    
    public GlobalMonitor() throws UnknownHostException {
    	DbConfig dbConfig = ((BuildAnalysisDescriptor)Jenkins.getInstance().getDescriptor(BuildAnalysis.class)).getDbConfig();
        this.globalDao = DAOFactory.getDAOFactory(dbConfig).getGlobalDAO();
    }
    
    public long getRecurrencePeriod() {
        return PERIOD_MINUTES * 60 * 1000L;
    }
    
    protected void doRun() throws Exception {
        GlobalInfo globalInfo = new GlobalInfo(new Date(System.currentTimeMillis()));
        Jenkins j = Jenkins.getInstance();
        
        globalInfo.setNumberOfProjetcs(j.getItems().size());
        
        int builds = 0;
        for(Item i : j.getItems()) {
        	if(i instanceof AbstractProject<?,?>)
        		builds += ((AbstractProject<?,?>) i).getBuilds().size();
        }
        globalInfo.setNumberOfBuilds(builds);
        
        globalInfo.setNumberOfSlaves(j.getNodes().size());
        
        int offlineNodes = 0;
        int idleNodes = 0;
        for(Node n : j.getNodes()) {
        	if(((Slave)n).getComputer().isOffline())
        		offlineNodes++;
        	if(((Slave)n).getComputer().isIdle())
        		idleNodes++;
        }
        globalInfo.setNumberOfOfflineSlaves(offlineNodes);
        globalInfo.setNumberOfIdleSlaves(idleNodes);
        
        globalDao.create(globalInfo);
    }
}

