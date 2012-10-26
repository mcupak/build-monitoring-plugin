package org.jenkinsci.plugins.buildanalysis.mongo;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.NotImplementedException;
import org.jenkinsci.plugins.buildanalysis.dao.QueueDAO;
import org.jenkinsci.plugins.buildanalysis.model.QueueInfo;
import org.jenkinsci.plugins.buildanalysis.model.QueueItemInfo;
import org.jenkinsci.plugins.buildanalysis.utils.QueueUtils;

import com.mongodb.BasicDBObject;
import com.mongodb.DBCollection;

public class MongoQueueDAO implements QueueDAO {
    
	private final DBCollection coll;
	
	private static final String KEY_TIMESTAMP = "timestamp";
	private static final String KEY_QUEUE_SIZE = "queueSize";
	private static final String KEY_BUILDABLE_SIZE = "buildableSize";
	private static final String KEY_PENDING_SIZE = "pendingSize";
	private static final String KEY_BLOCKED_SIZE = "blockedSize";
	private static final String KEY_WAITING_SIZE = "waitingSize";
	private static final String KEY_QUEUE_LIST = "queueList";
	
	public MongoQueueDAO(DBCollection coll) {
    	this.coll = coll;
    }
	
    public void create(QueueInfo queueInfo) {
    	BasicDBObject doc = new BasicDBObject();
        doc.put(KEY_TIMESTAMP, queueInfo.getTimestamp());
        doc.put(KEY_QUEUE_SIZE, queueInfo.getQueueSize());
        doc.put(KEY_BUILDABLE_SIZE, queueInfo.getBuildableSize());
        doc.put(KEY_PENDING_SIZE, queueInfo.getPendingSize());
        doc.put(KEY_BLOCKED_SIZE, queueInfo.getBlockedSize());
        doc.put(KEY_WAITING_SIZE, queueInfo.getWaitingSize());
        doc.put(KEY_QUEUE_LIST, QueueUtils.queueListToDBObject(queueInfo.getQueueList()));
        coll.insert(doc);
    }

    public void update(QueueInfo queueInfo) {
        throw new NotImplementedException();
    }
    
    
}
