package org.jenkinsci.plugins.buildanalysis.mongo;

import org.apache.commons.lang.NotImplementedException;
import org.jenkinsci.plugins.buildanalysis.dao.SlaveDAO;
import org.jenkinsci.plugins.buildanalysis.model.SlaveInfo;

import com.mongodb.BasicDBObject;
import com.mongodb.DBCollection;

public class MongoSlaveDAO implements SlaveDAO {
	
	private final DBCollection coll;
	
	private static final String KEY_NAME = "name";
	private static final String KEY_IS_ONLINE = "isOnline";
	private static final String KEY_TOTAL_EXECUTORS = "totalExecutors";
	private static final String KEY_BUSY_EXECUTORS = "busyExecutors";
	private static final String KEY_QUEUE_LENGTH = "queueLength";
	private static final String KEY_LABLES = "labels";
	
	public MongoSlaveDAO(DBCollection coll) {
		this.coll = coll;
	}
	
	public void create(SlaveInfo slaveInfo) {
		coll.insert(getDbObject(slaveInfo));
	}
	
    public void update(SlaveInfo slaveInfo) {
    	throw new NotImplementedException();
    }

    public static BasicDBObject getDbObject(SlaveInfo slaveInfo) {
    	BasicDBObject doc = new BasicDBObject();
		doc.put(KEY_NAME, slaveInfo.getName());
		doc.put(KEY_IS_ONLINE, slaveInfo.isOnline());
		doc.put(KEY_TOTAL_EXECUTORS, slaveInfo.getTotalExecutors());
		doc.put(KEY_BUSY_EXECUTORS, slaveInfo.getBusyExecutors());
		doc.put(KEY_QUEUE_LENGTH, slaveInfo.getQueueLength());
		doc.put(KEY_LABLES, slaveInfo.getLabels());
		return doc;
    }
    
}
