package org.jenkinsci.plugins.buildanalysis.dao;

import org.jenkinsci.plugins.buildanalysis.model.SlaveInfo;

public interface SlaveDAO extends MonitorDAO {

    public void create(SlaveInfo slaveInfo);

    public void update(SlaveInfo slaveInfo);

}
