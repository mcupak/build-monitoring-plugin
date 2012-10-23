package org.jenkinsci.plugins.buildanalysis.mongo;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.NotImplementedException;
import org.jenkinsci.plugins.buildanalysis.dao.SlavesDAO;
import org.jenkinsci.plugins.buildanalysis.model.SlaveInfo;
import org.jenkinsci.plugins.buildanalysis.model.SlavesInfo;

import com.mongodb.BasicDBObject;
import com.mongodb.DBCollection;

public class MongoSlavesDAO implements SlavesDAO {

	private final DBCollection coll;
	
	private static final String KEY_TIMESTAMP = "timestamp";
	private static final String KEY_SLAVE = "slaves";
	
	public MongoSlavesDAO(DBCollection coll) {
		this.coll = coll;
	}
	
	public void create(SlavesInfo slavesInfo) {
		coll.insert(getDbObject(slavesInfo));
	}
	
    public void update(SlavesInfo slavesInfo) {
    	throw new NotImplementedException();
    }

    public static BasicDBObject getDbObject(SlavesInfo slavesInfo) {
    	BasicDBObject doc = new BasicDBObject();
    	doc.put(KEY_TIMESTAMP, slavesInfo.getTimestamp());
    	
    	List<BasicDBObject> sList = new ArrayList<BasicDBObject>();
    	for(SlaveInfo si: slavesInfo.getSlaves()) {
    		sList.add(MongoSlaveDAO.getDbObject(si));
    	}
    	doc.put(KEY_SLAVE, sList);
    	
    	return doc;
    }
    
}
