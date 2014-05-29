package org.jenkinsci.plugins.buildanalysis.model;

import java.util.List;

/**
 * Statistics data for given {@link Slave}
 *
 * @author vjuranek
 *
 */
public class SlaveInfo {

    /**
     * Slave name
     */
    private String name;

    /**
     * Slave status (online/offline)
     */
    private boolean isOnline;

    /**
     * Total number of executors slave has configured
     */
    private int totalExecutors;

    /**
     * Number of currectly busy executors
     */
    private int busyExecutors;

    /**
     * Number of jobs in the queue which can be build on this slave //TODO verify if this is correct
     */
    private int queueLength;

    /**
     * List of labels slave has configured
     */
    private List<String> labels;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isOnline() {
        return isOnline;
    }

    public void setOnline(boolean isOnline) {
        this.isOnline = isOnline;
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
