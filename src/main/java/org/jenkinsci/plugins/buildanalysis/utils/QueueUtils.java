package org.jenkinsci.plugins.buildanalysis.utils;

import hudson.model.Label;
import hudson.model.Node;
import hudson.model.Queue.BuildableItem;

import java.util.ArrayList;
import java.util.List;

import org.jenkinsci.plugins.buildanalysis.model.QueueItemInfo;
import org.jenkinsci.plugins.buildanalysis.mongo.MongoQueueItemDAO;

import com.mongodb.BasicDBObject;

public class QueueUtils {

	public static int getLabelQueue(Label l, List<BuildableItem> bis) {
		int count = 0;
        for (BuildableItem bi : bis) {
            if(bi.task.getAssignedLabel() == l)
                count++;
        }
        return count;
	}
	
	public static int getSlaveQueue(Node n, List<BuildableItem> bis) {
		int count = 0;
        for (BuildableItem bi : bis) {
            if(n.canTake(bi) ==  null)
                count++;
        }
        return count;
	}
	
	public static List<BasicDBObject> queueListToDBObject(List<QueueItemInfo> qiList) {
    	List<BasicDBObject> dbList = new ArrayList<BasicDBObject>();
    	MongoQueueItemDAO qDao = new MongoQueueItemDAO();
    	for(QueueItemInfo qi : qiList) {
    		dbList.add(qDao.getDBObject(qi));
    	}
    	return dbList;
    }
	
}
