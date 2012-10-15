package org.jenkinsci.plugins.buildanalysis.dao;

import org.jenkinsci.plugins.buildanalysis.model.QueueInfo;

public interface QueueDAO {

    public void create(QueueInfo queueInfo);
    
}
