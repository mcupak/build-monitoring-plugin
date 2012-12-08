package org.jenkinsci.plugins.buildanalysis.model;

import java.util.Date;

/**
 * High level statistics data for whole Jenkins instance like number of slaves configured.
 * 
 * @author vjuranek
 *
 */
public class GlobalInfo {
    
    /**
     * Time when the data are collected 
     */
	private final Date timestamp;
	
	/**
	 * Number of project configured within this instance in given time 
	 */
    private int numberOfProjetcs;
    
    /**
     * Overall number of builds  
     */
    private int numberOfBuilds;
    
    /**
     * Total number of slaves configured within given instance
     */
    private int numberOfSlaves;
    
    /**
     * Number of offline slaves in given time
     */
    private int numberOfOfflineSlaves;
    
    /**
     * Number of idle slaves in given time
     */
    private int numberOfIdleSlaves;
    
    public GlobalInfo(Date timestamp) {
    	this.timestamp = timestamp;
    }
    
    public Date getTimestamp() {
		return timestamp;
	}

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

	public int getNumberOfIdleSlaves() {
		return numberOfIdleSlaves;
	}

	public void setNumberOfIdleSlaves(int numberOfIdleSlaves) {
		this.numberOfIdleSlaves = numberOfIdleSlaves;
	}

    
}
