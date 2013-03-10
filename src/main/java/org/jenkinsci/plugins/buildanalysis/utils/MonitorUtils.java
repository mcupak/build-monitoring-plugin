package org.jenkinsci.plugins.buildanalysis.utils;

import hudson.ExtensionList;
import hudson.ExtensionPoint;
import jenkins.model.Jenkins;

import org.jenkinsci.plugins.buildanalysis.BuildAnalysis;
import org.jenkinsci.plugins.buildanalysis.BuildAnalysis.BuildAnalysisDescriptor;
import org.jenkinsci.plugins.buildanalysis.dao.DAOFactory;
import org.jenkinsci.plugins.buildanalysis.dao.DbConfig;
import org.jenkinsci.plugins.buildanalysis.dao.DummyDAOFactory;
import org.jenkinsci.plugins.buildanalysis.monitor.BuildListener;
import org.jenkinsci.plugins.buildanalysis.monitor.Monitor;
import org.jenkinsci.plugins.buildanalysis.monitor.MonitorStatus;

public class MonitorUtils {

    public static <T> void enable(T monitor, ExtensionList<T> extensionList) {
        if(!extensionList.contains(monitor))
            extensionList.add(0, monitor);
    }
    
    public static <T extends ExtensionPoint, U extends T> boolean enable(String className) {
        try {
            Class<T> monitorClass = (Class<T>)Class.forName(className);
            ExtensionList<T> el = (ExtensionList<T>)Jenkins.getInstance().getExtensionList(monitorClass.getSuperclass());
            for(T c : el)
                if(c.getClass() == monitorClass) {
                    System.out.println("Class tady je " + c.getClass());
                    return true;
                }
            System.out.println("Pridavam monitor");
            el.add(0, (T)monitorClass.newInstance());
        } catch(Exception e) {
            System.out.println("EXCEPTION");
            e.printStackTrace();
            return false;
        }
        return true;
    }
    
    public static <T> void disable(T monitor, ExtensionList<T> extensionList) {
        if(extensionList.contains(monitor))
            extensionList.remove(monitor);
    }
    
    public static <T extends ExtensionPoint, U extends T> boolean disable(String className) {
        try {
            Class<U> monitorClass = (Class<U>)Class.forName(className);
            ExtensionList<T> el = (ExtensionList<T>)Jenkins.getInstance().getExtensionList(monitorClass.getSuperclass());
            System.out.println("EL SIZE: " + el.size());
            for(T c : el)
                if(c.getClass() == monitorClass) 
                    el.remove(c);
            System.out.println("EL SIZE AFTER: " + Jenkins.getInstance().getExtensionList(className).size());
        } catch(Exception e) {
            return false;
        }
        return true;
    }
    
    /*public static <T extends Monitor> MonitorStatus isEnabled(T monitor) {
        @SuppressWarnings("unchecked")
        ExtensionList<T> extensionList = (ExtensionList<T>)Jenkins.getInstance().getExtensionList(monitor.getClass().getSuperclass());
        return isEnabled(extensionList.get(monitor.getClass()));
    }*/
    
    public static <T extends Monitor> MonitorStatus isEnabled(Class<T> monitorClass) {
        ExtensionList<T> extensionList = (ExtensionList<T>)Jenkins.getInstance().getExtensionList(monitorClass.getSuperclass());
        //return isEnabled(extensionList.get(monitorClass));
        if(extensionList.get(monitorClass) != null)
            return MonitorStatus.RUNNING;
        return MonitorStatus.STOPPED;
    }
    
    /*public static <T extends Monitor> MonitorStatus isEnabled(String monitorStr) {
        try {
            @SuppressWarnings("unchecked")
            ExtensionList<T> extensionList = (ExtensionList<T>)Jenkins.getInstance().getExtensionList(monitorStr);
            return isEnabled(extensionList);
        } catch(ClassNotFoundException e) {
            return MonitorStatus.UNKNOWN;
        }
    }*/
    
    private static <T extends Monitor> MonitorStatus isEnabled(ExtensionList<T> extensionList) {
        if(extensionList.size() > 0) {
            System.out.println("extension point: " + extensionList.get(0).toString() + ", list size is " + extensionList.size());
            return MonitorStatus.RUNNING;
        }
        return MonitorStatus.STOPPED;
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
