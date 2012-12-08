package org.jenkinsci.plugins.buildanalysis.model;

import java.util.Date;
import java.util.List;

/**
 * Statistics for all items in the queue
 * 
 * @author vjuranek
 *
 */
public class QueueInfo {
	
    /**
     * Time when the data are collected 
     */
	private final Date timestamp;
	
	/**
	 * Number of item in the queue
	 */
	private int queueSize;
	
	/** 
	 * Number of buildable items (see {@link QueueItemInfo} for details)
	 */
	private int buildableSize;
	
	/**
	 * Number of pending items
	 */
	private int pendingSize;
	
	/**
	 * Number of blocked items (see {@link QueueItemInfo} for details)
	 */
	private int blockedSize;
	
	/**
	 * Number of waiting items, waiting e.g. for quiet period to finish
	 */
	private int waitingSize;
	
	/**
	 * All {@link QueueItemInfo} items in the queue
	 */
	private List<QueueItemInfo> queueList;
	
	public QueueInfo(Date timestamp) {
		this.timestamp = timestamp;
	}
	
	public int getQueueSize() {
		return queueSize;
	}
	
	public void setQueueSize(int queueSize) {
		this.queueSize = queueSize;
	}
	
	public int getBuildableSize() {
		return buildableSize;
	}
	
	public void setBuildableSize(int buildableSize) {
		this.buildableSize = buildableSize;
	}
	
	public int getBlockedSize() {
		return blockedSize;
	}
	
	public void setBlockedSize(int blockedSize) {
		this.blockedSize = blockedSize;
	}

	public int getPendingSize() {
		return pendingSize;
	}

	public void setPendingSize(int pendingSize) {
		this.pendingSize = pendingSize;
	}
	
	public int getWaitingSize() {
		return waitingSize;
	}

	public void setWaitingSize(int waitingSize) {
		this.waitingSize = waitingSize;
	}

	public List<QueueItemInfo> getQueueList() {
		return queueList;
	}

	public void setQueueList(List<QueueItemInfo> queueList) {
		this.queueList = queueList;
	}

	public Date getTimestamp() {
		return timestamp;
	}
	
}
