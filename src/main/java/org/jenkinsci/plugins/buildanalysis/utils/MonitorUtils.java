package org.jenkinsci.plugins.buildanalysis.utils;

import hudson.ExtensionList;

public class MonitorUtils {

    public static <T> void enable(T monitor, ExtensionList<T> extensionList) {
        if(!extensionList.contains(monitor))
            extensionList.add(monitor);
    }
    
    public static <T> void disable(T monitor, ExtensionList<T> extensionList) {
        if(extensionList.contains(monitor))
            extensionList.remove(monitor);
    }
    
}
