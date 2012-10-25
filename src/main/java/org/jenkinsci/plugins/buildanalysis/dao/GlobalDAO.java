package org.jenkinsci.plugins.buildanalysis.dao;

import net.sf.json.JSONObject;

import org.jenkinsci.plugins.buildanalysis.model.GlobalInfo;

public interface GlobalDAO {
    
    public void create(GlobalInfo globalInfo);
    public JSONObject getSeries();

}
