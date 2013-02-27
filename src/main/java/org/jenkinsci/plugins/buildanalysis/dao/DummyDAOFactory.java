package org.jenkinsci.plugins.buildanalysis.dao;

/**
 * Dummy implementation of {@link DAOFactory}, return null for all DAOs
 * 
 * @author vjuranek
 *
 */
public class DummyDAOFactory extends DAOFactory {
    
    public GlobalDAO getGlobalDAO() {
        return null;
    }
    
    public GlobalDAO getGlobalDAO(String collectionName) {
        return null;
    }
    
    public BuildDAO getBuildDAO() {
        return null;
    }
    
    public BuildDAO getBuildDAO(String collectionName) {
        return null;
    }
    
    public QueueDAO getQueueDAO() {
        return null;
    }
    
    public QueueDAO getQueueDAO(String collectionName) {
        return null;
    }
    
    public LabelsDAO getLabelsDAO() {
        return null;
    }
    
    public LabelsDAO getLabelsDAO(String collectionName) {
        return null;
    }
    
    public SlavesDAO getSlavesDAO() {
        return null;
    }
    
    public SlavesDAO getSlavesDAO(String collectionName) {
        return null;
    }

}
