package org.jenkinsci.plugins.buildanalysis.model;

import java.util.Date;
import java.util.List;

/**
 * Statistics data for all {@link Label}s
 *
 * @author vjuranek
 *
 */
public class LabelsInfo {

    /**
     * Time when the data are collected
     */
    private final Date timestamp;

    /**
     * All available {@link label}s configure in given time
     */
    private List<LabelInfo> labels;

    public LabelsInfo(Date timestamp) {
        this.timestamp = timestamp;
    }

    public List<LabelInfo> getLabels() {
        return labels;
    }

    public void setLabels(List<LabelInfo> labels) {
        this.labels = labels;
    }

    public Date getTimestamp() {
        return timestamp;
    }

}
