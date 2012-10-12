package org.jenkinsci.plugins.buildanalysis;

import jenkins.model.Jenkins;
import hudson.Extension;
import hudson.model.PeriodicWork;

/*@Extension
public class StatsMonitor extends PeriodicWork {

    private static final int PERIOD_MINUTES = 15;
    
    public long getRecurrencePeriod() {
        return PERIOD_MINUTES * 60 * 1000L;
    }
    
    protected void doRun() throws Exception {
        StatsInfo statsInfo = new StatsInfo();
        Jenkins j = Jenkins.getInstance();
        statsInfo.setNumberOfProjetcs(j.getItems().size());
        statsInfo.setNumberOfSlaves(j.getNodes().size());
    }
}*/

