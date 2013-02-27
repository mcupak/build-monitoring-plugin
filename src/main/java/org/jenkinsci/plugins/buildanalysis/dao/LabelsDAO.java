package org.jenkinsci.plugins.buildanalysis.dao;

import org.jenkinsci.plugins.buildanalysis.model.LabelsInfo;


public interface LabelsDAO extends MonitorDAO {

    public void create(LabelsInfo labelsInfo);
    public void update(LabelsInfo labelsInfo);
    
	
}
