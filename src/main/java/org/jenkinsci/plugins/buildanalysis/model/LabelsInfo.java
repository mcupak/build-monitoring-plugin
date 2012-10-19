package org.jenkinsci.plugins.buildanalysis.model;

import java.util.Date;
import java.util.List;

public class LabelsInfo {

	private final Date timestamp;
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
