package org.jenkinsci.plugins.buildanalysis.model;

import java.util.Date;
import java.util.List;

public class QueueInfo {
	
	private final Date timestamp;
	private int queueSize;
	private int buildableSize;
	private int pendingSize;
	private int blockedSize;
	private int waitingSize;
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
