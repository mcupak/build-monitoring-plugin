package org.jenkinsci.plugins.buildanalysis.model;

import hudson.model.queue.CauseOfBlockage;

/**
 * Information about one single item waiting in the job queue
 *
 * @author vjuranek
 *
 */
public class QueueItemInfo {

    private int id;

    /**
     * Build number
     */
    private int number;

    /**
     * Build name
     */
    private String name;

    /**
     * Class name of the job (e.g. freestyle, matrix etc.)
     */
    private String className;

    /**
     * Parameters the build is started with
     */
    private String params;

    /**
     * Reason why item waits in the queue
     */
    private CauseOfBlockage blockageCause;

    /**
     * True if multiple builds of the same job can run simultaneously
     */
    private boolean isConcurentBuild;

    /**
     * True if item can be build, but waits in the queue e.g. because of waiting for available executor
     */
    private boolean isBuildable;

    /**
     * True is item in the queue is blocked, e.g. because concurrent builds are not allowed and previous build doesn't
     * finish yet
     */
    private boolean isBlocked;

    /**
     * True if waits in the queue too long, e.g. because there isn't any executor where the item can be built
     */
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

    public CauseOfBlockage getBlockageCause() {
        return blockageCause;
    }

    public void setBlockageCause(CauseOfBlockage blockageCause) {
        this.blockageCause = blockageCause;
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
