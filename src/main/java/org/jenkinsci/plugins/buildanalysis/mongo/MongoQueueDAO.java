package org.jenkinsci.plugins.buildanalysis.mongo;

import net.sf.json.JSONArray;

import org.apache.commons.lang.NotImplementedException;
import org.jenkinsci.plugins.buildanalysis.dao.QueueDAO;
import org.jenkinsci.plugins.buildanalysis.model.QueueInfo;
import org.jenkinsci.plugins.buildanalysis.utils.MapReduceUtils;
import org.jenkinsci.plugins.buildanalysis.utils.MapReduceUtils.MapReduceFunctions;
import org.jenkinsci.plugins.buildanalysis.utils.QueueUtils;

import com.mongodb.BasicDBObject;
import com.mongodb.DBCollection;
import com.mongodb.DBObject;
import com.mongodb.MapReduceCommand;
import com.mongodb.MapReduceOutput;

public class MongoQueueDAO implements QueueDAO {
    
	private final DBCollection coll;
	
	private static final String COLLECTION_NAME = "queue";
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
    
    public JSONArray getQueueSizeSerie() {
    	JSONArray queueSize = new JSONArray();
		
    	MapReduceOutput mr = mapReduce();
    	for(DBObject o : mr.results()) {
    		BasicDBObject value = (BasicDBObject)o.get("value");
    		String date = value.getString("date");
    		Double queueAvg = (Double)value.get("queueSize");
    		
    		JSONArray queuePoint = new JSONArray();
    		queuePoint.add(date);
    		queuePoint.add(queueAvg);
    		queueSize.add(queuePoint);
    	}
    	
		return queueSize;
    }
    
    private MapReduceOutput mapReduce() {
    	MapReduceFunctions mr = MapReduceUtils.getMapReduce(COLLECTION_NAME);
    	MapReduceOutput out = coll.mapReduce(mr.getMap(), mr.getReduce(), null, MapReduceCommand.OutputType.INLINE, null);
    	return out;
    }
}
