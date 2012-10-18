package org.jenkinsci.plugins.buildanalysis.model;

import java.util.List;

public class SlaveInfo {
	
	private String name;
	private int totalExecutors;
	private int busyExecutors;
	private int queueLength;
	private List<String> labels;
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
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
	
	public List<String> getLabels() {
		return labels;
	}
	
	public void setLabels(List<String> labels) {
		this.labels = labels;
	}

}
