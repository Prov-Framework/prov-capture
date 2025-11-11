package com.provframework.capture.model;

import java.util.List;

public class Activity {
    
    private String id; 
    private String startedAtTime;
    private String endedAtTime;
    private List<Activity> wasInformedBy;
    private List<Entity> used;
    private List<Agent> wasAssociatedWith;

    public Activity() {
    }

    public Activity(String id, String startedAtTime, String endedAtTime, 
        List<Activity> wasInformedBy, List<Entity> used, List<Agent> wasAssociatedWith) {
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

    public List<Activity> getWasInformedBy() {
        return wasInformedBy;
    }

    public List<Entity> getUsed() {
        return used;
    }

    public List<Agent> getWasAssociatedWith() {
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

    public void setWasInformedBy(List<Activity> wasInformedBy) {
        this.wasInformedBy = wasInformedBy;
    }

    public void setUsed(List<Entity> used) {
        this.used = used;
    }

    public void setWasAssociatedWith(List<Agent> wasAssociatedWith) {
        this.wasAssociatedWith = wasAssociatedWith;
    }
}