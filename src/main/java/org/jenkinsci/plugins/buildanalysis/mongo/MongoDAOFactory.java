package org.jenkinsci.plugins.buildanalysis.mongo;

import java.net.UnknownHostException;

import org.jenkinsci.plugins.buildanalysis.dao.BuildDAO;
import org.jenkinsci.plugins.buildanalysis.dao.DAOFactory;
import org.jenkinsci.plugins.buildanalysis.dao.DbConfig;

import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.Mongo;

public class MongoDAOFactory extends DAOFactory {

	private final Mongo mongo;
    private final DB db;
	
	public MongoDAOFactory(DbConfig dbConfig) throws UnknownHostException {
		this.mongo = new Mongo(dbConfig.getHost());
		this.db = mongo.getDB(dbConfig.getDbName());
	}
	
    public BuildDAO getBuildDAO(String collectionName) throws UnknownHostException {
        DBCollection coll = db.getCollection(collectionName);
        return new MongoBuildDAO(coll);
    }
}
