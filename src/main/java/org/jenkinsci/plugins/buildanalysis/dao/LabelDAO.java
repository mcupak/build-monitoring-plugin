package org.jenkinsci.plugins.buildanalysis.dao;

import org.jenkinsci.plugins.buildanalysis.model.LabelInfo;

public interface LabelDAO {
	
	public void create(LabelInfo labelInfo);
    public void update(LabelInfo labelInfo);

}
