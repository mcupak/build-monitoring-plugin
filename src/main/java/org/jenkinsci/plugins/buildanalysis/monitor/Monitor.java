package org.jenkinsci.plugins.buildanalysis.monitor;

public interface Monitor {

    //public String getDisplayName();
    //public MonitorStatus getStatus();
    public void enable();

    public void disable();

}
