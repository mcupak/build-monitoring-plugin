package org.jenkinsci.plugins.buildanalysis.utils;

import hudson.model.Label;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class LabelUtils {

    public static List<String> getLableNames(Set<? extends Label> labels) {
        List<String> lList = new ArrayList<String>();
        for (Label l : labels) {
            lList.add(l.getExpression());
        }
        return lList;
    }

}
