package com.provframework.capture.model;

import java.util.List;

public class Entity {

    private String id;
    private List<Entity> wasDerivedFrom;
    private List<Activity> wasGeneratedBy;
    private List<Agent> wasAttributedTo;
    
    public Entity() {
    }

    public Entity(String id, List<Entity> wasDerivedFrom, List<Activity> wasGeneratedBy, List<Agent> wasAttributedTo) {
        this.id = id;
        this.wasDerivedFrom = wasDerivedFrom;
        this.wasGeneratedBy = wasGeneratedBy;
        this.wasAttributedTo = wasAttributedTo;
    }

    public String getId() {
        return id;
    }  

    public List<Entity> getWasDerivedFrom() {
        return wasDerivedFrom;
    }

    public List<Activity> getWasGeneratedBy() {
        return wasGeneratedBy;
    }

    public List<Agent> getWasAttributedTo() {
        return wasAttributedTo;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setWasDerivedFrom(List<Entity> wasDerivedFrom) {
        this.wasDerivedFrom = wasDerivedFrom;
    }  

    public void setWasAttributedTo(List<Agent> wasAttributedTo) {
        this.wasAttributedTo = wasAttributedTo;
    }

    public void setWasGeneratedBy(List<Activity> wasGeneratedBy) {
        this.wasGeneratedBy = wasGeneratedBy;
    }
}
