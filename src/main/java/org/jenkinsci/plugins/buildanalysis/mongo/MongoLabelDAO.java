package org.jenkinsci.plugins.buildanalysis.mongo;

import org.apache.commons.lang.NotImplementedException;
import org.jenkinsci.plugins.buildanalysis.dao.LabelDAO;
import org.jenkinsci.plugins.buildanalysis.model.LabelInfo;

import com.mongodb.BasicDBObject;
import com.mongodb.DBCollection;

public class MongoLabelDAO implements LabelDAO {
	
	private final DBCollection coll;
	
	private static final String KEY_LABEL = "label";
	private static final String KEY_LABEL_EXPRESSION = "labelExpresion";
	private static final String KEY_TOTAL_EXECUTOR = "totalExecutors";
	private static final String KEY_BUSY_EXECUTORS = "busyExecutors";
	private static final String KEY_QUEUE_LENGTH = "queueLength";
	private static final String KEY_SLAVES = "slaves";
	
	
	public MongoLabelDAO(DBCollection coll) {
		this.coll = coll;
	}

    public void create(LabelInfo labelInfo) {
    	coll.insert(getDbObject(labelInfo));
    }
    
    public void update(LabelInfo labelInfo) {
    	throw new NotImplementedException();
    }
    
    public static BasicDBObject getDbObject(LabelInfo labelInfo) {
    	BasicDBObject doc = new BasicDBObject();
    	doc.put(KEY_LABEL, labelInfo.getLabel());
    	doc.put(KEY_LABEL_EXPRESSION, labelInfo.getLabelExpression());
    	doc.put(KEY_TOTAL_EXECUTOR, labelInfo.getTotalExecutors());
    	doc.put(KEY_BUSY_EXECUTORS, labelInfo.getBusyExecutors());
    	doc.put(KEY_QUEUE_LENGTH, labelInfo.getQueueLength());
    	doc.put(KEY_SLAVES, labelInfo.getSlaves());
    	return doc;
    }
	
}
