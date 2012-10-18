package org.jenkinsci.plugins.buildanalysis.mongo;

import java.net.UnknownHostException;

import org.jenkinsci.plugins.buildanalysis.dao.QueueDAO;
import org.jenkinsci.plugins.buildanalysis.model.QueueInfo;

import com.mongodb.DBCollection;

public class MongoQueueDAO implements QueueDAO {
    
	private final DBCollection coll;
	
	public MongoQueueDAO(DBCollection coll) throws UnknownHostException {
    	this.coll = coll;
    }
	
    public void create(QueueInfo queueInfo) {
        
    }

    public void update(QueueInfo queueInfo) {
        
    }
}
