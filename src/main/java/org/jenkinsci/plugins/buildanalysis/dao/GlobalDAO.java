package org.jenkinsci.plugins.buildanalysis.dao;

import org.jenkinsci.plugins.buildanalysis.model.GlobalInfo;

public interface GlobalDAO {
    
    public void create(GlobalInfo globalInfo);

}