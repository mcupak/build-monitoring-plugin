package org.jenkinsci.plugins.buildanalysis.monitor;

public enum MonitorStatus {
    
    RUNNING(true),
    STOPPED(false),
    UNKNOWN(false);
    
    private boolean isRunning;
    
    private MonitorStatus(boolean isRunning) {
        this.isRunning = isRunning;
    }
    
    public boolean toBoolean() {
        return isRunning;
    }
}
