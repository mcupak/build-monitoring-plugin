package org.jenkinsci.plugins.buildanalysis.mongo;

import hudson.util.IOUtils;

import java.io.IOException;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;

import org.jenkinsci.plugins.buildanalysis.dao.BuildDAO;
import org.jenkinsci.plugins.buildanalysis.model.BuildInfo;
import org.jenkinsci.plugins.buildanalysis.utils.BuildUtils;

import com.mongodb.BasicDBObject;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.mongodb.MapReduceCommand;
import com.mongodb.MapReduceOutput;

public class MongoBuildDAO implements BuildDAO {

    private final DBCollection coll; 
    
    private final String KEY_NUMBER = "number";
    private final String KEY_NAME = "name";
    private final String KEY_JOB_CLASS_NAME = "job_class";
    private final String KEY_JDK_NAME = "jdk_name";
    private final String KEY_LABEL = "label";
    private final String KEY_SCHEDULED_TIME = "scheduled_time";
    private final String KEY_STARTED_TIME = "started_time";
    private final String KEY_FINISHED_TIME = "finished_time";
    private final String KEY_RESULT = "result";
    private final String KEY_BUILD_ON = "build_on";
    private final String KEY_TRIGGER_CAUSES = "trigger_causes";
    private final String KEY_PARAMETERS = "parameters";
    
    public MongoBuildDAO(DBCollection coll) throws UnknownHostException {
    	this.coll = coll;
    }

    public void create(BuildInfo build) {
        BasicDBObject doc = new BasicDBObject();
        doc.put(KEY_NUMBER,build.getNumber());
        doc.put(KEY_NAME, build.getName());
        doc.put(KEY_SCHEDULED_TIME, build.getScheduledTime());
        coll.insert(doc);
    }
    
    public void updateOnStarted(BuildInfo build) {
        BasicDBObject old = new BasicDBObject();
        old.put(KEY_NUMBER,build.getNumber());
        old.put(KEY_NAME, build.getName());
        BasicDBObject doc = new BasicDBObject();
        doc.put(KEY_JOB_CLASS_NAME, build.getClassName());
        doc.put(KEY_JDK_NAME,build.getJdkName());
        doc.put(KEY_LABEL, build.getLabel());
        doc.put(KEY_STARTED_TIME, build.getStartedTime());
        doc.put(KEY_BUILD_ON, build.getBuildOn());
        doc.put(KEY_TRIGGER_CAUSES, BuildUtils.convertCauses(build.getTriggerCauses()));
        doc.put(KEY_PARAMETERS, build.getParameters());
        coll.update(old, new BasicDBObject().append("$set", doc), true, false);
    }
    
    public void updateOnFinalized(BuildInfo build) {
        BasicDBObject old = new BasicDBObject();
        old.put(KEY_NUMBER,build.getNumber());
        old.put(KEY_NAME, build.getName());
        BasicDBObject doc = new BasicDBObject();
        doc.put(KEY_FINISHED_TIME, build.getFinishedTime());
        doc.put(KEY_RESULT, BuildUtils.convertResult(build.getResult()));
        coll.update(old, new BasicDBObject().append("$set", doc), true, false);
    }
    
    public void getBuilds(String jobName) {
    	BasicDBObject query = new BasicDBObject();
    	query.put(KEY_NAME, jobName);
    	List<DBObject> results = doQuery(query);
    	
    	for(DBObject o : results) {
    		System.out.println(o);
    	}
    	
    	System.out.println("MAP_REDUCE:");
    	MapReduceOutput mr = mapReduce();
    	for(DBObject o : mr.results()) {
    		System.out.println(o);
    	}
    }
    
    public void getBuild(String jobName, int number) {
    	BasicDBObject query = new BasicDBObject();
    	query.put(KEY_NAME, jobName);
    	query.put(KEY_NUMBER, number);
    	List<DBObject> results = doQuery(query);
    	
    	for(DBObject o : results) {
    		System.out.println(o);
    	}
    }
    
    
    /**
     * 
     * @param query to be executed
     * @return {@link List} of search results, can be empty but never null
     */
    private List<DBObject> doQuery(BasicDBObject query) {
    	List<DBObject> results = new ArrayList<DBObject>();
    	DBCursor cursor = coll.find(query);
        try {
            while(cursor.hasNext()) {
                results.add(cursor.next());
            }
        } finally {
            cursor.close();
        }
        return results;
    }
    
    private MapReduceOutput mapReduce() {
    	String map = "";
    	String reduce = "";
    	ClassLoader classLoader = getClass().getClassLoader();
    	try {
    		map = IOUtils.toString(classLoader.getResourceAsStream("js/MapReduce/mapTest.js"));
    		reduce = IOUtils.toString(classLoader.getResourceAsStream("js/MapReduce/reduceTest.js"));
    	} catch(IOException e) {
    		e.printStackTrace();
    	}
    	
    	MapReduceOutput out = coll.mapReduce(map, reduce, null, MapReduceCommand.OutputType.INLINE, null);
    	return out;
    }
    
}
