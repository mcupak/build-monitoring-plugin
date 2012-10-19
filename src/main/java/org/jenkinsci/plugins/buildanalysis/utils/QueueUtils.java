package org.jenkinsci.plugins.buildanalysis.utils;

import hudson.model.Label;
import hudson.model.Node;
import hudson.model.Queue.BuildableItem;

import java.util.List;

public class QueueUtils {

	public static int getLabelQueue(Label l, List<BuildableItem> bis) {
		int count = 0;
        for (BuildableItem bi : bis) {
            if(bi.task.getAssignedLabel() == l)
                count++;
        }
        return count;
	}
	
	public static int getSlaveQueue(Node n, List<BuildableItem> bis) {
		int count = 0;
        for (BuildableItem bi : bis) {
            if(n.canTake(bi) ==  null)
                count++;
        }
        return count;
	}
	
}
