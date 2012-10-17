package org.jenkinsci.plugins.buildanalysis.dao;

import org.jenkinsci.plugins.buildanalysis.model.BuildInfo;

public interface BuildDAO {

    public void create(BuildInfo build);
    public void updateOnStarted(BuildInfo build);
    public void updateOnFinalized(BuildInfo build);
    public void getBuild(String jobName, int number);
    public void getBuilds(String jobName);
    
}
