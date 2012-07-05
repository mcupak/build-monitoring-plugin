package org.jenkinsci.plugins.buildanalysis.mongo;

import hudson.model.Result;
import hudson.model.Cause;

import java.net.UnknownHostException;
import java.util.LinkedList;
import java.util.List;

import org.jenkinsci.plugins.buildanalysis.BuildInfo;
import org.jenkinsci.plugins.buildanalysis.dao.BuildDAO;

import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.Mongo;
import com.mongodb.WriteResult;

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
    
    public MongoBuildDAO() throws UnknownHostException {
        Mongo m = new Mongo("localhost");
        DB db = m.getDB("jenkins");
        coll = db.getCollection("builds");
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
        doc.put(KEY_TRIGGER_CAUSES, convertCauses(build.getTriggerCauses()));
        doc.put(KEY_PARAMETERS, build.getParameters());
        coll.update(old, new BasicDBObject().append("$set", doc), true, false);
    }
    
    public void updateOnFinalized(BuildInfo build) {
        BasicDBObject old = new BasicDBObject();
        old.put(KEY_NUMBER,build.getNumber());
        old.put(KEY_NAME, build.getName());
        BasicDBObject doc = new BasicDBObject();
        doc.put(KEY_FINISHED_TIME, build.getFinishedTime());
        doc.put(KEY_RESULT, convertResult(build.getResult()));
        coll.update(old, new BasicDBObject().append("$set", doc), true, false);
    }

    
    private String convertResult(Result result) {
        return result == null ? null : result.toString();
    }
    
    private List<String> convertCauses(List<Cause> causes) {
        if(causes == null)
            return null;
        List<String> causesStr = new LinkedList<String>();
        for(Cause cause: causes){
            causesStr.add(cause.getShortDescription());
        }
        return causesStr;
    }
       
}
