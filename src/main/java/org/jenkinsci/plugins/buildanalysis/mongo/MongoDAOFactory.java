package org.jenkinsci.plugins.buildanalysis.mongo;

import java.net.UnknownHostException;

import org.jenkinsci.plugins.buildanalysis.dao.BuildDAO;
import org.jenkinsci.plugins.buildanalysis.dao.DAOFactory;
import org.jenkinsci.plugins.buildanalysis.dao.DbConfig;
import org.jenkinsci.plugins.buildanalysis.dao.LabelDAO;
import org.jenkinsci.plugins.buildanalysis.dao.QueueDAO;
import org.jenkinsci.plugins.buildanalysis.dao.SlaveDAO;

import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.Mongo;

public class MongoDAOFactory extends DAOFactory {

	private static final String BUILDS_COLLECTION_NAME = "builds";
	private static final String QUEUE_COLLECTION_NAME = "queue";
	private static final String LABEL_COLLECTION_NAME = "labels";
	private static final String SLAVE_COLLECTION_NAME = "slaves";
	
	private final Mongo mongo;
    private final DB db;
	
	public MongoDAOFactory(DbConfig dbConfig) throws UnknownHostException {
		this.mongo = new Mongo(dbConfig.getHost());
		this.db = mongo.getDB(dbConfig.getDbName());
	}
	
	public BuildDAO getBuildDAO() {
        DBCollection coll = db.getCollection(BUILDS_COLLECTION_NAME);
        return new MongoBuildDAO(coll);
    }
	
    public BuildDAO getBuildDAO(String collectionName) {
        DBCollection coll = db.getCollection(collectionName);
        return new MongoBuildDAO(coll);
    }
    
    public QueueDAO getQueueDAO() {
        DBCollection coll = db.getCollection(QUEUE_COLLECTION_NAME);
        return new MongoQueueDAO(coll);
    }
	
    public QueueDAO getQueueDAO(String collectionName) {
        DBCollection coll = db.getCollection(collectionName);
        return new MongoQueueDAO(coll);
    }
    
    public LabelDAO getLabelDAO() {
        DBCollection coll = db.getCollection(LABEL_COLLECTION_NAME);
        return new MongoLabelDAO(coll);
    }
    
    public LabelDAO getLabelDAO(String collectionName) {
    	DBCollection coll = db.getCollection(collectionName);
        return new MongoLabelDAO(coll);
    }
    
    public SlaveDAO getSlaveDAO() {
    	DBCollection coll = db.getCollection(SLAVE_COLLECTION_NAME);
        return new MongoSlaveDAO(coll);
    }
    
    public SlaveDAO getSlaveDAO(String collectionName) {
    	DBCollection coll = db.getCollection(collectionName);
        return new MongoSlaveDAO(coll);
    }
}
