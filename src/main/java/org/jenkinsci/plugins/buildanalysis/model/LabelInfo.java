package org.jenkinsci.plugins.buildanalysis.model;

import java.util.List;

/**
 * Statistics data for given {@link Label}s.
 * 
 * @author vjuranek
 *
 */
public class LabelInfo {
	
    /**
     * Human readable label name
     */
	private String label;
	
	/**
	 * In most cases similar to {@link label}, differ over implementations, 
	 * e.g. in case of LabelAtom it contains escaped version of {@link label}. 
	 */
	private String labelExpression;
	
	/**
	 * Total number of executors which have assigned this label
	 */
	private int totalExecutors;
	
	/**
	 * Total number of executors which have assigned this label and are busy
	 */
	private int busyExecutors;
	
	/**
	 * Size of job queue waiting for this label
	 */
	private int queueLength;
	
	/**
	 * List of slaves which have assigned this label
	 */
	private List<String> slaves;
	
	public String getLabel() {
		return label;
	}
	
	public void setLabel(String label) {
		this.label = label;
	}
	
	public String getLabelExpression() {
		return labelExpression;
	}

	public void setLabelExpression(String labelExpression) {
		this.labelExpression = labelExpression;
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
