package org.jenkinsci.plugins.buildanalysis.mongo;

import java.net.UnknownHostException;

import org.jenkinsci.plugins.buildanalysis.BuildUpdater;
import org.jenkinsci.plugins.buildanalysis.model.BuildInfo;

import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.Mongo;

public class MongoUpdater implements BuildUpdater {

    private final DBCollection coll; 
    
    public MongoUpdater() throws UnknownHostException {
        Mongo m = new Mongo("localhost");
        DB db = m.getDB("jenkins");
        coll = db.getCollection("builds");
    }

    public void update(BuildInfo build) {
        BasicDBObject doc = new BasicDBObject();
        doc.put("number",build.getNumber());
        doc.put("name", build.getName());
        //doc.put("date", build.getDate());
        coll.insert(doc);
    }

}
