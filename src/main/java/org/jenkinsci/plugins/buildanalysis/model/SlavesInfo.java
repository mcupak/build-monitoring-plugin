package org.jenkinsci.plugins.buildanalysis.model;

import java.util.Date;
import java.util.List;

public class SlavesInfo {

	private final Date timestamp;
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
