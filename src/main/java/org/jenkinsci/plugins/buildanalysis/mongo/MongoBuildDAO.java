package org.jenkinsci.plugins.buildanalysis.mongo;

import java.net.UnknownHostException;

import org.jenkinsci.plugins.buildanalysis.dao.BuildDAO;
import org.jenkinsci.plugins.buildanalysis.model.BuildInfo;
import org.jenkinsci.plugins.buildanalysis.utils.BuildUtils;

import com.mongodb.BasicDBObject;
import com.mongodb.DBCollection;

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
       
}
