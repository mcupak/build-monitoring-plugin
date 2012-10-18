package org.jenkinsci.plugins.buildanalysis.model;

import hudson.model.queue.CauseOfBlockage;

public class QueueItemInfo {

	private int id;
    private int number;
    private String name;
    private String className;
    private String params;
    private CauseOfBlockage blockageCauses;
    private boolean isConcurentBuild;
    private boolean isBuildable;
    private boolean isBlocked;
    private boolean isStucked;
    
	public int getId() {
		return id;
	}
	
	public void setId(int id) {
		this.id = id;
	}
	
	public int getNumber() {
		return number;
	}
	
	public void setNumber(int number) {
		this.number = number;
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public String getClassName() {
		return className;
	}
	
	public void setClassName(String className) {
		this.className = className;
	}
	
	public String getParams() {
		return params;
	}
	
	public void setParams(String params) {
		this.params = params;
	}
	
	public CauseOfBlockage getBlockageCauses() {
		return blockageCauses;
	}
	
	public void setBlockageCauses(CauseOfBlockage blockageCauses) {
		this.blockageCauses = blockageCauses;
	}
	
	public boolean isConcurentBuild() {
		return isConcurentBuild;
	}
	
	public void setConcurentBuild(boolean isConcurentBuild) {
		this.isConcurentBuild = isConcurentBuild;
	}

	public boolean isBuildable() {
		return isBuildable;
	}

	public void setBuildable(boolean isBuildable) {
		this.isBuildable = isBuildable;
	}

	public boolean isBlocked() {
		return isBlocked;
	}

	public void setBlocked(boolean isBlocked) {
		this.isBlocked = isBlocked;
	}

	public boolean isStucked() {
		return isStucked;
	}

	public void setStucked(boolean isStucked) {
		this.isStucked = isStucked;
	}
	
	
	
}
