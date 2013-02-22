package org.jenkinsci.plugins.buildanalysis.monitor;

import hudson.Extension;
import hudson.model.Item;
import hudson.model.PeriodicWork;
import hudson.model.AbstractProject;
import hudson.model.Node;
import hudson.model.Slave;

import java.net.UnknownHostException;
import java.util.Date;

import jenkins.model.Jenkins;

import org.jenkinsci.plugins.buildanalysis.BuildAnalysis;
import org.jenkinsci.plugins.buildanalysis.BuildAnalysis.BuildAnalysisDescriptor;
import org.jenkinsci.plugins.buildanalysis.dao.DAOFactory;
import org.jenkinsci.plugins.buildanalysis.dao.DbConfig;
import org.jenkinsci.plugins.buildanalysis.dao.GlobalDAO;
import org.jenkinsci.plugins.buildanalysis.model.GlobalInfo;
import org.jenkinsci.plugins.buildanalysis.utils.MonitorUtils;

@Extension
public class GlobalMonitor extends PeriodicWork {

    private static final int PERIOD_MINUTES = 1;
    
    private GlobalDAO globalDao;
    
    public GlobalMonitor() throws UnknownHostException {
    	load();
    }
    
    public long getRecurrencePeriod() {
        return PERIOD_MINUTES * 60 * 1000L;
    }
    
    protected void doRun() throws Exception {
        if(globalDao == null) {
            //all().remove(this); // db is not set up, cannot record anything
            return;
        }
        
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
    
    public void enable() {
        MonitorUtils.enable(this, all());
    }
    
    public void disable() {
        MonitorUtils.disable(this, all());
    }
    
    public void load()  throws UnknownHostException {
        DbConfig dbConfig = ((BuildAnalysisDescriptor)Jenkins.getInstance().getDescriptor(BuildAnalysis.class)).getDbConfig();
        if(dbConfig == null) {
            this.globalDao = null;
            return;
        }
        this.globalDao = DAOFactory.getDAOFactory(dbConfig).getGlobalDAO();
    }

}

