package org.jenkinsci.plugins.buildanalysis.mongo;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.NotImplementedException;
import org.jenkinsci.plugins.buildanalysis.dao.LabelsDAO;
import org.jenkinsci.plugins.buildanalysis.model.LabelInfo;
import org.jenkinsci.plugins.buildanalysis.model.LabelsInfo;

import com.mongodb.BasicDBObject;
import com.mongodb.DBCollection;

public class MongoLabelsDAO implements LabelsDAO {

	private final DBCollection coll;
	
	private static final String KEY_TIMESTAMP = "timestamp";
	private static final String KEY_LABLES = "labels";
	
	public MongoLabelsDAO(DBCollection coll) {
		this.coll = coll;
	}
	
	public void create(LabelsInfo labelsInfo) {
		coll.insert(getDbObject(labelsInfo));
	}
	
    public void update(LabelsInfo labelsInfo) {
    	throw new NotImplementedException();
    }
    
    public static BasicDBObject getDbObject(LabelsInfo labelsInfo) {
    	BasicDBObject doc = new BasicDBObject();
    	doc.put(KEY_TIMESTAMP, labelsInfo.getTimestamp());
    	
    	List<BasicDBObject> lList = new ArrayList<BasicDBObject>();
    	for(LabelInfo li: labelsInfo.getLabels()) {
    		lList.add(MongoLabelDAO.getDbObject(li));
    	}
    	doc.put(KEY_LABLES, lList);
    	
    	return doc;
    }
	
}
