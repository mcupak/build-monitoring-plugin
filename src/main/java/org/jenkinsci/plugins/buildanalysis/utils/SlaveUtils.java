package org.jenkinsci.plugins.buildanalysis.utils;

import hudson.model.Node;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class SlaveUtils {

    public static List<String> getSlaveNames(Set<Node> nodes) {
        List<String> slaves = new ArrayList<String>();
        for (Node n : nodes) {
            slaves.add(n.getDisplayName());
        }
        return slaves;
    }

}
