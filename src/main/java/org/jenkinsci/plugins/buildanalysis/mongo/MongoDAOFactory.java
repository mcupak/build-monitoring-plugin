package org.jenkinsci.plugins.buildanalysis.mongo;

import java.net.UnknownHostException;

import org.jenkinsci.plugins.buildanalysis.dao.BuildDAO;
import org.jenkinsci.plugins.buildanalysis.dao.DAOFactory;
import org.jenkinsci.plugins.buildanalysis.dao.GlobalDAO;
import org.jenkinsci.plugins.buildanalysis.dao.LabelsDAO;
import org.jenkinsci.plugins.buildanalysis.dao.QueueDAO;
import org.jenkinsci.plugins.buildanalysis.dao.SlavesDAO;

import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.MongoException;

public class MongoDAOFactory extends DAOFactory {

    public static final String GLOBAL_COLLECTION_NAME = "global";
    public static final String BUILDS_COLLECTION_NAME = "builds";
    public static final String QUEUE_COLLECTION_NAME = "queue";
    public static final String LABEL_COLLECTION_NAME = "labels";
    public static final String SLAVE_COLLECTION_NAME = "slaves";

    private final DB db;

    public MongoDAOFactory(DB db) throws UnknownHostException, MongoException, IllegalArgumentException {
        if (db == null) {
            throw new IllegalArgumentException("DB is null");
        }
        this.db = db;
    }

    public GlobalDAO getGlobalDAO() {
        DBCollection coll = db.getCollection(GLOBAL_COLLECTION_NAME);
        return new MongoGlobalDAO(coll);
    }

    public GlobalDAO getGlobalDAO(String collectionName) {
        DBCollection coll = db.getCollection(collectionName);
        return new MongoGlobalDAO(coll);
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

    public LabelsDAO getLabelsDAO() {
        DBCollection coll = db.getCollection(LABEL_COLLECTION_NAME);
        return new MongoLabelsDAO(coll);
    }

    public LabelsDAO getLabelsDAO(String collectionName) {
        DBCollection coll = db.getCollection(collectionName);
        return new MongoLabelsDAO(coll);
    }

    public SlavesDAO getSlavesDAO() {
        DBCollection coll = db.getCollection(SLAVE_COLLECTION_NAME);
        return new MongoSlavesDAO(coll);
    }

    public SlavesDAO getSlavesDAO(String collectionName) {
        DBCollection coll = db.getCollection(collectionName);
        return new MongoSlavesDAO(coll);
    }
}
