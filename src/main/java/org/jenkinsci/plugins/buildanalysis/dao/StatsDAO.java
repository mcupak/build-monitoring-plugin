package org.jenkinsci.plugins.buildanalysis.dao;

import org.jenkinsci.plugins.buildanalysis.model.StatsInfo;

public interface StatsDAO {
    
    public void create(StatsInfo statsInfo);

}
