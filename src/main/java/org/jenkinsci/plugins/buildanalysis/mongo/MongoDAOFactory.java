package org.jenkinsci.plugins.buildanalysis.mongo;

import java.net.UnknownHostException;

import org.jenkinsci.plugins.buildanalysis.dao.BuildDAO;
import org.jenkinsci.plugins.buildanalysis.dao.DAOFactory;

public class MongoDAOFactory extends DAOFactory {

    public BuildDAO getBuildDAO() throws UnknownHostException {
        return new MongoBuildDAO();
    }
}
