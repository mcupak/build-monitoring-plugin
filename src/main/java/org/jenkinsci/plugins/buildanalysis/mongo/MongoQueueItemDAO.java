package org.jenkinsci.plugins.buildanalysis.mongo;

import org.jenkinsci.plugins.buildanalysis.model.QueueItemInfo;

import com.mongodb.BasicDBObject;


public class MongoQueueItemDAO {
	
	private final String KEY_ID = "id";
	private final String KEY_NUMBER = "number";
	private final String KEY_NAME = "name";
	private final String KEY_CLASS_NAME = "className";
	private final String KEY_PARAMS = "params";
	private final String KEY_BLOCKAGE_CAUSES = "blockageCauses";
	private final String KEY_IS_CONCURENT_BUILD = "isConcurentBuild";
	private final String KEY_IS_BUILDABLE = "isBuildable";
	private final String KEY_IS_BLOCKED = "isBlocked";
	private final String KEY_iS_STUCKED = "isStucked";
	
	public MongoQueueItemDAO() {
    }

    public BasicDBObject getDBObject(QueueItemInfo qi) {
        BasicDBObject doc = new BasicDBObject();
        doc.put(KEY_ID, qi.getId());
        doc.put(KEY_NUMBER, qi.getNumber());
        doc.put(KEY_NAME, qi.getName());
        doc.put(KEY_CLASS_NAME, qi.getClassName());
        doc.put(KEY_PARAMS, qi.getParams());
        doc.put(KEY_BLOCKAGE_CAUSES, qi.getBlockageCause().getShortDescription());
        doc.put(KEY_IS_CONCURENT_BUILD, qi.isConcurentBuild());
        doc.put(KEY_IS_BUILDABLE, qi.isBuildable());
        doc.put(KEY_IS_BLOCKED, qi.isBlocked());
        doc.put(KEY_iS_STUCKED, qi.isStucked());
        return doc;
    }
	
}
