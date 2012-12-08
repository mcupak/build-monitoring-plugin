package org.jenkinsci.plugins.buildanalysis.model;

import java.util.Date;
import java.util.List;

/**
 * Statistics data for all slaves
 * 
 * @author vjuranek
 *
 */
public class SlavesInfo {

    /**
     * Time when the data are collected 
     */
	private final Date timestamp;
	
	/**
	 * All slaves available in give time
	 */
	private List<SlaveInfo> slaves;
	
	public SlavesInfo(Date timestamp) {
		this.timestamp = timestamp;
	}

	public List<SlaveInfo> getSlaves() {
		return slaves;
	}

	public void setSlaves(List<SlaveInfo> slaves) {
		this.slaves = slaves;
	}

	public Date getTimestamp() {
		return timestamp;
	}
	
	
	
}
