package org.jenkinsci.plugins.buildanalysis.mongo;

import java.util.ArrayList;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import net.sf.json.JSONArray;

import org.jenkinsci.plugins.buildanalysis.dao.BuildDAO;
import org.jenkinsci.plugins.buildanalysis.model.BuildInfo;
import org.jenkinsci.plugins.buildanalysis.utils.BuildUtils;
import org.jenkinsci.plugins.buildanalysis.utils.MapReduceUtils;
import org.jenkinsci.plugins.buildanalysis.utils.MapReduceUtils.MapReduceFunctions;

import com.mongodb.BasicDBObject;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.mongodb.MapReduceCommand;
import com.mongodb.MapReduceOutput;
import com.mongodb.QueryBuilder;
import com.mongodb.WriteResult;
import com.mongodb.util.JSON;

public class MongoBuildDAO implements BuildDAO {

    private final DBCollection coll;

    private static final String KEY_NUMBER = "number";
    private static final String KEY_NAME = "name";
    private static final String KEY_JOB_CLASS_NAME = "job_class";
    private static final String KEY_JDK_NAME = "jdk_name";
    private static final String KEY_LABEL = "label";
    private static final String KEY_SCHEDULED_TIME = "scheduled_time";
    private static final String KEY_STARTED_TIME = "started_time";
    private static final String KEY_FINISHED_TIME = "finished_time";
    private static final String KEY_RESULT = "result";
    private static final String KEY_BUILD_ON = "build_on";
    private static final String KEY_TRIGGER_CAUSES = "trigger_causes";
    private static final String KEY_PARAMETERS = "parameters";

    public MongoBuildDAO(DBCollection coll) {
        this.coll = coll;
    }

    public void create(BuildInfo build) {
        BasicDBObject doc = new BasicDBObject();
        doc.put(KEY_NUMBER, build.getNumber());
        doc.put(KEY_NAME, build.getName());
        doc.put(KEY_SCHEDULED_TIME, build.getScheduledTime());
        coll.insert(doc);
    }

    public void updateOnStarted(BuildInfo build) {
        System.out.println("UPDATED on start");
        try {
            BasicDBObject old = new BasicDBObject();
            old.put(KEY_NUMBER, build.getNumber());
            old.put(KEY_NAME, build.getName());

            BasicDBObject doc = new BasicDBObject();
            doc.put(KEY_JOB_CLASS_NAME, build.getClassName());
            doc.put(KEY_JDK_NAME, build.getJdkName());
            doc.put(KEY_LABEL, build.getLabel());
            doc.put(KEY_STARTED_TIME, build.getStartedTime());
            doc.put(KEY_BUILD_ON, build.getBuildOn());
            doc.put(KEY_TRIGGER_CAUSES, BuildUtils.convertCauses(build.getTriggerCauses()));
            doc.put(KEY_PARAMETERS, build.getParameters());
            WriteResult wr = coll.update(old, new BasicDBObject().append("$set", doc), true, false);
            System.out.println("Result:" + wr.toString());
            System.out.println(wr.getError());
        } catch (Exception e) {
            System.out.println("VYJIMKA:");
            e.printStackTrace();
        }
    }

    public void updateOnFinalized(BuildInfo build) {
        BasicDBObject old = new BasicDBObject();
        old.put(KEY_NUMBER, build.getNumber());
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

        for (DBObject o : results) {
            System.out.println(o);
        }

    }

    public void getBuild(String jobName, int number) {
        BasicDBObject query = new BasicDBObject();
        query.put(KEY_NAME, jobName);
        query.put(KEY_NUMBER, number);
        List<DBObject> results = doQuery(query);

        for (DBObject o : results) {
            System.out.println(o);
        }
    }

    public JSONArray getBuildTypes() {
        JSONArray buildTypes = new JSONArray();
        MapReduceOutput mr = mrBuildTypes();
        for (DBObject o : mr.results()) {
            System.out.println("Builds: " + o);
            JSONArray typesPoint = new JSONArray();
            String type = o.get("_id") == null ? "Unknown" : (String) o.get("_id");
            typesPoint.add(type);
            typesPoint.add(o.get("value"));
            buildTypes.add(typesPoint);
        }
        return buildTypes;
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
            while (cursor.hasNext()) {
                results.add(cursor.next());
            }
        } finally {
            cursor.close();
        }
        return results;
    }

    /*
     * private MapReduceOutput mapReduce() { String map = ""; String reduce = ""; ClassLoader classLoader =
     * getClass().getClassLoader(); try { map = IOUtils.toString(classLoader
     * .getResourceAsStream("js/MapReduce/mapTest.js")); reduce = IOUtils.toString(classLoader
     * .getResourceAsStream("js/MapReduce/reduceTest.js")); } catch (IOException e) { e.printStackTrace(); }
     * 
     * MapReduceOutput out = coll.mapReduce(map, reduce, null, MapReduceCommand.OutputType.INLINE, null); for (DBObject
     * o : out.results()) { System.out.println("Values: " + o.get("value")); } return out; }
     */
    private MapReduceOutput mrBuildTypes() {
        MapReduceFunctions mr = MapReduceUtils.getMapReduce(MongoDAOFactory.BUILDS_COLLECTION_NAME, "BuildType");
        MapReduceOutput out = coll.mapReduce(mr.getMap(), mr.getReduce(), null, MapReduceCommand.OutputType.INLINE,
                null);
        return out;
    }

    public String find() {
        Date startDate = (new GregorianCalendar(2013, 3, 23)).getTime();
        DBObject query = QueryBuilder.start(KEY_SCHEDULED_TIME).greaterThanEquals(startDate).get();
        List<DBObject> res = coll.find(query).toArray();
        return JSON.serialize(res);
    }

    public List<DBObject> dbQuery(String queryString) {
        DBObject query = QueryBuilder.start(KEY_NAME).in(queryString.split(",")).get();
        List<DBObject> res = coll.find(query).toArray();
        return res;
    }
}
