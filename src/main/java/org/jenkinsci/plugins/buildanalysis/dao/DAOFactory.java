package org.jenkinsci.plugins.buildanalysis.dao;

import org.jenkinsci.plugins.buildanalysis.mongo.MongoDAOFactory;

// TODO switch to Hibernate OGM once technical problems are solved. Then there's no need to DAO at all 
public abstract class DAOFactory {

    public static DAOFactory getDAOFactory(){
        // TODO implement as an extension point and return factory based on configuration
        return new MongoDAOFactory();
    }
    
    public abstract BuildDAO getBuildDAO() throws Exception;
}
