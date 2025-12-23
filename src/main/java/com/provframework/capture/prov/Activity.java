package com.provframework.capture.prov;

import java.util.List;

public class Activity {
    
    private String id; 
    private String startedAtTime;
    private String endedAtTime;
    private String atLocation;
    private List<String> wasInformedBy;
    private List<String> used;
    private List<String> wasAssociatedWith;

    public Activity() {
    }

    public Activity(String id, String startedAtTime, String endedAtTime, 
        List<String> wasInformedBy, List<String> used, List<String> wasAssociatedWith) {
        this.id = id;
        this.startedAtTime = startedAtTime;
        this.endedAtTime = endedAtTime;
        this.wasInformedBy = wasInformedBy;
        this.used = used;
        this.wasAssociatedWith = wasAssociatedWith;
    }

    public String getId() {
        return id;
    }

    public String getStartedAtTime() {
        return startedAtTime;
    }

    public String getEndedAtTime() {
        return endedAtTime;
    }

    public String getAtLocation() {
        return atLocation;
    }

    public void setAtLocation(String atLocation) {
        this.atLocation = atLocation;
    }

    public List<String> getWasInformedBy() {
        return wasInformedBy;
    }

    public List<String> getUsed() {
        return used;
    }

    public List<String> getWasAssociatedWith() {
        return wasAssociatedWith;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setStartedAtTime(String startedAtTime) {
        this.startedAtTime = startedAtTime;
    }

    public void setEndedAtTime(String endedAtTime) {
        this.endedAtTime = endedAtTime;
    }

    public void setWasInformedBy(List<String> wasInformedBy) {
        this.wasInformedBy = wasInformedBy;
    }

    public void setUsed(List<String> used) {
        this.used = used;
    }

    public void setWasAssociatedWith(List<String> wasAssociatedWith) {
        this.wasAssociatedWith = wasAssociatedWith;
    }
}