package org.jenkinsci.plugins.buildanalysis.dao;

import org.jenkinsci.plugins.buildanalysis.model.SlavesInfo;

public interface SlavesDAO {

    public void create(SlavesInfo slavesInfo);
    public void update(SlavesInfo slavesInfo);
	
}
