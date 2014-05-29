package org.jenkinsci.plugins.buildanalysis.mongo;

import java.util.logging.Logger;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.commons.lang.NotImplementedException;
import org.jenkinsci.plugins.buildanalysis.dao.DAOFactory;
import org.jenkinsci.plugins.buildanalysis.dao.GlobalDAO;
import org.jenkinsci.plugins.buildanalysis.model.GlobalInfo;
import org.jenkinsci.plugins.buildanalysis.utils.MapReduceUtils;
import org.jenkinsci.plugins.buildanalysis.utils.MapReduceUtils.MapReduceFunctions;

import com.mongodb.BasicDBObject;
import com.mongodb.DBCollection;
import com.mongodb.DBObject;
import com.mongodb.MapReduceCommand;
import com.mongodb.MapReduceOutput;
import com.mongodb.WriteResult;

public class MongoGlobalDAO implements GlobalDAO {

    private static final String KEY_TIMESTAMP = "timestamp";
    private static final String KEY_NUM_PROJECTS = "numberOfProjetcs";
    private static final String KEY_NUM_BUILDS = "numberOfBuilds";
    private static final String KEY_NUM_SLAVES = "numberOfSlaves";
    private static final String KEY_NUM_OFFLINE_SLAVES = "numberOfOfflineSlaves";
    private static final String KEY_NUM_IDLE_SLAVES = "numberOfIdleSlaves";

    private final DBCollection coll;

    public MongoGlobalDAO(DBCollection coll) {
        this.coll = coll;
    }

    public void create(GlobalInfo globalInfo) {
        WriteResult wr = coll.insert(getDbObject(globalInfo));
        if (wr.getError() != null) {
            LOGGER.warning("Some problem with storing data into global collection:" + wr.getError());
        }
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

    public JSONObject getSeries() {
        JSONArray projects = new JSONArray();
        JSONArray builds = new JSONArray();
        JSONArray slaves = new JSONArray();
        JSONArray offline = new JSONArray();
        JSONArray idle = new JSONArray();

        MapReduceOutput mr = mapReduce();
        for (DBObject o : mr.results()) {
            BasicDBObject value = (BasicDBObject) o.get("value");
            String date = value.getString("date");
            Double projectsAvg = (Double) value.get("projects");
            Double buildsAvg = (Double) value.get("builds");
            Double slavesAvg = (Double) value.get("slaves");
            Double offlineAvg = (Double) value.get("offline");
            Double idleAvg = (Double) value.get("idle");

            JSONArray projectsPoint = new JSONArray();
            projectsPoint.add(date);
            projectsPoint.add(projectsAvg);
            projects.add(projectsPoint);

            JSONArray buildsPoint = new JSONArray();
            buildsPoint.add(date);
            buildsPoint.add(buildsAvg);
            builds.add(buildsPoint);

            JSONArray slavesPoint = new JSONArray();
            slavesPoint.add(date);
            slavesPoint.add(slavesAvg);
            slaves.add(slavesPoint);

            JSONArray offlinePoint = new JSONArray();
            offlinePoint.add(date);
            offlinePoint.add(offlineAvg);
            offline.add(offlinePoint);

            JSONArray idlePoint = new JSONArray();
            idlePoint.add(date);
            idlePoint.add(idleAvg);
            idle.add(idlePoint);

        }

        JSONObject jo = new JSONObject();
        jo.put("projects", projects);
        jo.put("builds", builds);
        jo.put("slaves", slaves);
        jo.put("offline", offline);
        jo.put("idle", idle);

        return jo;
    }

    private MapReduceOutput mapReduce() {
        MapReduceFunctions mr = MapReduceUtils.getMapReduce(MongoDAOFactory.GLOBAL_COLLECTION_NAME);
        MapReduceOutput out = coll.mapReduce(mr.getMap(), mr.getReduce(), null, MapReduceCommand.OutputType.INLINE, null);
        return out;
    }

    private static final Logger LOGGER = Logger.getLogger(DAOFactory.class.getName());

}
