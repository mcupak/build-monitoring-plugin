package org.jenkinsci.plugins.buildanalysis;

import hudson.Extension;
import hudson.model.AbstractBuild;
import hudson.model.listeners.RunListener;

import java.net.MalformedURLException;
import java.net.UnknownHostException;
import java.util.Date;

@Extension
public class BuildListener extends RunListener<AbstractBuild> {
    
    private final BuildUpdater updater;
    
    public BuildListener() throws UnknownHostException {
        // TODO extentension point
        this.updater = new MongoUpdater();
    }
    
    public void onFinalized(AbstractBuild build) {
        int number = build.number;
        String name = build.getParent().getDisplayName();
        Date scheduleTime = build.getTime();
        String buildOn = build.getBuiltOnStr();
        System.out.println("Build FINISHED: " + number + "\t" + name );
        updater.update(new BuildInfo(number, name, scheduleTime));
    }
    

}
