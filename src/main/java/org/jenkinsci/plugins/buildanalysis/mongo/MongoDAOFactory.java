package org.jenkinsci.plugins.buildanalysis.mongo;

import java.net.UnknownHostException;

import org.jenkinsci.plugins.buildanalysis.dao.BuildDAO;
import org.jenkinsci.plugins.buildanalysis.dao.DAOFactory;
import org.jenkinsci.plugins.buildanalysis.dao.DbConfig;
import org.jenkinsci.plugins.buildanalysis.dao.QueueDAO;

import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.Mongo;

public class MongoDAOFactory extends DAOFactory {

	private static final String BUILDS_COLLECTION_NAME = "builds";
	private static final String QUEUE_COLLECTION_NAME = "queue";
	
	private final Mongo mongo;
    private final DB db;
	
	public MongoDAOFactory(DbConfig dbConfig) throws UnknownHostException {
		this.mongo = new Mongo(dbConfig.getHost());
		this.db = mongo.getDB(dbConfig.getDbName());
	}
	
	public BuildDAO getBuildDAO() throws UnknownHostException {
        DBCollection coll = db.getCollection(BUILDS_COLLECTION_NAME);
        return new MongoBuildDAO(coll);
    }
	
    public BuildDAO getBuildDAO(String collectionName) throws UnknownHostException {
        DBCollection coll = db.getCollection(collectionName);
        return new MongoBuildDAO(coll);
    }
    
    public QueueDAO getQueueDAO() throws UnknownHostException {
        DBCollection coll = db.getCollection(QUEUE_COLLECTION_NAME);
        return new MongoQueueDAO(coll);
    }
	
    public QueueDAO getQueueDAO(String collectionName) throws UnknownHostException {
        DBCollection coll = db.getCollection(collectionName);
        return new MongoQueueDAO(coll);
    }
}
