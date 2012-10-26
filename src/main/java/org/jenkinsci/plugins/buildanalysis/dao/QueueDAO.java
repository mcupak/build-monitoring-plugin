package org.jenkinsci.plugins.buildanalysis.dao;

import net.sf.json.JSONArray;

import org.jenkinsci.plugins.buildanalysis.model.QueueInfo;

public interface QueueDAO {

    public void create(QueueInfo queueInfo);
    public void update(QueueInfo queueInfo);
    
    public JSONArray getQueueSizeSerie();
    
}
