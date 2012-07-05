package org.jenkinsci.plugins.buildanalysis.dao;

import org.jenkinsci.plugins.buildanalysis.QueueInfo;

public interface QueueDAO {

    public void create(QueueInfo queueInfo);
    
}
