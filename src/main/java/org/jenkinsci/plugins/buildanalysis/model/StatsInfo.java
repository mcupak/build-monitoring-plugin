package org.jenkinsci.plugins.buildanalysis.model;

public class StatsInfo {
    
    private int numberOfProjetcs;
    private int numberOfBuilds;
    private int numberOfSlaves;
    private int numberOfOfflineSlaves;
    private int numberOfExecutors;
    
    
    public int getNumberOfProjetcs() {
        return numberOfProjetcs;
    }
    public void setNumberOfProjetcs(int numberOfProjetcs) {
        this.numberOfProjetcs = numberOfProjetcs;
    }
    public int getNumberOfBuilds() {
        return numberOfBuilds;
    }
    public void setNumberOfBuilds(int numberOfBuilds) {
        this.numberOfBuilds = numberOfBuilds;
    }
    public int getNumberOfSlaves() {
        return numberOfSlaves;
    }
    public void setNumberOfSlaves(int numberOfSlaves) {
        this.numberOfSlaves = numberOfSlaves;
    }
    public int getNumberOfOfflineSlaves() {
        return numberOfOfflineSlaves;
    }
    public void setNumberOfOfflineSlaves(int numberOfOfflineSlaves) {
        this.numberOfOfflineSlaves = numberOfOfflineSlaves;
    }
    
    

}
