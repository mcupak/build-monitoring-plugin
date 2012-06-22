package org.jenkinsci.plugins.buildanalysis;

import java.util.Date;

import org.ektorp.support.CouchDbDocument;

public class BuildInfo extends CouchDbDocument {
    
    private int number;
    private String name;
    private Date date;
    
    public BuildInfo(int number, String name, Date date){
        this.number = number;
        this.name = name;
        this.date = date;
        this.setId(name + "_" + number);
    }
    
    public int getNumber() {
        return number;
    }
    public void setNumber(int number) {
        this.number = number;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public Date getDate() {
        return date;
    }
    public void setDate(Date date) {
        this.date = date;
    }
    
    

}
