package org.jenkinsci.plugins.buildanalysis;

import hudson.Extension;
import hudson.model.AbstractBuild;
import hudson.model.listeners.RunListener;

import java.net.MalformedURLException;
import java.util.Date;

@Extension
public class BuildListener extends RunListener<AbstractBuild> {
    
    private final BuildMonitor monitor;
    
    public BuildListener() throws MalformedURLException {
        this.monitor = new BuildMonitor();
    }
    
    public void onFinalized(AbstractBuild build) {
        int number = build.number;
        String name = build.getParent().getDisplayName();
        Date scheduleTime = build.getTime();
        String buildOn = build.getBuiltOnStr();
        System.out.println("Build FINISHED: " + number + "\t" + name );
        monitor.update(new BuildInfo(number, name, scheduleTime));
    }
    

}
