package org.jenkinsci.plugins.buildanalysis.monitor;

public enum MonitorStatus {
    
    RUNNING(true),
    STOPPED(false),
    FAILED(false),
    UNKNOWN(false);
    
    private boolean isRunning;
    
    private MonitorStatus(boolean isRunning) {
        this.isRunning = isRunning;
    }
    
    public boolean toBoolean() {
        return isRunning;
    }
}
