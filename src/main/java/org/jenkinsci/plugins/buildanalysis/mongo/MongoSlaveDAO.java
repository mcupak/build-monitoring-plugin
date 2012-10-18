package org.jenkinsci.plugins.buildanalysis.mongo;

import org.apache.commons.lang.NotImplementedException;
import org.jenkinsci.plugins.buildanalysis.dao.SlaveDAO;
import org.jenkinsci.plugins.buildanalysis.model.SlaveInfo;

import com.mongodb.BasicDBObject;
import com.mongodb.DBCollection;

public class MongoSlaveDAO implements SlaveDAO {
	
	private final DBCollection coll;
	
	private final String KEY_NAME = "name";
	private final String KEY_TOTAL_EXECUTORS = "totalExecutors";
	private final String KEY_BUSY_EXECUTORS = "busyExecutors";
	private final String KEY_QUEUE_LENGTH = "queueLength";
	private final String KEY_LABLES = "labels";
	
	public MongoSlaveDAO(DBCollection coll) {
		this.coll = coll;
	}
	
	public void create(SlaveInfo slaveInfo) {
		BasicDBObject doc = new BasicDBObject();
		doc.put(KEY_NAME, slaveInfo.getName());
		doc.put(KEY_TOTAL_EXECUTORS, slaveInfo.getTotalExecutors());
		doc.put(KEY_BUSY_EXECUTORS, slaveInfo.getBusyExecutors());
		doc.put(KEY_QUEUE_LENGTH, slaveInfo.getQueueLength());
		doc.put(KEY_LABLES, slaveInfo.getLabels());
		coll.insert(doc);
	}
	
    public void update(SlaveInfo slaveInfo) {
    	throw new NotImplementedException();
    }

}
