package org.jenkinsci.plugins.buildanalysis;

import java.util.Date;

public class BuildInfo {
    
    private int number;
    private String name;
    private Date date;
    
    public BuildInfo(int number, String name, Date date){
        this.number = number;
        this.name = name;
        this.date = date;
    }
    
    public int getNumber() {
        return number;
    }
    
    public String getName() {
        return name;
    }
    
    public Date getDate() {
        return date;
    }
    
}
