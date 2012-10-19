package org.jenkinsci.plugins.buildanalysis.dao;

import org.jenkinsci.plugins.buildanalysis.model.LabelsInfo;


public interface LabelsDAO {

    public void create(LabelsInfo labelsInfo);
    public void update(LabelsInfo labelsInfo);
    
	
}
