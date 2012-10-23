package org.jenkinsci.plugins.buildanalysis.mongo;

import org.apache.commons.lang.NotImplementedException;
import org.jenkinsci.plugins.buildanalysis.dao.GlobalDAO;
import org.jenkinsci.plugins.buildanalysis.model.GlobalInfo;

import com.mongodb.BasicDBObject;
import com.mongodb.DBCollection;

public class MongoGlobalDAO implements GlobalDAO {
	
	private final DBCollection coll;
	
	private static final String KEY_TIMESTAMP = "timestamp";
	private static final String KEY_NUM_PROJECTS = "numberOfProjetcs";
	private static final String KEY_NUM_BUILDS = "numberOfBuilds";
	private static final String KEY_NUM_SLAVES = "numberOfSlaves";
	private static final String KEY_NUM_OFFLINE_SLAVES = "numberOfOfflineSlaves";
	private static final String KEY_NUM_IDLE_SLAVES = "numberOfIdleSlaves";
	
	public MongoGlobalDAO(DBCollection coll) {
		this.coll = coll;
	}
	
	public void create(GlobalInfo globalInfo) {
		coll.insert(getDbObject(globalInfo));
	}
	
	public void update(GlobalInfo globalInfo) {
    	throw new NotImplementedException();
    }
	
	public static BasicDBObject getDbObject(GlobalInfo globalInfo) {
		BasicDBObject doc = new BasicDBObject();
		doc.put(KEY_TIMESTAMP, globalInfo.getTimestamp());
		doc.put(KEY_NUM_PROJECTS, globalInfo.getNumberOfProjetcs());
		doc.put(KEY_NUM_BUILDS, globalInfo.getNumberOfBuilds());
		doc.put(KEY_NUM_SLAVES, globalInfo.getNumberOfSlaves());
		doc.put(KEY_NUM_OFFLINE_SLAVES, globalInfo.getNumberOfOfflineSlaves());
		doc.put(KEY_NUM_IDLE_SLAVES, globalInfo.getNumberOfIdleSlaves());
		return doc;
	}

}
