package org.jenkinsci.plugins.buildanalysis.utils;

import hudson.ExtensionList;
import jenkins.model.Jenkins;

import org.jenkinsci.plugins.buildanalysis.BuildAnalysis;
import org.jenkinsci.plugins.buildanalysis.BuildAnalysis.BuildAnalysisDescriptor;
import org.jenkinsci.plugins.buildanalysis.dao.DAOFactory;
import org.jenkinsci.plugins.buildanalysis.dao.DbConfig;
import org.jenkinsci.plugins.buildanalysis.dao.DummyDAOFactory;

public class MonitorUtils {

    public static <T> void enable(T monitor, ExtensionList<T> extensionList) {
        if(!extensionList.contains(monitor))
            extensionList.add(monitor);
    }
    
    public static <T> void disable(T monitor, ExtensionList<T> extensionList) {
        if(extensionList.contains(monitor))
            extensionList.remove(monitor);
    }
    
    /**
     * 
     * @return {@link DAOFactory} based on current {@link DbConfig}. Never return null.
     */
    public static DAOFactory getDaoFactory() {
        DbConfig dbConfig = ((BuildAnalysisDescriptor)Jenkins.getInstance().getDescriptor(BuildAnalysis.class)).getDbConfig();
        DAOFactory factory = DAOFactory.getDAOFactory(dbConfig);
        if(factory == null)
            factory = new DummyDAOFactory();
        return factory;
    }
    
    /*public static <T extends Monitor> void load(T monitor) {
        DbConfig dbConfig = ((BuildAnalysisDescriptor)Jenkins.getInstance().getDescriptor(BuildAnalysis.class)).getDbConfig();
        if(dbConfig == null) {
            monitor.setMonitorDAO(null);
            return;
        }
        //monitor.setMonitorDAO(DAOFactory.getDAOFactory(dbConfig).getGlobalDAO());
    }*/
    
}
