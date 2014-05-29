package org.jenkinsci.plugins.buildanalysis.dao;

import java.util.List;

import net.sf.json.JSONArray;

import org.jenkinsci.plugins.buildanalysis.model.BuildInfo;

import com.mongodb.DBObject;

public interface BuildDAO extends MonitorDAO {

    public void create(BuildInfo build);

    public void updateOnStarted(BuildInfo build);

    public void updateOnFinalized(BuildInfo build);

    public void getBuild(String jobName, int number);

    public void getBuilds(String jobName);

    public List<DBObject> dbQuery(String queryString);

    public JSONArray getBuildTypes();

}
