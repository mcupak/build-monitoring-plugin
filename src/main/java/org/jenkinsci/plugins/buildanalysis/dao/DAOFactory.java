package org.jenkinsci.plugins.buildanalysis.dao;

import java.net.UnknownHostException;

import org.jenkinsci.plugins.buildanalysis.mongo.MongoDAOFactory;

// TODO switch to Hibernate OGM once technical problems are solved. Then there's no need to DAO at all 
public abstract class DAOFactory {
	
    public static DAOFactory getDAOFactory(DbConfig dbConfig) throws UnknownHostException {
        // TODO implement as an extension point and return factory based on configuration
        return new MongoDAOFactory(dbConfig);
    }
    
    public abstract BuildDAO getBuildDAO();
    public abstract BuildDAO getBuildDAO(String collectionName);
    
    public abstract QueueDAO getQueueDAO();
    public abstract QueueDAO getQueueDAO(String collectionName);
    
    public abstract LabelsDAO getLabelsDAO();
    public abstract LabelsDAO getLabelsDAO(String collectionName);
    
    public abstract SlavesDAO getSlavesDAO();
    public abstract SlavesDAO getSlavesDAO(String collectionName);
}
