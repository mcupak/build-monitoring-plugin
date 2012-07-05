package org.jenkinsci.plugins.buildanalysis.dao;

import org.jenkinsci.plugins.buildanalysis.mongo.MongoDAOFactory;

public abstract class DAOFactory {

    public static DAOFactory getDAOFactory(){
        // TODO implement as an extension point
        return new MongoDAOFactory();
    }
    
    public abstract BuildDAO getBuildDAO() throws Exception;
}
