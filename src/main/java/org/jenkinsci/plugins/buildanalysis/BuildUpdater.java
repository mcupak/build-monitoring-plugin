package org.jenkinsci.plugins.buildanalysis;

import org.jenkinsci.plugins.buildanalysis.model.BuildInfo;

public interface BuildUpdater {

    public void update(BuildInfo build);
}
