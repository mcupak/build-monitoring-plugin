package org.jenkinsci.plugins.buildanalysis.mongo;

import java.net.UnknownHostException;
import java.util.logging.Logger;

import org.jenkinsci.plugins.buildanalysis.dao.DbConfig;

import com.mongodb.DB;
import com.mongodb.MongoClient;

public class MongoDB {

    private static DbConfig dbConfig;
    private static DB db;

    public static synchronized void init(DbConfig dbConfig) {
        if (dbConfig == null)
            throw new IllegalArgumentException("DbConfig is null");
        MongoDB.dbConfig = dbConfig;
        createDB();
    }

    public static DB createDB() throws IllegalStateException {
        if(dbConfig == null)
            throw new IllegalStateException("DB config is null, call init() method first!");
        
        MongoClient mongo;
        DB db = null;
        try {
            mongo = new MongoClient(dbConfig.getHostname());
        } catch(UnknownHostException e) {
            LOGGER.warning("Cannot connect to Mongo databse " + dbConfig.getHostname() + ", " + e.getMessage());
            return db;
        }
        
        try {
            
            db = mongo.getDB(dbConfig.getDbName());
            db.authenticate(dbConfig.getUsername(), dbConfig.getPassword().toCharArray());
        } catch (Exception e) {
            mongo.close(); // have to close the connector, see https://jira.mongodb.org/browse/JAVA-831
            LOGGER.warning("Cannot connect to obtain Mongo databse " + dbConfig.getDbName() + ", " + e.getMessage());
        }
        
        return db;
    }

    public static DB getInstance() {
        if (db == null)
            synchronized (MongoDB.class) {
                if (db == null)
                    db = createDB();
            }
        return db;
    }
    
    private static final Logger LOGGER = Logger.getLogger(MongoDB.class.getName());

}
