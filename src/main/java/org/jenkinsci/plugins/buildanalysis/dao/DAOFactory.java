package org.jenkinsci.plugins.buildanalysis.dao;

import java.util.logging.Level;
import java.util.logging.Logger;

import org.jenkinsci.plugins.buildanalysis.mongo.MongoDAOFactory;
import org.jenkinsci.plugins.buildanalysis.mongo.MongoDB;

// TODO switch to Hibernate OGM once technical problems are solved. Then there's no need to have DAO at all 
public abstract class DAOFactory {

    /**
     *
     * @param dbConfig
     * @return DAOFactory for creating DOA objects.
     */
    public static DAOFactory getDAOFactory(DbConfig dbConfig) {
        // TODO implement as an extension point and return factory based on configuration

        DAOFactory factory = null;
        try {
            //TODO select implementation base on dbConfig
            factory = new MongoDAOFactory(MongoDB.getInstance());
        } catch (Exception e) {
            LOGGER.log(Level.WARNING, "Failed to create DAO factory for Mongo DB", e);
        }
        return factory;
    }

    public abstract GlobalDAO getGlobalDAO();

    public abstract GlobalDAO getGlobalDAO(String collectionName);

    public abstract BuildDAO getBuildDAO();

    public abstract BuildDAO getBuildDAO(String collectionName);

    public abstract QueueDAO getQueueDAO();

    public abstract QueueDAO getQueueDAO(String collectionName);

    public abstract LabelsDAO getLabelsDAO();

    public abstract LabelsDAO getLabelsDAO(String collectionName);

    public abstract SlavesDAO getSlavesDAO();

    public abstract SlavesDAO getSlavesDAO(String collectionName);

    private static final Logger LOGGER = Logger.getLogger(DAOFactory.class.getName());
}
