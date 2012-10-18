package org.jenkinsci.plugins.buildanalysis.model;

import java.util.List;

public class LabelInfo {

	private String label;
	private int totalExecutors;
	private int busyExecutors;
	private int queueLength;
	private List<String> slaves;
	
	public String getLabel() {
		return label;
	}
	
	public void setLabel(String label) {
		this.label = label;
	}
	
	public int getTotalExecutors() {
		return totalExecutors;
	}
	
	public void setTotalExecutors(int totalExecutors) {
		this.totalExecutors = totalExecutors;
	}
	
	public int getBusyExecutors() {
		return busyExecutors;
	}
	
	public void setBusyExecutors(int busyExecutors) {
		this.busyExecutors = busyExecutors;
	}
	
	public int getQueueLength() {
		return queueLength;
	}
	
	public void setQueueLength(int queueLength) {
		this.queueLength = queueLength;
	}

	public List<String> getSlaves() {
		return slaves;
	}

	public void setSlaves(List<String> slaves) {
		this.slaves = slaves;
	}
	
}
